package com.example.novel_backend.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * Reset Password Request DTO
 *
 * @author :Contanstin
 * @create 2023/6/14 13:44
 */
@Data
public class ResetPasswordReqDto {

    @Schema(description = "Email", required = true)
    @NotBlank(message="Email cannot be empty！")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Invalid email format")
    private String email;

    @Schema(description = "Verification Code", required = true)
    @NotBlank(message="Verification Code cannot be empty！")
    @Pattern(regexp="^\\d{6}$",message="Invalid email format")
    private String velCode;

    @Schema(description = "New password", required = true)
    @NotBlank(message="New password cannot be empty！")
    private String newPassword;
}
