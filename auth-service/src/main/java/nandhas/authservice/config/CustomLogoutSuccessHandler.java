package nandhas.authservice.config;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
                if (authentication != null && authentication.getDetails() != null) {
                    try {
                        request.getSession().invalidate();
                        System.out.println("User Successfully Logout");
                        //you can add more codes here when the user successfully logs out,
                        //such as updating the database for last active.
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
         
                response.setStatus(HttpServletResponse.SC_OK);

                String postLogoutUri = request.getParameter("post_logout_redirect_uri");

                if(postLogoutUri != null && !postLogoutUri.isEmpty()) {
                    response.sendRedirect(postLogoutUri);
                }
                else {
                    //redirect to login
                    response.sendRedirect("/signin");
                }
	}

}