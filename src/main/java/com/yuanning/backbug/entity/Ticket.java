package com.yuanning.backbug.entity;

import com.yuanning.backbug.entity.messageEnum.TicketPriority;
import com.yuanning.backbug.entity.messageEnum.TicketStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
// @NoArgsConstructor
@Entity
public class Ticket {
    @Id
    @SequenceGenerator(
            name = "ticket_sequence",
            sequenceName = "ticket_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ticket_sequence"
    )
    private Long id;
    private String title;
    private String description;
    private Integer time;
    private LocalDateTime createdTime;
    private TicketStatus status;
    private TicketPriority priority;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "projectId"
    )
    private Project project;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "appUserId"
    )
    private User creator;

    @ManyToMany(mappedBy = "assignTickets")
    private Set<User> users;

    public Ticket() {
    }
}
