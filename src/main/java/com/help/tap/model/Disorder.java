package com.help.tap.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "disorder", schema = "\"hpTap\"")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Disorder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer medicalRecordId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @Column(name = "disorder_name", nullable = false)
    private String disorderName;

    @Column(name = "disorder_degree", nullable = false)
    private String disorderDegree;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
}
