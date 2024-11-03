package nextstep.security.authentication;

public class BasicTokenAuthentication extends AuthenticationToken {

    private final String token;

    public BasicTokenAuthentication(String token) {
        super(null, null);
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
