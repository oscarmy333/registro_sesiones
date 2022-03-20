package com.myos.registro_sesiones.dominio;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import org.springframework.data.domain.Persistable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.myos.registro_sesiones.util.tools.Generator;

@Entity
@Table(name = "users")
public class Usuario implements UserDetails, Persistable<String> {

    @Id
    private String id;

    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    private boolean enabled;
    private boolean accountNonLocked;
    private boolean accountNonExpired;
    private boolean credentialsNonExpired;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }

    @PrePersist
    private void doPrePersist() {
        this.id = Generator.generateUniqueString();
    }

    @Override
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Usuario)) {
            return false;
        }
        Usuario user = (Usuario) o;
        return getId().equals(user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Usuario{"
                + "id=" + id
                + ", email='" + email + '\''
                + ", password='" + password + '\''
                + ", enabled=" + enabled
                + ", accountNonLocked=" + accountNonLocked
                + ", accountNonExpired=" + accountNonExpired
                + ", credentialsNonExpired=" + credentialsNonExpired
                + ", roles=" + roles
                + '}';
    }
}
