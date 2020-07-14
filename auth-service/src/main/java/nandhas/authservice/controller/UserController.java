package nandhas.authservice.controller;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

import nandhas.authservice.service.CustomUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController
 */
@RestController
@Component
public class UserController {

    @Autowired
    CustomUserService userService;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @RequestMapping(value = { "/me", "/user" }, method = RequestMethod.GET)
    public Principal user(Principal principal) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("name", principal.getName());
        return principal;
    }
    
}