package com.mrmessy.messenger.graphql.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class UserMutationDto {
    @NotNull(message = "User id is required", groups = {UpdateModeValidation.class})
    private Integer id;

    @NotEmpty(message = "First name is required")
    private String email;

    @NotEmpty(message = "Password is required")
    private String password;

    @NotEmpty(message = "ConfirmPassword is required")
    private String confirmPassword;

    private Integer role;

    @NotEmpty(message = "First name is required")
    private String firstName;

    @NotEmpty(message = "Last name is required")
    private String lastName;


    public interface UpdateModeValidation {
    }
}
