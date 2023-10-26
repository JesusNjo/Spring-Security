package com.Security.SpringSecurity.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/security")
public class CustomerController {


    @Autowired
    private SessionRegistry sessionRegistry;

    @GetMapping("/v1")
    public String getString(){
        return "Endpoint con security";
    }

    @GetMapping("/v2")
    public String getString2(){
        return "Endpoint sin security";
    }


    @GetMapping("/session")
    public ResponseEntity<?> getInformation() {

        String sessionId = "";
        User sessionUser = null;


        List<Object> sessions = sessionRegistry.getAllPrincipals();
        Map<String, Object> response = null;
        for (Object session : sessions) {
            if (session instanceof User) {
                sessionUser = (User) session;
            }

            List<SessionInformation> sessionInformations = sessionRegistry.getAllSessions(session, false);

            for (SessionInformation sessionInformation : sessionInformations) {
                sessionId = sessionInformation.getSessionId();
            }

            response = new HashMap<>();
            response.put("response", "Hello world");
            response.put("sessionId", sessionId);
            response.put("sessionUser", sessionUser);
        }

        return ResponseEntity.ok(response);
    }
}
