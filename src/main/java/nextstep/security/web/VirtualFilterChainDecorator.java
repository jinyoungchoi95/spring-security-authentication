package nextstep.security.web;

import javax.servlet.*;
import java.io.IOException;
import java.util.List;

public class VirtualFilterChainDecorator implements FilterChainDecorator {
    @Override
    public FilterChain decorate(FilterChain original, List<Filter> filters) {
        return new VirtualFilterChain(original, filters);
    }

    private static final class VirtualFilterChain implements FilterChain {

        private final FilterChain originalChain;
        private final List<Filter> additionalFilters;
        private final int filterSize;

        private int currentPosition = 0;

        public VirtualFilterChain(FilterChain originalChain, List<Filter> additionalFilters) {
            this.originalChain = originalChain;
            this.additionalFilters = additionalFilters;
            this.filterSize = additionalFilters.size();
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
            if (currentPosition == filterSize) {
                originalChain.doFilter(request, response);
                return;
            }
            Filter nextFilter = additionalFilters.get(currentPosition);
            currentPosition++;
            nextFilter.doFilter(request, response, this);
        }
    }
}
