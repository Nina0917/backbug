package com.yuanning.backbug.repository;

import com.yuanning.backbug.entity.Ticket;
import com.yuanning.backbug.entity.request.TicketInfoResult;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TicketRepository extends PagingAndSortingRepository<Ticket, Long> {
    @Query("select new com.yuanning.backbug.entity.request.TicketInfoResult(t.id, t.title, t.description, t.time, t.createdTime, t.status, t.priority, t.creator.id, t.creator.lastName, t.project.id, t.project.name) from Ticket t where t.id = ?1")
    TicketInfoResult findTicketInfoById(Long ticketId);

    Ticket findTicketById(Long id);

    @Query("select new com.yuanning.backbug.entity.request.TicketInfoResult(t.id, t.title, t.description, t.time, t.createdTime, t.status, t.priority, t.creator.id, t.creator.lastName, t.project.id, t.project.name) from Ticket t where t.project.id = ?1")
    List<TicketInfoResult> findTicketInfosByProjectId(Long projectId, Pageable pageable);


}
