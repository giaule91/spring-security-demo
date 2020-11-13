package com.tina.demo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tina.demo.security.vo.UserInfo;
import com.tina.demo.vo.ResultCommonEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class EntryPointHandler implements AuthenticationEntryPoint {

    private static final String NULL_RESPONSE_ERROR_MESSAGE = "HTTP Response cannot be null.";
    /**
     * Called upon the beginning of the authentication chain. This function will always
     * return 401 UNAUTHORIZED.
     *
     * @param request The HTTP request for a resource on the server.
     * @param response The HTTP response the server will write to.
     * @param authException The authentication exception that caused this method to be called.
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException, ServletException {
        if (response == null) {
            throw new IllegalArgumentException(EntryPointHandler.NULL_RESPONSE_ERROR_MESSAGE);
        }
        response.setContentType(JafSecurityConstants.RESPONSE_CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ObjectMapper mapper = new ObjectMapper();
        ResultCommonEntity<UserInfo> result = new ResultCommonEntity<UserInfo>(null, false, "Fail", response.getStatus());
        PrintWriter writer = response.getWriter();
        writer.print(mapper.writeValueAsString(result));
        writer.flush();
        writer.close();
    }
}

