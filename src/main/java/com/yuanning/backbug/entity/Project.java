package com.yuanning.backbug.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
    private String title;
    private String description;
    private String readme;
    @ManyToOne
    // the owner entity ConfirmationToken will have a joint column called ""app_user_id
    // which stores the corresponding user id for each token
    // and has a foreign key to the AppUser Entity
    @JoinColumn(
            nullable = false,
            name = "app_user_id"
    )
    private AppUser appUser;

    public Project(String title, String description, String readme, AppUser appUser) {
        this.title = title;
        this.description = description;
        this.readme = readme;
        this.appUser = appUser;
    }
}
