package com.yeahbutstill.hibernatemapping.domain;

import jakarta.persistence.Entity;

@Entity
public class OrderHeader extends BaseEntity {

    private String customer;

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderHeader that)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        return getCustomer() != null ? getCustomer().equals(that.getCustomer()) : that.getCustomer() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getCustomer() != null ? getCustomer().hashCode() : 0);
        return result;
    }
}
