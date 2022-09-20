package com.yuanning.backbug.entity.request;

import com.yuanning.backbug.entity.messageEnum.TicketPriority;
import com.yuanning.backbug.entity.messageEnum.TicketStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@ToString

public class TicketInfoResult {
    private final Long ticketId;
    private final String title;
    private final String description;
    private final Integer time;
    private final LocalDateTime createdTime;
    private final TicketStatus status;
    private final TicketPriority priority;
    private final Long creatorId;
    private final String creatorName;
    private Set<AppUserResult> assignedUsers;
    private final Long projectId;
    private final String projectName;

    public TicketInfoResult(Long ticketId, String title, String description, Integer time, LocalDateTime createdTime, TicketStatus status, TicketPriority priority, Long creatorId, String creatorName, Long projectId, String projectName) {
        this.ticketId = ticketId;
        this.title = title;
        this.description = description;
        this.time = time;
        this.createdTime = createdTime;
        this.status = status;
        this.priority = priority;
        this.creatorId = creatorId;
        this.creatorName = creatorName;
        this.assignedUsers = assignedUsers;
        this.projectId = projectId;
        this.projectName = projectName;
    }
}
