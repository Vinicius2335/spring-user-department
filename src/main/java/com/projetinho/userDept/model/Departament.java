package com.projetinho.userDept.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;

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
    @Schema(description = "The departament name")
    private String name;
}
