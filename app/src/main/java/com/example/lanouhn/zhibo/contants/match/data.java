package com.example.lanouhn.zhibo.contants.match;

import java.util.List;

/**
 * Created by lanouhn on 16/8/22.
 */
public class data {
    private String limit;
    private int totalItems;
    private int offset;

    private List<items> items;

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

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

    public List<com.example.lanouhn.zhibo.contants.match.items> getItems() {
        return items;
    }

    public void setItems(List<com.example.lanouhn.zhibo.contants.match.items> items) {
        this.items = items;
    }
}
