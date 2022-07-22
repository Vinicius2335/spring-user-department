package com.projetinho.userDept.model;

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
    private Long idDepartament;
    private String name;
}
