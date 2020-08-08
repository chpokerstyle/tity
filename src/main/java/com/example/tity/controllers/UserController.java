package com.example.tity.controllers;

import com.example.tity.models.UsersDto;
import com.example.tity.service.UsersService;
import com.example.tity.service.ValidationException;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Log
public class UserController {
    private final UsersService usersService;

    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }
    @PostMapping("/save")
    public UsersDto saveUser(@RequestBody UsersDto usersDto) throws ValidationException {

      return usersService.saveUser(usersDto);
    }
    @GetMapping("/findAll")
    public List<UsersDto> findAllUsersDto(){
        return usersService.findAll();
    }
    @GetMapping("/findByLogin")
    public UsersDto findByLogin(@RequestBody String login){
        return usersService.findByLogin(login);
    }
    @DeleteMapping("/delete/(id)")
    public ResponseEntity<Void>deleteUsers(@PathVariable Integer id){
        usersService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
