package com.SpringSecutiryJWT.SpringSecutiryJWT.controller.request;

import com.SpringSecutiryJWT.SpringSecutiryJWT.models.RoleEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class CreateUserDTO {

    private String email;

    private String username;

    private String password;

    private Set<RoleEntity> roles;
}
