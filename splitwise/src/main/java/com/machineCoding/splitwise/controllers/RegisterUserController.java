package com.machineCoding.splitwise.controllers;

import com.machineCoding.splitwise.dto.RegisterUserRequestDto;
import com.machineCoding.splitwise.dto.RegisterUserResponseDto;
import com.machineCoding.splitwise.models.User;
import com.machineCoding.splitwise.services.RegisterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Controller
@Component
public class RegisterUserController {
    @Autowired
    private RegisterUserService registerUserService;
    public RegisterUserResponseDto regUser(RegisterUserRequestDto request)
    {
        RegisterUserResponseDto registerUserResponseDto = new RegisterUserResponseDto();
        User user = registerUserService.regUser(request.getName(),request.getPhone(),request.getEmail());
        registerUserResponseDto.setUser(user);
        return registerUserResponseDto;
    }
}
