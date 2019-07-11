package com.mrmessy.messenger.graphql.dto;

import com.mrmessy.messenger.validations.PasswordsEqualConstraint;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@PasswordsEqualConstraint(message = "passwords are not equal")
public class UserMutationDto implements Serializable {
//    @NotNull(message = "User id is required", groups = {UpdateModeValidation.class})
    private Integer id;

    @NotEmpty(message = "First name is required")
    private String email;

    @NotEmpty(message = "Password is required")
    @Size(min = 6, max = 30)
    private String password;

    @NotEmpty(message = "ConfirmPassword is required")
    @Size(min = 6, max = 30)
    private String confirmPassword;

    private Integer role;

    @NotEmpty(message = "First name is required")
    private String firstName;

    @NotEmpty(message = "Last name is required")
    private String lastName;


    public interface UpdateModeValidation {
    }
}
