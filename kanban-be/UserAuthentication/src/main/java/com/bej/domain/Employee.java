package com.bej.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Employee {
    @Id
    private String userId;
    private String password;
}
