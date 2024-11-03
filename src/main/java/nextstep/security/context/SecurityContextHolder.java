package nextstep.security.context;

public class SecurityContextHolder {

    private static final ThreadLocal<SecurityContext> contextHolder = new ThreadLocal<>();

    private SecurityContextHolder() {
    }

    public static void clearContext() {
        contextHolder.remove();
    }

    public static SecurityContext getContext() {
        SecurityContext securityContext = contextHolder.get();

        if (securityContext == null) {
            securityContext = new SecurityContext();
            contextHolder.set(securityContext);
        }
        return securityContext;
    }

    public static void setContext(SecurityContext context) {
        if (context == null) {
            return;
        }
        contextHolder.set(context);
    }
}
