package com.study.project.domain.member;

import com.study.project.domain.plans.Plan;
import com.study.project.domain.records.Records;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId;

    @Column(name = "user_email", nullable = false)
    private String userEmail;

    @Column(name = "user_password", nullable = false)
    private String userPassword;

    //이름을 안쓰면 email에 넣은 값을 넣어주고 싶음
    @Column(name="user_name", nullable = true)
    private String userName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<Plan> planList=new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<Records> recordList=new ArrayList<>();

    @Builder
    public Member(String userEmail, String userPassword, String userName){
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userName = userName;
    }

    public void update(String userPassword, String userName){
        this.userPassword = userPassword;
        this.userName = userName;
    }

}
