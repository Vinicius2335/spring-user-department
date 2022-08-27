package com.projetinho.userDept.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tb_department")
public class Departament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "The departament ID", required = true)
    private Long idDepartament;

    @NotEmpty(message = "The departament name cannot be empty")
    @Schema(description = "The departament name")
    private String name;
}
