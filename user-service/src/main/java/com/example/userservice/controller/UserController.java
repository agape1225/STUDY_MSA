package com.example.userservice.controller;

import com.example.userservice.VO.Greeting;
import com.example.userservice.VO.RequestUser;
import com.example.userservice.VO.ResponseUser;
import com.example.userservice.dto.UserDto;
import com.example.userservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class UserController {

    private Environment env;
    private Greeting greeting;
    private UserService userService;
    @Autowired
    public UserController(Environment env, Greeting greeting, UserService userService){
        this.env = env;
        this.greeting = greeting;
        this.userService = userService;
    }

    @GetMapping("/heath_check")
    public String status(){

        return "It's Working in User Service";

    }


    @GetMapping("/welcome")
    public String welcome(){

        return greeting.getMessage();

    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = mapper.map(user, UserDto.class);
        userService.createUser(userDto);

        ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);

    }
}
