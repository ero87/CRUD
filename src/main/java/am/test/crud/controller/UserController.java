package am.test.crud.controller;

import am.test.crud.entity.UserEntity;
import am.test.crud.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UsersService usersService;

    @Autowired
    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/users")
    private ResponseEntity<List<UserEntity>> getAllUsers() {
        List<UserEntity> userEntities = new ArrayList<>();
        userEntities = this.usersService.getAllFromRedis();
        return ResponseEntity.ok(userEntities);
    }

    @GetMapping("/users/{userId}")
    private ResponseEntity<UserEntity> getUser(@PathVariable("userId") Integer userId) throws Exception {
        return ResponseEntity.ok(this.usersService.getUser(userId).orElse(new UserEntity()));
    }

    @PostMapping("/users/add")
    private ResponseEntity<UserEntity> addUser(@RequestBody UserEntity userEntity) {
        return ResponseEntity.ok(this.usersService.add(userEntity));
    }

    @PatchMapping("/users/update/{userId}")
    private ResponseEntity<UserEntity> updateUser(@PathVariable("userId") Integer userId, @RequestBody UserEntity userEntity) {
        return ResponseEntity.ok(this.usersService.updateUser(userId, userEntity).orElse(userEntity));
    }

}
