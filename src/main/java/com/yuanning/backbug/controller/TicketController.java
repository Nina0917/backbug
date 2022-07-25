package com.yuanning.backbug.controller;

import com.yuanning.backbug.service.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/ticket")
@AllArgsConstructor
@CrossOrigin
public class TicketController {
    //private final TicketService ticketService;
}
