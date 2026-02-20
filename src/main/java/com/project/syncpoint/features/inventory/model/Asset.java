package com.project.syncpoint.features.inventory.model;

import com.project.syncpoint.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "assets")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Asset extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String category; // e.g., "Hardware", "Software License"

    @Column(name = "serial_number", unique = true)
    private String serialNumber;

    private String status; // e.g., "AVAILABLE", "ASSIGNED"
}