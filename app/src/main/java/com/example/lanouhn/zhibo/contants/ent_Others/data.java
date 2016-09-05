package com.example.lanouhn.zhibo.contants.ent_Others;

import java.util.List;

/**
 * Created by lanouhn on 2016/8/19.
 */
public class data {
    private int totalItems;
    private int offset;
    private int limit;
    private String sort;
    private List<items> items;

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

    public List<com.example.lanouhn.zhibo.contants.ent_Others.items> getItems() {
        return items;
    }

    public void setItems(List<com.example.lanouhn.zhibo.contants.ent_Others.items> items) {
        this.items = items;
    }
}
