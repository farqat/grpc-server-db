package com.example.grpcserverdb.model;


import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "person")
@ToString
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;
    private Short age;
    private Integer orgid;

    public Person(String firstname, String lastname, Short age, Integer orgid) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.orgid = orgid;
    }
}
