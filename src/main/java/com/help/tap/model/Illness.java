package com.help.tap.model;

import com.help.tap.infra.security.EncryptionUtil;
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

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private RiskRating riskRating;

    @Transient
    private transient EncryptionUtil encryptionUtil;

    public void setName(String illnessName) throws Exception {
        if (this.isSensitive != null && this.isSensitive && encryptionUtil != null){
            this.illnessName = encryptionUtil.encrypt(illnessName);
        } else {
            this.illnessName = illnessName;
        }
    }

    public void setNotes(String notes) throws Exception {
        if (this.isSensitive != null && this.isSensitive && encryptionUtil != null){
            this.notes = encryptionUtil.encrypt(notes);
        } else {
            this.notes = notes;
        }
    }

    public String getDecryptedName() throws Exception {
        if (this.isSensitive != null && this.isSensitive && encryptionUtil != null){
            return encryptionUtil.decrypt(this.illnessName);
        }
        return this.illnessName;
    }

    public String getDecryptedNotes() throws Exception {
        if (this.isSensitive != null && this.isSensitive && encryptionUtil != null){
            return encryptionUtil.decrypt(this.notes);
        }
        return this.notes;
    }
}
