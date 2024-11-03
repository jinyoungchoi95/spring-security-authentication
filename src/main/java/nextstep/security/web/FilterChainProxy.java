package nextstep.security.web;

import nextstep.security.context.SecurityContextHolderFilter;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FilterChainProxy extends GenericFilterBean {

    private final List<SecurityFilterChain> filterChains;
    private final FilterChainDecorator filterChainDecorator;
    private final SecurityContextHolderFilter securityContextHolderFilter;

    public FilterChainProxy(List<SecurityFilterChain> filterChains, FilterChainDecorator filterChainDecorator) {
        this.filterChains = filterChains;
        this.filterChainDecorator = filterChainDecorator;
        this.securityContextHolderFilter = new SecurityContextHolderFilter();
    }

    public FilterChainProxy(SecurityFilterChain... filterChains) {
        this(List.of(filterChains), new VirtualFilterChainDecorator());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        List<Filter> filters = new ArrayList<>(getFilters((HttpServletRequest) servletRequest));
        filters.add(securityContextHolderFilter);
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
