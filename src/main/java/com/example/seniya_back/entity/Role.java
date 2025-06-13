package com.example.seniya_back.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
@Getter
@NoArgsConstructor
public class    Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "role_name", nullable = false, unique = true)
    private String roleName;

    // 양방향 연관관계 설정: 하나의 Role이 여러 User를 가질 수 있음
    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude // 순환 참조 방지
    private List<User> users = new ArrayList<>();

    @Builder
    public Role(String roleName) {
        this.roleName = roleName;
    }

    // 연관관계 편의 메서드 (선택 사항)
    public void addUser(User user) {
        users.add(user);
        user.setRole(this);
    }
}