package com.yuanning.backbug.service;

import com.yuanning.backbug.entity.User;
import com.yuanning.backbug.entity.Ticket;
import com.yuanning.backbug.entity.messageEnum.TicketPriority;
import com.yuanning.backbug.entity.messageEnum.TicketStatus;
import com.yuanning.backbug.entity.request.AddTicketRequest;
import com.yuanning.backbug.entity.request.AppUserResult;
import com.yuanning.backbug.entity.request.TicketInfoResult;
import com.yuanning.backbug.exceptionHandler.MessageUtil;
import com.yuanning.backbug.exceptionHandler.Result;
import com.yuanning.backbug.repository.AppUserRepository;
import com.yuanning.backbug.repository.ProjectRepository;
import com.yuanning.backbug.repository.TicketRepository;
import com.yuanning.backbug.security.common.utils.CurrentUserUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final AppUserRepository appUserRepository;
    private final ProjectRepository projectRepository;
    private final CurrentUserUtils currentUserUtils;

    public Result<String> addTicket(AddTicketRequest addTicketRequest) {
        // get current user
        User user = currentUserUtils.getCurrentUser();

        // add ticket to ticket repository
        Ticket ticket = new Ticket();
        ticket.setTitle(addTicketRequest.getTitle());
        ticket.setDescription(addTicketRequest.getDescription());
        ticket.setCreatedTime(LocalDateTime.now());
        ticket.setCreator(user);
        ticket.setTime(addTicketRequest.getTime());
        Integer priority = addTicketRequest.getPriority();
        if (priority == 0) {
            ticket.setPriority(TicketPriority.Low);
        } else if (priority == 1) {
            ticket.setPriority(TicketPriority.Medium);
        } else {
            ticket.setPriority(TicketPriority.High);
        }
        Integer status = addTicketRequest.getStatus();
        if (status == 0) {
            ticket.setStatus(TicketStatus.New);
        } else if (status == 1) {
            ticket.setStatus(TicketStatus.In_Progress);
        } else {
            ticket.setStatus(TicketStatus.Resolved);
        }
        ticket.setProject(projectRepository.findById(addTicketRequest.getProjectId()).get());
        // set who are assigned with this ticket
        List<Long> userIds = addTicketRequest.getUserIds();

        Ticket save = ticketRepository.save(ticket);
        for (Long id : userIds) {
            User appuser = appUserRepository.findById(id).get();
            appuser.addTicket(save);
            appUserRepository.save(appuser);
        }

        return MessageUtil.success();

    }

    public Result<Ticket> getTicket(Long ticketId) {
        // find ticket bu id, except the column assignedUsersResult
        TicketInfoResult ticketById = ticketRepository.findTicketInfoById(ticketId);
        // find all users who are assigned with the ticket
        Set<User> users = appUserRepository.findAppUsersByAssignTicketsId(ticketId);
        // make appUsers to appUserResults
        Set<AppUserResult> appUserResults = new HashSet<>();

        for (User user : users) {
            // Long id, String email, String firstName, String lastName
            AppUserResult result = new AppUserResult(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName());
            appUserResults.add(result);
        }
        // set the last column
        ticketById.setAssignedUsers(appUserResults);
        return MessageUtil.success(ticketById);
    }

    public Result<List<TicketInfoResult>> getAllTickets(Long projectId, int page) {

        // query from the repository, get all following users
        // every page contains 5 rows
        Pageable pageable = PageRequest.of(page,5);
        List<TicketInfoResult> ticketInfos = ticketRepository.findTicketInfosByProjectId(projectId, pageable);
        return MessageUtil.success(ticketInfos);
    }
}
