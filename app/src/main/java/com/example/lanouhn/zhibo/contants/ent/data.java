package com.example.lanouhn.zhibo.contants.ent;

import java.util.List;

/**
 * Created by lanouhn on 2016/8/19.
 */
public class data {

    private List<banner> banner;
    private List<tabs> tabs;
    private List<columns> columns;
    private more more;

    public List<com.example.lanouhn.zhibo.contants.ent.banner> getBanner() {
        return banner;
    }

    public void setBanner(List<com.example.lanouhn.zhibo.contants.ent.banner> banner) {
        this.banner = banner;
    }

    public List<com.example.lanouhn.zhibo.contants.ent.tabs> getTabs() {
        return tabs;
    }

    public void setTabs(List<com.example.lanouhn.zhibo.contants.ent.tabs> tabs) {
        this.tabs = tabs;
    }

    public List<com.example.lanouhn.zhibo.contants.ent.columns> getColumns() {
        return columns;
    }

    public void setColumns(List<com.example.lanouhn.zhibo.contants.ent.columns> columns) {
        this.columns = columns;
    }

    public com.example.lanouhn.zhibo.contants.ent.more getMore() {
        return more;
    }

    public void setMore(com.example.lanouhn.zhibo.contants.ent.more more) {
        this.more = more;
    }
}
