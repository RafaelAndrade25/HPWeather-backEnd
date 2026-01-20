package com.HelpTapProj.backEnd.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "illness", schema = "\"hpTap\"")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Illness {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ilnessId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @Column(nullable = false)
    private String illnessName;

    @Column(name = "is_sensitive", nullable = false)
    private Boolean isSensitive;

    @Column(columnDefinition = "TEXT")
    private String notes;
}
