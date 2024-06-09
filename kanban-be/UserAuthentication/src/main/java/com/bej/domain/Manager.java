package com.bej.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Manager {
    @Id
    private String managerId;
    private String managerPassword;
}
