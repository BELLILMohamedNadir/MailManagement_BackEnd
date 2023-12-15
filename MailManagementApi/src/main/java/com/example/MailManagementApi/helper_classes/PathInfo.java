package com.example.MailManagementApi.helper_classes;


import com.example.MailManagementApi.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PathInfo {

    // when we update the mail we need this data,
    // so we create this class to avoid bugs when we send null field
    // from the client side  it's just a helper class

    private User user;
    private String path,internalReference;
}
