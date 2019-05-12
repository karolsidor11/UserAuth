package pl.sidor.UserAuth.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private int id;
    private String name;
    private String lastName;
    private String email;
    private RoleDto role;

}
