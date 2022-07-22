package com.projetinho.userDept.model;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tb_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;
    private String name;
    private String email;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Departament departament;
}
