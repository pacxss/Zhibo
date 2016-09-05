package com.example.lanouhn.zhibo.contants.ago;

import java.util.List;

/**
 * Created by lanouhn on 16/9/2.
 */
public class data {

    private int totalItems;
    private int offset;
    private int limit;
    private String sort;
    private List<items> itemses;

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public List<items> getItemses() {
        return itemses;
    }

    public void setItemses(List<items> itemses) {
        this.itemses = itemses;
    }
}
