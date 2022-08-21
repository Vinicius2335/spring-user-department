package com.projetinho.userDept.requests;

import com.projetinho.userDept.model.Departament;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPutRequestBody {
    @Schema(description = "This is the User Id", required = true)
    private Long idUser;
    @Schema(description = "This is the User name")
    private String name;
    @Schema(description = "This is the User email", example = "example@gmail.com")
    private String email;
    private Departament departament;
}
