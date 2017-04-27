package com.softwareengg.project.notifyme.PromoListFragment.PromoListOperations;

import java.io.Serializable;

/**
 * Created by shivanshu on 26/04/17.
 *
 * Purpose
 * - Container for the sorting criteria and the direction.
 */

public class Sort implements Serializable {
    int criteria;
    int order;

    public Sort() {}

    public Sort(int criteria, int ascending) {
        this.criteria = criteria;
        this.order = ascending;
    }

    public int getCriteria() {
        return criteria;
    }

    public void setCriteria(int criteria) {
        this.criteria = criteria;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
