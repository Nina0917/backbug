package com.yuanning.backbug.controller;

import com.yuanning.backbug.entity.Ticket;
import com.yuanning.backbug.entity.request.AddTicketRequest;
import com.yuanning.backbug.entity.request.TicketInfoResult;
import com.yuanning.backbug.exceptionHandler.MessageUtil;
import com.yuanning.backbug.exceptionHandler.Result;
import com.yuanning.backbug.service.TicketService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(path = "api/tickets")
@CrossOrigin
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TicketController {
    private final TicketService ticketService;

    @PostMapping("add-ticket")
    public Result addTicket(@RequestBody AddTicketRequest addTicketRequest) {
        return ticketService.addTicket(addTicketRequest);
    }

    @GetMapping("get-ticket")
    public Result<Ticket> getTicket(@RequestParam Long ticketId) {
        return ticketService.getTicket(ticketId);
    }

    /**
     *
     * @param projectId: project ID
     * @param page: page number
     * @return: all tickets under the project
     */
    @GetMapping("get-all")
    public Result<List<TicketInfoResult>> getAllTicketsAccProject(@RequestParam Long projectId, @RequestParam(defaultValue = "0") int page) {
        return ticketService.getAllTickets(projectId, page);
    }


}
