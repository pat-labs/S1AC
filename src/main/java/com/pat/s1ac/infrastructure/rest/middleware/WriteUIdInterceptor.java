package com.pat.s1ac.infrastructure.rest.middleware;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class WriteUIdInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(WriteUIdInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ipFromHeader = request.getHeader("X-FORWARDED-FOR");
        if (ipFromHeader != null && ipFromHeader.length() > 0) {
            log.debug("ip from proxy - X-FORWARDED-FOR : {}", ipFromHeader);
        }

        String writeUId = request.getHeader("writeUId");
        if (writeUId == null || writeUId.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Missing 'writeUId' in the request");
            return false;
        }
        log.debug("User code : {}", writeUId);
        return true;
    }
}