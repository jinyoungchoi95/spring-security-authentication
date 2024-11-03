package nextstep.security.authentication;

import nextstep.app.ui.AuthenticationException;

import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BasicAuthentication implements Authentication {

    private static final Pattern TOKEN_PATTERN = Pattern.compile("^Basic (.+)$");
    private static final String TOKEN_SPLIT_REGEX = ":";
    private static final int TOKEN_LENGTH = 2;
    private static final int TOKEN_EMAIL_INDEX = 0;
    private static final int TOKEN_PASSWORD_INDEX = 1;

    private final String principal;
    private final String credentials;

    public BasicAuthentication(String principal, String credentials) {
        this.principal = principal;
        this.credentials = credentials;
    }

    public static BasicAuthentication from(String authorizationHeader) {
        if (authorizationHeader == null) {
            throw new AuthenticationException();
        }
        String[] parts = parseToken(authorizationHeader);
        if (parts.length != TOKEN_LENGTH) {
            throw new AuthenticationException();
        }
        return new BasicAuthentication(parts[TOKEN_EMAIL_INDEX], parts[TOKEN_PASSWORD_INDEX]);
    }

    private static String[] parseToken(String authorizationHeader) {
        Matcher matcher = TOKEN_PATTERN.matcher(authorizationHeader);
        if (!matcher.matches()) {
            throw new AuthenticationException();
        }

        String base64Credentials = matcher.group(1);
        String decodedCredentials = new String(Base64.getDecoder().decode(base64Credentials));
        return decodedCredentials.split(TOKEN_SPLIT_REGEX, TOKEN_LENGTH);
    }


    @Override
    public String getPrincipal() {
        return principal;
    }

    @Override
    public String getCredentials() {
        return credentials;
    }
}
