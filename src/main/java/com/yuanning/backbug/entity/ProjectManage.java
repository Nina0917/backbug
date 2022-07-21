package com.yuanning.backbug.entity;

import com.yuanning.backbug.entity.messageEnum.AppUserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "project_manage")
public class ProjectManage {
    @Id
    @SequenceGenerator(
            name = "projectManage_sequence",
            sequenceName = "projectManage_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "projectManage_sequence"
    )
    private Long id;

    private Long project_id;

    private Long appUser_id;

    private AppUserRole role;
}
