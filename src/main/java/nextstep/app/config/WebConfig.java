package nextstep.app.config;

import nextstep.security.interceptor.BasicAuthenticationInterceptor;
import nextstep.security.interceptor.FormLoginInterceptor;
import nextstep.security.userdetails.UserDetailsService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpSession;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final HttpSession httpSession;
    private final UserDetailsService userDetailsService;

    public WebConfig(HttpSession httpSession, UserDetailsService userDetailsService) {
        this.httpSession = httpSession;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new BasicAuthenticationInterceptor(userDetailsService))
                .addPathPatterns("/members");
        registry.addInterceptor(new FormLoginInterceptor(userDetailsService, httpSession))
                .addPathPatterns("/login");
    }
}
