package nandhas.authservice.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import nandhas.authservice.service.CustomOAuth2UserService;
import nandhas.authservice.service.CustomUserService;

@Configuration
@EnableOAuth2Client
@Order(200)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    OAuth2ClientContext oauth2ClientContext;

    @Autowired
    CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Autowired
    CustomUserService userService;

    @Autowired
    CustomOAuth2UserService oauth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors().and()
            // .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .antMatcher("/**")
                .authorizeRequests()
                    .antMatchers("/signin", "/signup**", "/activate/*", "/resend/*", "/reset-password", "/change-password", "/change-password/*", "/webjars/**", "/error**", "/oauth/authorize")
                    .permitAll()
            .anyRequest()
                .authenticated()
                .and()
            .exceptionHandling().and()
            .formLogin()
                .loginPage("/signin").failureUrl("/signin?error=true")
                .defaultSuccessUrl("/")
                .usernameParameter("email")
                .passwordParameter("password")
                .and()
                .logout().logoutSuccessHandler(customLogoutSuccessHandler).permitAll()
                .and()
            .oauth2Login()
                .authorizationEndpoint().baseUri("/login").and()
                .redirectionEndpoint().baseUri("/login/callback/*").and()
                .userInfoEndpoint().userService(oauth2UserService)
                .and()
                .and()
            .csrf().disable();
    }

    @Override
	public void configure(WebSecurity web) throws Exception {
        web
	       .ignoring()
	       .antMatchers("/favicon.ico", "/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	}

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
            .passwordEncoder(encoder());
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        // provides the default AuthenticationManager as a Bean
        return super.authenticationManagerBean();
    }
}