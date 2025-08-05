package com.kalki.blog.controllers;

import com.kalki.blog.config.MessageConstants;
import com.kalki.blog.exceptions.ApiException;
import com.kalki.blog.payloads.*;
import com.kalki.blog.security.JwtTokenHelper;
import com.kalki.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponseWrapper<JwtAuthResponse>> createToken(@RequestBody JwtAuthRequest request) throws Exception {
        authenticate(request.getUsername(), request.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtTokenHelper.generateToken(userDetails);

        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(token);

        return ResponseEntity.ok(ApiResponseWrapper.success(MessageConstants.LOGIN_SUCCESS, response));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (UsernameNotFoundException ex) {
            throw new ApiException(MessageConstants.USER_NOT_FOUND, false);
        } catch (BadCredentialsException ex) {
            throw new ApiException(MessageConstants.INVALID_CREDENTIALS, false);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponseWrapper<UserDto>> registerUser(@RequestBody UserDto userDto) {
        UserDto registeredUser = userService.registerNewUser(userDto);
        return new ResponseEntity<>(ApiResponseWrapper.success(MessageConstants.USER_REGISTERED_SUCCESS, registeredUser), HttpStatus.CREATED);
    }
}
