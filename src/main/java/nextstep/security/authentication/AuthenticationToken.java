package nextstep.security.authentication;

public class AuthenticationToken implements Authentication {

    private final Object principal;
    private final Object credentials;

    public AuthenticationToken(Object principal, Object credentials) {
        this.principal = principal;
        this.credentials = credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }
}
