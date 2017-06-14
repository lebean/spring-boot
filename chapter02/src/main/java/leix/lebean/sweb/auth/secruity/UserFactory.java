package leix.lebean.sweb.auth.secruity;

import leix.lebean.sweb.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class UserFactory {

    private UserFactory() {
    }

    public static AuthUser create(User user) {
        return new AuthUser(
                user.getId(),
                user.getName(),
                user.getPassword(),
                user.getEmail(),
                mapToGrantedAuthorities(user.getRoles()),
                user.getLastPasswordResetDate()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(String authorities) {
        String[] authorityArray=authorities.split(",");
        List<String> authorityList= Arrays.asList(authorities);
        return authorityList.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}

