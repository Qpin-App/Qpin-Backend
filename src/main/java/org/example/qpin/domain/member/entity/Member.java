package org.example.qpin.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.qpin.domain.insurance.entity.Insurance;
import org.example.qpin.domain.scrap.entity.Scrap;
import org.example.qpin.global.common.BaseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(length = 20, nullable = false)
    private String name;

    @Column
    private String password;

    private String role;

    @Column(length = 50,nullable = false)
    private String email;

    @Column(length = 30, nullable = false)
    private String phoneNumber;

    private String imageUrl;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "lastLogin")
    private LocalDateTime lastLogin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insurance_id")
    private Insurance insurance;

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<Scrap> scrapList = new ArrayList<>();

    public void setMemberInfo(String imageUrl, String name, String email, String phoneNumber, Insurance insurance) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.insurance = insurance;
    }

    @PreUpdate
    public void setLastLogin() {
        this.lastLogin = LocalDateTime.now();
    }

    public Member (String name, String password, String role){
        this.name = name;
        this.password = password;
//        this.email = email;
        this.role = role;
//        this.phoneNumber = phoneNumber;
    }

    // 주차 여부
    @Column(nullable = false)
    private boolean isParking;

    // 주차 여부를 설정
    public void setParking(boolean isParking) {
        this.isParking = isParking;
    }
}
