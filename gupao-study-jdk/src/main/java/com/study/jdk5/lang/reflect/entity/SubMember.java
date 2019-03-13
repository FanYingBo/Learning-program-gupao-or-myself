package com.study.jdk5.lang.reflect.entity;

import com.study.jdk5.lang.reflect.entity.anno.EntityDemoAlias;

@EntityDemoAlias("subMember")
public class SubMember extends Member {

    private String clubName;

    private Integer clubId;

    private boolean isNew;

    public String getClubName() {
        return clubName;
    }
    public Integer getClubId() {
        return clubId;
    }
    public boolean isNew() {
        return isNew;
    }


    @Override
    public String join(String clubname, Integer clubId, boolean isNew) {
        this.clubName = clubName;
        this.clubId = clubId;
        this.isNew = isNew;
        return super.join(clubname, clubId, isNew);
    }
}
