package nextstep.security.web.matcher;

import javax.servlet.http.HttpServletRequest;

public class RequestPathMatcher implements RequestMatcher {

    private final String path;

    public RequestPathMatcher(String path) {
        this.path = path;
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return path.equals(uri);
    }
}
