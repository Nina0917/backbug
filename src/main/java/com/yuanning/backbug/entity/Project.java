package com.yuanning.backbug.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Project {
    @Id
    @SequenceGenerator(
            name = "project_sequence",
            sequenceName = "project_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "project_sequence"
    )
    private Long id;
    @Column(nullable = false)
    private String name;
    private String description;
    @OneToOne
    @JoinColumn
    private User manager;

    @ManyToMany(mappedBy = "project")
    private Set<User> user;


}
