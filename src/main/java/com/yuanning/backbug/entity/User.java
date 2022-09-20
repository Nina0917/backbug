package com.yuanning.backbug.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yuanning.backbug.entity.messageEnum.RoleType;
import com.yuanning.backbug.representation.UserRepresentation;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class User {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    // default value
    private Boolean locked = false;
    private Boolean enabled = false;
    // 负责的项目
    @ManyToMany
    @JoinTable(
            name = "project_manage",
            joinColumns = @JoinColumn(name = "appUser_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private Set<Project> project;

    @ManyToMany
    @JoinTable(
            name = "ticket_assign",
            joinColumns = @JoinColumn(name = "appUser_id"),
            inverseJoinColumns = @JoinColumn(name = "ticket_id")
    )
    private Set<Ticket> assignTickets;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<UserRole> userRoles = new ArrayList<>();

    public User(String firstName, String lastName, String email,
                String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;

    }

    public List<SimpleGrantedAuthority> getRoles() {
        List<Role> roles = userRoles.stream().map(UserRole::getRole).collect(Collectors.toList());
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName())));
        return authorities;
    }

    // 这个是干啥的？
    public UserRepresentation toUserRepresentation() {
        return UserRepresentation.builder().fullName(getFirstName()+" "+getLastName())
                .userName(this.email).build();
    }


    public void addTicket(Ticket ticket) {
        this.assignTickets.add(ticket);
    }

    public void addProject(Project p) {
        this.project.add(p);
    }
}
