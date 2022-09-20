package com.globallogic.creditcardpayment.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.globallogic.creditcardpayment.entity.JwtRequest;
import com.globallogic.creditcardpayment.entity.JwtResponse;
import com.globallogic.creditcardpayment.servicesImpl.JwtServiceImpl;


@RestController
@CrossOrigin
public class JwtController {

    @Autowired
    private JwtServiceImpl jwtService;

    @PostMapping({"/authenticate"})
    public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        return jwtService.createJwtToken(jwtRequest);
    }
}