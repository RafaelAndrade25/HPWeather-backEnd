package com.help.tap.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "medical_record")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer medicalRecordId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @Column(name = "blood_type", nullable = false)
    private String bloodType;

    @Column(name = "height", nullable = false)
    private Integer height;

    @Column(name = "weight", nullable = false)
    private Double weight;

    @Column(name = "ethnicity", nullable = false)
    private String ethnicity;

    @Column(name = "organ_donor", nullable = false)
    private Boolean organDonor;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
}
