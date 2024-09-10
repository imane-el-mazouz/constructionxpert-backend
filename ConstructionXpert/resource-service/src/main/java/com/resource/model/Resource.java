package com.resource.model;

import com.resource.enums.ResourceType;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int quantity;

    @Enumerated(EnumType.STRING)
    private ResourceType type;

    private String provider;

    @Column(name = "task_id", nullable = false)
    private Long taskId;
}
