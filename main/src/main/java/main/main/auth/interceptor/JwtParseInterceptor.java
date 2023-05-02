package main.main.auth.interceptor;

import lombok.extern.slf4j.Slf4j;
import main.main.auth.utils.ErrorResponder;
import main.main.auth.utils.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@Component
public class JwtParseInterceptor implements HandlerInterceptor {

    private final JwtUtils jwtUtils;
    private static final ThreadLocal<Long> authenticatedMemberId = new ThreadLocal<>();

    public JwtParseInterceptor(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    public static long getAutheticatedMemberId() {
        return authenticatedMemberId.get();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            Map<String, Object> claims = jwtUtils.getJwsClaimsFromRequest(request);
            Object userId = claims.get("memberId");
            if (userId != null) {
                authenticatedMemberId.set(Long.valueOf(userId.toString()));
                return true;
            } else {
                ErrorResponder.sendErorrResponse(response, HttpStatus.UNAUTHORIZED);
                return false;
            }
        } catch (Exception e) {
            authenticatedMemberId.set(-1L);
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
        authenticatedMemberId.remove();
    }
}
