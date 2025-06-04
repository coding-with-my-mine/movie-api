package kh.com.kshrd.movieapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppUser implements UserDetails {

    private Long userId;
    private String fullName;
    private String email;
    private String password;
    private String profilePicture;
    private Boolean isAdmin;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (isAdmin){
            return Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
