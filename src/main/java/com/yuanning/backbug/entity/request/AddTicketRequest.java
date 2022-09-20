package com.yuanning.backbug.entity.request;

import com.yuanning.backbug.entity.Ticket;
import com.yuanning.backbug.entity.messageEnum.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class AddTicketRequest {
    private final String title;
    private final String description;
    private final Integer time;
    private final List<Long> userIds;
    // 0:New, 1:In_Progress, 2:Resolved
    private final Integer status;
    // 0:Low, 1:Medium, 2:High
    private final Integer priority;
    private final Long projectId;
}
