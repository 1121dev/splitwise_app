package com.machineCoding.splitwise.commands;

import com.machineCoding.splitwise.controllers.RegisterUserController;
import com.machineCoding.splitwise.dto.RegisterUserRequestDto;
import com.machineCoding.splitwise.dto.RegisterUserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class RegisterUserCommand implements Command{
    @Autowired
    private RegisterUserController registerUserController;
    @Override
    public void execute(String input) {
        List<String>words = List.of(input.split(" "));
        RegisterUserRequestDto request = new RegisterUserRequestDto();
        request.setName(words.get(1));
        request.setPhone(words.get(2));
        request.setEmail(words.get(3));
        RegisterUserResponseDto response = registerUserController.regUser(request);
        System.out.println("Welcome to Splitwise "+response.getUser().getName()+" , Your user id is : "+ response.getUser().getId());
    }

    @Override
    public boolean matches(String input) {
        List<String>words = List.of(input.split(" "));
        return words.get(0).equalsIgnoreCase(CommandKeywords.registerUser)&&
                words.size()==4;
    }
}
