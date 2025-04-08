package rest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min=2, max=25, message = "Name should be between 2 and 25 characters")
    @Column(name="name")
    private String name;

    @NotEmpty(message = "Surname should not be empty")
    @Size(min=2, max=25, message = "Surname should be between 2 and 25 characters")
    @Column(name="surname")
    private String surname;

    @NotEmpty(message = "Nickname should not be empty")
    @Size(min=2, max=25, message = "Nickname should be between 2 and 25 characters")
    @Column(name="username")
    private String username;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email not valid")
    @Column(name="email")
    private String email;

    @NotEmpty(message = "Password should not be empty")
    @Column(name="password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName= "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName= "id")
    )
    private Set<Role> roles = new HashSet<>();

    public String getRolesString() {
        StringBuilder rolesString = new StringBuilder();
        this.roles.stream().map(Role::getName).forEach(role -> {
            rolesString.append(role.substring(5));
            rolesString.append(" ");
        });
        return rolesString.toString();
    }

    public void update(User newUser, Set<Role> managedRoles) {
        this.setName(newUser.getName());
        this.setSurname(newUser.getSurname());
        this.setUsername(newUser.getUsername());
        this.setEmail(newUser.getEmail());
        this.setPassword(newUser.getPassword());
        this.setRoles(managedRoles);
    }
    @Override
    public String toString(){
        return String.valueOf(this.id) + ": " + this.name + " " + this.surname + " " + this.username + " " + this.email
                + " roles: " + this.getRolesString();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
