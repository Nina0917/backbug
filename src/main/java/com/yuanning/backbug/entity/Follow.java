package com.yuanning.backbug.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Follow {
    @Id
    @SequenceGenerator(
            name = "follow_sequence",
            sequenceName = "follow_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "follow_sequence"
    )
    private Long id;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "userId"
    )
    private User user;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "followUserId"
    )
    private User followUser;
    private LocalDateTime createdTime;
}
