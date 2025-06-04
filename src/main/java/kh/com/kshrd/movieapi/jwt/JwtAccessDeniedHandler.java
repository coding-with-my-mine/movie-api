package kh.com.kshrd.movieapi.jwt;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        String json = """
                {
                  "status": 403,
                  "error": "FORBIDDEN",
                  "message": "You do not have permission to access this resource",
                  "path": "%s"
                }
                """.formatted(request.getRequestURI());

        response.getWriter().write(json);
    }
}

