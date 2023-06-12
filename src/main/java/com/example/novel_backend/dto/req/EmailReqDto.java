package com.example.novel_backend.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * @author :Contanstin
 * @create 2023/6/12 19:32
 */
@Data
public class EmailReqDto {

    @Schema(description = "Email", required = true)
    @NotBlank(message="Email cannot be empty！")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Invalid email format")
    private String email;
}
