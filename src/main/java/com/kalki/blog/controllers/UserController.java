package com.kalki.blog.controllers;

import com.kalki.blog.config.MessageConstants;
import com.kalki.blog.payloads.ApiResponseWrapper;
import com.kalki.blog.payloads.UserDto;
import com.kalki.blog.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<ApiResponseWrapper<UserDto>> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto created = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseWrapper.success(MessageConstants.USER_CREATED, created));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponseWrapper<UserDto>> updateUser(
            @Valid @RequestBody UserDto userDto,
            @PathVariable("userId") Integer uid) {
        UserDto updated = userService.updateUser(userDto, uid);
        return ResponseEntity.ok(ApiResponseWrapper.success(MessageConstants.USER_UPDATED, updated));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseWrapper<Void>> deleteUser(@PathVariable("userId") Integer uid) {
        userService.deleteUser(uid);
        return ResponseEntity.ok(ApiResponseWrapper.success(MessageConstants.USER_DELETED, null));
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponseWrapper<List<UserDto>>> getAllUsers() {
        return ResponseEntity.ok(ApiResponseWrapper.success(null, userService.getAllUsers()));
    }

    @GetMapping("{userId}")
    public ResponseEntity<ApiResponseWrapper<UserDto>> getSingleUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(ApiResponseWrapper.success(null, userService.getUserById(userId)));
    }
}
