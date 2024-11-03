package nextstep.security.context;

import javax.servlet.ServletRequest;

public interface SecurityContextRepository {

    SecurityContext loadContext(ServletRequest request);
}
