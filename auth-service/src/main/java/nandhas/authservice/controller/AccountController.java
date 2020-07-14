package nandhas.authservice.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import nandhas.authservice.event.SendActivationEvent;
import nandhas.authservice.dto.CurrentUser;
import nandhas.authservice.dto.CustomUserPrincipal;
import nandhas.authservice.event.ResetPasswordEvent;
import nandhas.authservice.service.CustomUserService;
import nandhas.common.client.UserServiceClient;
import nandhas.common.dto.userservice.ChangePasswordDto;
import nandhas.common.dto.userservice.TokenDto;
import nandhas.common.dto.userservice.UserDto;
import nandhas.common.enums.MessageCode;
import nandhas.common.enums.TokenType;
import nandhas.common.utils.AuthUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccountController {

    @Value("${signup.successMessage}")
    private String signupSuccessMessage;

    @Value("${signup.userExistMessage}")
    private String userExistsErrorMessage;

    @Value("${signup.userNotExistMessage}")
    private String userNotExistsErrorMessage;

    @Value("${resetpassword.successMessage}")
    private String resetPasswordSuccessMessage;

    @Autowired
    CustomUserService userService;

    @Autowired
    UserServiceClient userServiceClient;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @RequestMapping(value = { "/signin" }, method = RequestMethod.GET)
    public String signin(@RequestParam(value = "userId", required = false) String userId, Model model) {
        model.addAttribute("userId", userId);
        return "signin";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(@ModelAttribute("user") UserDto user) {
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@Valid @ModelAttribute("user") UserDto user, BindingResult bindingResult, Model model,
            HttpServletRequest request) {

        if (!bindingResult.hasErrors()) {
            if (this.userService.getUser(user.getEmail()) != null) {
                bindingResult.rejectValue("email", "", userExistsErrorMessage);
            } else {
                user.setActive(false);
                user = this.userService.createUser(user);
                eventPublisher.publishEvent(new SendActivationEvent(user, getAppUrl(request)));
                model.addAttribute("successMessage", signupSuccessMessage);
            }
        }

        return "signup";
    }

    @GetMapping("/reset-password")
    public String resetPassword() {
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public ModelAndView resetPassword(ModelAndView modelAndView, @RequestParam String email,
            HttpServletRequest request) {
        final UserDto userDto = this.userService.getUser(email);

        if (userDto == null) {
            modelAndView.addObject("errorMessage", userNotExistsErrorMessage);
        } else {
            eventPublisher.publishEvent(new ResetPasswordEvent(userDto, getAppUrl(request)));
            modelAndView.addObject("successMessage", resetPasswordSuccessMessage);
        }

        modelAndView.setViewName("reset-password");
        return modelAndView;
    }

    @GetMapping("/change-password/{token}")
    public ModelAndView changePassword(@PathVariable String token) {
        ModelAndView modelAndView = new ModelAndView();
        TokenDto tokenDto = userServiceClient.getToken(token);
        if (tokenDto == null || tokenDto.getType() != TokenType.ResetPassword
                || Calendar.getInstance().getTime().after(tokenDto.getExpiresAt())) {
            modelAndView.addObject("errorMessage", "Oops, Your reset password link is not valid");
        }
        modelAndView.setViewName("change-password");
        modelAndView.addObject("token", token);
        return modelAndView;
    }

    @PostMapping("/change-password")
    public ModelAndView changePassword(@Valid ChangePasswordDto changePasswordDto, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();

        if (!result.hasErrors()) {
            TokenDto tokenDto = userServiceClient.getToken(changePasswordDto.getToken());
            if (tokenDto == null || tokenDto.getType() != TokenType.ResetPassword
                    || Calendar.getInstance().getTime().after(tokenDto.getExpiresAt())) {
                modelAndView.addObject("errorMessage", "Oops, Your reset password link is not valid");
            } else {
                userService.changePassword(tokenDto.getUserId(), changePasswordDto.getPassword());
                modelAndView.addObject("successMessage", "Your password has been changed successfully.");
            }
        }

        modelAndView.setViewName("change-password");
        return modelAndView;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String myProfile(@ModelAttribute("user") UserDto user, @CurrentUser CustomUserPrincipal userPrincipal) {
        user.setName(userPrincipal.getName());
        user.setEmail(userPrincipal.getEmail());
        user.setAvatarUrl(userPrincipal.getAvatar());
        return "my-profile";
    }

    @RequestMapping(value = "/my-profile", method = RequestMethod.POST)
    public String myProfile(@RequestParam("avatar") MultipartFile avatar, @ModelAttribute("user") UserDto user)
            throws IOException {
        if (avatar != null && avatar.getSize() > 0) {
            ByteArrayResource contentsAsResource = new ByteArrayResource(avatar.getBytes());
            MultiValueMap<String, Object> avatarMap = new LinkedMultiValueMap<>();
            avatarMap.add("avatar", contentsAsResource);
            avatarMap.add("fileType", avatar.getContentType());
            userServiceClient.updateAvatar(AuthUtil.getUserId(), avatarMap);
        }
        return "my-profile";
    }

    @RequestMapping(value = "/activate/{token}")
    public String activate(@PathVariable String token, Model model) {
        int messageCode = MessageCode.ActivationInvalid.ordinal();
        TokenDto tokenDto = userServiceClient.getToken(token);
        if (tokenDto != null && tokenDto.getType() == TokenType.Activation) {
            if (Calendar.getInstance().getTime().before(tokenDto.getExpiresAt())) {
                if (userService.activateUser(tokenDto.getUserId())) {
                    messageCode = MessageCode.ActivationSuccessful.ordinal();
                }
            } else {
                return "redirect:/signin?code=" + MessageCode.ActivationExpired.ordinal() + "&userId="
                        + tokenDto.getUserId();
            }
        }

        return "redirect:/signin?code=" + messageCode;
    }

    @RequestMapping(value = "/resend/{userId}")
    public String resend(@PathVariable String userId, HttpServletRequest request) {
        UserDto user = userServiceClient.getUser(userId);
        if (user != null) {
            eventPublisher.publishEvent(new SendActivationEvent(user, getAppUrl(request)));
        }

        return "redirect:/signin?code=" + MessageCode.ActivationSent.ordinal();
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}