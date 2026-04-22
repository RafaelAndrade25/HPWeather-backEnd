package com.help.tap.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "emergency_contact")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmergencyContact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer contactId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @Column(nullable = false)
    private String phone;

    @Column(name = "phone_owner", nullable = false)
    private String phoneOwner;

    @Column(name = "degreeOfKinship", nullable = false)
    private String degreeOfKinship;
}
