package com.kalki.blog.payloads;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private int id;

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 4, message = "Username must be at least 4 characters long")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 5, max = 10, message = "Password must be between 5 and 10 characters")
    private String password;

    @NotBlank(message = "About section cannot be blank")
    private String about;

    private Set<RoleDto> roles = new HashSet<>();
}
