package com.HelpTapProj.backEnd.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "deficiency", schema = "\"hpTap\"")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Deficiency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @Column(nullable = false)
    private String type;

    @Column(columnDefinition = "TEXT")
    private String description;
}
