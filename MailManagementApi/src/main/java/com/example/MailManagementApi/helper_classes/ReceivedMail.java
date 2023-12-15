package com.example.MailManagementApi.helper_classes;

import com.example.MailManagementApi.model.Category;
import com.example.MailManagementApi.model.Structure;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceivedMail {
    private Structure structure;
    private Category category;
    private String receivedCategory,mailReference,objectReceived;
}
