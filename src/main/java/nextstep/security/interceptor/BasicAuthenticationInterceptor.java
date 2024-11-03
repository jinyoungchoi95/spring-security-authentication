package nextstep.security.interceptor;

import nextstep.app.ui.AuthenticationException;
import nextstep.security.authentication.Authentication;
import nextstep.security.authentication.BasicAuthentication;
import nextstep.security.userdetails.UserDetails;
import nextstep.security.userdetails.UserDetailsService;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class BasicAuthenticationInterceptor implements HandlerInterceptor {

    private final UserDetailsService userDetailsService;

    public BasicAuthenticationInterceptor(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Authentication basicAuthentication = BasicAuthentication.from(request.getHeader(AUTHORIZATION));
        UserDetails userDetails = userDetailsService.loadUserByUsername(basicAuthentication.getPrincipal());

        if (!userDetails.getPassword().equals(basicAuthentication.getCredentials())) {
            throw new AuthenticationException();
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
