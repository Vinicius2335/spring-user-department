package com.projetinho.userDept.requests;

import com.projetinho.userDept.model.Departament;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class UserPutRequestBody {
    @Schema(description = "This is the User Id", required = true)
    private Long idUser;
    @Schema(description = "This is the User name", required = true)
    private String name;
    @Schema(description = "This is the User email", example = "example@gmail.com")
    private String email;
    private Departament departament;
}
