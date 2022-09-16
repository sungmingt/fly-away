package com.codestates.flyaway.domain.member.entity;

import com.codestates.flyaway.domain.record.entity.Record;
import com.codestates.flyaway.global.audit.Auditable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor  //
public class Member extends Auditable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<Record> records = new ArrayList<>();

    private String name;
    private String email;
    private String password;
    private long totalRecord;


    //==================


    public void addTotalRecord(long record) {
        this.totalRecord += record;
    }
}