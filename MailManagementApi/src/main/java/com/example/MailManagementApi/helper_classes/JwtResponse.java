package com.example.MailManagementApi.helper_classes;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private String token;
    private UserResponse userResponse;

}
