package nextstep.security.interceptor;

import nextstep.app.ui.AuthenticationException;
import nextstep.security.userdetails.UserDetails;
import nextstep.security.userdetails.UserDetailsService;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FormLoginInterceptor implements HandlerInterceptor {

    public static final String SPRING_SECURITY_CONTEXT_KEY = "SPRING_SECURITY_CONTEXT";

    private final UserDetailsService userDetailsService;
    private final HttpSession httpSession;

    public FormLoginInterceptor(UserDetailsService userDetailsService, HttpSession httpSession) {
        this.userDetailsService = userDetailsService;
        this.httpSession = httpSession;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username == null || password == null) {
            throw new AuthenticationException();
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (!userDetails.getPassword().equals(password)) {
            throw new AuthenticationException();
        }

        httpSession.setAttribute(SPRING_SECURITY_CONTEXT_KEY, userDetails);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
