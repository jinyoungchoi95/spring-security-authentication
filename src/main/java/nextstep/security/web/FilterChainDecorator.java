package nextstep.security.web;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import java.util.List;

public interface FilterChainDecorator {

    FilterChain decorate(FilterChain original, List<Filter> filters);
}
