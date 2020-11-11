package com.tina.demo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tina.demo.vo.ResultCommonEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Respond to the client that the authentication failed.
 */
@Component
public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private static final String LOGIN_FAILURE_LOG_MESSAGE = "Unsuccessful login attempt from user %s at IP %s";
    private static final String USER_NAME_PARAMETER = "username";
	public static final String UNKNOWN_VALUE = "<unknown>";

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFailureHandler.class);

    /**
     * Called when a user login attempt fails because of authentication. This implementation just logs it and
     * returns an unauthorized HTTP response code.
     * @param request The HttpServletRequest created by the login attempt.
     * @param response The HttpServletResponse to send errors to.
     * @param exception The error generated by the failed login attempt.
     * @throws IOException Required by the interface, but unlikely to be thrown.
     * @throws ServletException Required by the interface, but unlikely to be thrown.
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException {

        // Log failed login attempts.
        try {
            AuthenticationFailureHandler.logFailedLoginAttempt(request);
        } catch (Exception e) {
            AuthenticationFailureHandler.logger.error(e.getMessage());
        }
        response.setContentType(JafSecurityConstants.RESPONSE_CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ObjectMapper mapper = new ObjectMapper();
        ResultCommonEntity<AccountCredentials> result = new ResultCommonEntity<AccountCredentials>(null, false, "Fail", response.getStatus());
        PrintWriter writer = response.getWriter();
        writer.print(mapper.writeValueAsString(result));
        writer.flush();
        writer.close();
    }

    /**
     * Logs failed login attempts.
     *
     * @param request The HttpServletRequest created by the login attempt.
     */
    private static void logFailedLoginAttempt(HttpServletRequest request) {
        String userName = request.getParameter(AuthenticationFailureHandler.USER_NAME_PARAMETER);
        if (userName == null) {
            userName = UNKNOWN_VALUE;
        }
        String ipAddress = request.getRemoteAddr();
        if (ipAddress == null) {
            ipAddress = UNKNOWN_VALUE;
        }
        AuthenticationFailureHandler.logger.info(String.format(AuthenticationFailureHandler.LOGIN_FAILURE_LOG_MESSAGE,
				userName, ipAddress));
    }
}