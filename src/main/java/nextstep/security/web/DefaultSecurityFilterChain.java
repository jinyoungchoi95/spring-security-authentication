package nextstep.security.web;

import nextstep.security.web.matcher.RequestMatcher;
import nextstep.security.web.matcher.RequestPathMatcher;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class DefaultSecurityFilterChain implements SecurityFilterChain {

    private final RequestMatcher requestMatcher;
    private final List<Filter> filters;

    public DefaultSecurityFilterChain(RequestMatcher requestMatcher, List<Filter> filters) {
        this.requestMatcher = requestMatcher;
        this.filters = filters;
    }

    public static DefaultSecurityFilterChain of(String path, Filter... filters) {
        return new DefaultSecurityFilterChain(new RequestPathMatcher(path), List.of(filters));
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        return requestMatcher.matches(request);
    }

    @Override
    public List<Filter> getFilters() {
        return filters;
    }
}
