package nextstep.app.config;

import nextstep.security.filter.BasicAuthenticationFilter;
import nextstep.security.filter.FormLoginAuthenticationFilter;
import nextstep.security.userdetails.UserDetailsService;
import nextstep.security.web.DefaultSecurityFilterChain;
import nextstep.security.web.FilterChainProxy;
import nextstep.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final UserDetailsService userDetailsService;

    public WebConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public DelegatingFilterProxy delegatingFilterProxy() {
        SecurityFilterChain loginFilterChain = DefaultSecurityFilterChain.of(
                "/login",
                new FormLoginAuthenticationFilter(userDetailsService)
        );
        SecurityFilterChain basicFilterChain = DefaultSecurityFilterChain.of(
                "/members",
                new BasicAuthenticationFilter(userDetailsService)
        );
        return new DelegatingFilterProxy(new FilterChainProxy(loginFilterChain, basicFilterChain));
    }
}
