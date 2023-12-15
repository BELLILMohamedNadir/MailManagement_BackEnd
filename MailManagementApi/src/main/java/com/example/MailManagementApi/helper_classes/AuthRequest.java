package com.example.MailManagementApi.helper_classes;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    private String email,password;
}
