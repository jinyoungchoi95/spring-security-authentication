package nextstep.security.context;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class HttpSessionSecurityContextRepository implements SecurityContextRepository {

    private static final String SPRING_SECURITY_CONTEXT_KEY = "SPRING_SECURITY_CONTEXT";

    @Override
    public SecurityContext loadContext(ServletRequest request) {
        HttpSession httpSession = ((HttpServletRequest) request).getSession(false);
        if (httpSession == null) {
            return null;
        }
        Object contextFromSession = httpSession.getAttribute(SPRING_SECURITY_CONTEXT_KEY);
        if (contextFromSession == null) {
            return null;
        }
        if (!(contextFromSession instanceof SecurityContext)) {
            return null;
        }
        return (SecurityContext) contextFromSession;
    }
}
