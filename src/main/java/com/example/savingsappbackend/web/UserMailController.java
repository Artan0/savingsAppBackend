package com.example.savingsappbackend.web;

import com.example.savingsappbackend.models.UserMail;
import com.example.savingsappbackend.models.dto.UserMailDTO;
import com.example.savingsappbackend.service.UserMailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class UserMailController {
    private final UserMailService service;

    public UserMailController(UserMailService userMailService) {
        this.service = userMailService;
    }

    @PostMapping("/send-email")
    public ResponseEntity<Void> addNewEmail(@RequestBody UserMailDTO data){
        if(data == null){
            return ResponseEntity.notFound().build();
        }
        UserMail um = this.service.addNewEmail(data.email, data.message);
        System.out.println(data.getUserEmail());
        System.out.println(data.getMessage());
        if(um != null)
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.notFound().build();
    }
}
