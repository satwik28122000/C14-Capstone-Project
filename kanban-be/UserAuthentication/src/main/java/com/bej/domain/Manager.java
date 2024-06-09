package com.bej.domain;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Manager {
    @Id
    private String managerId;
    private String managerPassword;
}
