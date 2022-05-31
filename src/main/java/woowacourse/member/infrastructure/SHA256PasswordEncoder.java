package woowacourse.member.infrastructure;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.stereotype.Component;

@Component
public class SHA256PasswordEncoder implements PasswordEncoder {

    private final PasswordValidator passwordValidator;

    public SHA256PasswordEncoder(final PasswordValidator passwordValidator) {
        this.passwordValidator = passwordValidator;
    }

    @Override
    public String encode(String password) {
        passwordValidator.validate(password);
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();

            for (byte hashByte : hash) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    stringBuilder.append('0');
                }
                stringBuilder.append(hex);
            }
            return stringBuilder.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
