package nextstep.security.web;

import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public class FilterChainProxy extends GenericFilterBean {

    private final List<SecurityFilterChain> filterChains;
    private final FilterChainDecorator filterChainDecorator;

    public FilterChainProxy(List<SecurityFilterChain> filterChains, FilterChainDecorator filterChainDecorator) {
        this.filterChains = filterChains;
        this.filterChainDecorator = filterChainDecorator;
    }

    public FilterChainProxy(SecurityFilterChain... filterChains) {
        this(List.of(filterChains), new VirtualFilterChainDecorator());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        List<Filter> filters = getFilters((HttpServletRequest) servletRequest);
        if (filters.isEmpty()) {
            return;
        }
        filterChainDecorator.decorate(filterChain, filters).doFilter(servletRequest, servletResponse);
    }

    private List<Filter> getFilters(HttpServletRequest request) {
        return filterChains.stream()
                .filter(filterChain -> filterChain.matches(request))
                .findFirst()
                .map(SecurityFilterChain::getFilters)
                .orElse(List.of());
    }
}
