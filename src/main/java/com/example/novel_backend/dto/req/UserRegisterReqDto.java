package com.example.novel_backend.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 *
 * User Registration Request DTO
 * @author :Contanstin
 * @create 2023/6/11 22:36
 */
@Data
public class UserRegisterReqDto {

    @Schema(description = "Email", required = true)
    @NotBlank(message="Email cannot be empty！")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Invalid email format")
    private String email;

    @Schema(description = "Username", required = true)
    @NotBlank(message="Username cannot be empty！")
    private String username;

    @Schema(description = "Password", required = true)
    @NotBlank(message="Password cannot be empty！！")
    private String password;

    @Schema(description = "Verification Code", required = true)
    @NotBlank(message="Verification Code cannot be empty！")
    @Pattern(regexp="^\\d{4}$",message="Invalid email format")
    private String velCode;

}
