package com.example.timeout.server.controller;

import com.example.timeout.server.model.User;
import com.example.timeout.server.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

  @Autowired
  ClientService clientService;

  @GetMapping("/")
  public ResponseEntity<User> index(@RequestParam("syncName") String syncName) {

    return clientService.getUser(syncName);
  }
}
