package com.yeahbutstill.hibernatemapping.domain;

import jakarta.persistence.Entity;


@Entity
public class OrderApproval extends BaseEntity {

    private String approvedBy;

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }
}
