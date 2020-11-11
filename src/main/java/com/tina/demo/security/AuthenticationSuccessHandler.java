package com.tina.demo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.tina.demo.vo.ResultCommonEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    public static final String CSRF_COOKIE_NAME = "XSRF-TOKEN";

    private static final String LOGIN_SUCCESS_MESSAGE = "User %s logged in from IP address %s";
    private static final String LOGIN_WITHOUT_ID_ERROR_MESSAGE = "User logged in, but username undefined.";
    private static final String LOGIN_WITHOUT_IP_ERROR_MESSAGE = "User logged in, but IP address is unknown.";

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationSuccessHandler.class);


    /**
     * This is called by Spring when a user successfully logs in. It sends the user's ID, name, and access permissions
     * back to the client.
     *
     * @param request The HttpServletRequest generated by the login attempt.
     * @param response The HttpServletResponse to write the user information to.
     * @param authentication The authentication object holding information about the logged in user.
     * @throws IOException Any IOException generated in writing the response.
     * @throws ServletException Any ServletException generated in writing the response.
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        // we need the principal to pass information back to the client
        // i.e. username and roles

        if (!(authentication.getPrincipal() instanceof User)) {
            throw new IllegalArgumentException("Principal must be an instance of XandyUserDetails.");
        }

        User user = (User) authentication.getPrincipal();

        // Log the successful login attempt
        try {
            AuthenticationSuccessHandler.logSuccessfulLogin(user, request);
        } catch (Exception e) {
            AuthenticationSuccessHandler.logger.error(e.getMessage());
        }

        // JSON transformer for user information
        ObjectMapper mapper = new ObjectMapper();

        AccountCredentials us = new AccountCredentials();
        us.setToken(TokenAuthenticationService.addAuthentication(user.getUsername()));
        us.setUsername(us.getUsername());
        ResultCommonEntity<AccountCredentials> result = new ResultCommonEntity<AccountCredentials>(us, true, "successfull", response.getStatus());
        // prepare response for JSON consumption
        response.setContentType(JafSecurityConstants.RESPONSE_CONTENT_TYPE);
        CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (csrf != null) {
            Cookie cookie = WebUtils.getCookie(request, CSRF_COOKIE_NAME);
            String token = csrf.getToken();
            if (cookie==null || token!=null && !token.equals(cookie.getValue())) {
                cookie = new Cookie(CSRF_COOKIE_NAME, token);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
        PrintWriter writer = response.getWriter();
        writer.print(mapper.writeValueAsString(result));
        writer.flush();
        writer.close();

        // forget what the client did
        clearAuthenticationAttributes(request);
    }

    /**
     * Logs successful login attempts.
     *
     * @param user The User object representing the user who logged in.
     * @param request The HttpServletRequest created by the login attempt.
     */
    private static void logSuccessfulLogin(User user, HttpServletRequest request) {
        String userName = user.getUsername();
        if (userName == null) {
            AuthenticationSuccessHandler.logger.error(AuthenticationSuccessHandler.LOGIN_WITHOUT_ID_ERROR_MESSAGE);
            userName = JafSecurityConstants.UNKNOWN_VALUE;
        }
        String ipAddress = request.getRemoteAddr();
        if (ipAddress == null) {
            AuthenticationSuccessHandler.logger.error(AuthenticationSuccessHandler.LOGIN_WITHOUT_IP_ERROR_MESSAGE);
            ipAddress = JafSecurityConstants.UNKNOWN_VALUE;
        }
        AuthenticationSuccessHandler.logger.info(String.format(AuthenticationSuccessHandler.LOGIN_SUCCESS_MESSAGE,
                userName, ipAddress));
    }
}
