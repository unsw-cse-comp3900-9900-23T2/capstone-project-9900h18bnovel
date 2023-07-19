package com.example.novel_backend.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * User Login Request DTO
 *
 * @author :Contanstin
 * @create 2023/6/13 13:47
 */
@Data
public class UserLoginReqDto {

    @Schema(description = "Email", required = true)
    @NotBlank(message = "Can not be empty!")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Invalid email format")
    private String email;

    @Schema(description = "Password", required = true, example = "123456")
    @NotBlank(message = "Password cannot be empty！")
    private String password;

    @Schema(description = "Verification Code", required = true)
    @NotBlank(message="Verification Code cannot be empty！")
    @Pattern(regexp="^\\d{4}$",message="Invalid email format")
    private String velCode;

    /**
     * Request session identifier,
     * which identifies the session to which the graphic verification code belongs
     * */
    @Schema(description = "sessionId", required = true)
    @NotBlank
    @Length(min = 32,max = 32)
    private String sessionId;

}
