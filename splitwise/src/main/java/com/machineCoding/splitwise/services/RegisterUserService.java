package com.machineCoding.splitwise.services;

import com.machineCoding.splitwise.models.User;
import com.machineCoding.splitwise.repositories.UserRespository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class RegisterUserService {
    @Autowired
    private UserRespository userRespository;
    public User regUser( String name, String phone,String email)
    {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPhone(phone);
        userRespository.save(user);
        return user;
    }
}
