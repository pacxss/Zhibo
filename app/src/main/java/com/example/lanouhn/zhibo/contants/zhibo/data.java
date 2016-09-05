package com.example.lanouhn.zhibo.contants.zhibo;

import java.util.List;

/**
 * Created by lanouhn on 2016/8/16.
 */
public class data {

    private List<banner> banner;
    private List<quickbutton> quickbutton;
    private List<columns> columns;

    public List<com.example.lanouhn.zhibo.contants.zhibo.banner> getBanner() {
        return banner;
    }

    public void setBanner(List<com.example.lanouhn.zhibo.contants.zhibo.banner> banner) {
        this.banner = banner;
    }

    public List<com.example.lanouhn.zhibo.contants.zhibo.quickbutton> getQuickbutton() {
        return quickbutton;
    }

    public void setQuickbutton(List<com.example.lanouhn.zhibo.contants.zhibo.quickbutton> quickbutton) {
        this.quickbutton = quickbutton;
    }

    public List<com.example.lanouhn.zhibo.contants.zhibo.columns> getColumns() {
        return columns;
    }

    public void setColumns(List<com.example.lanouhn.zhibo.contants.zhibo.columns> columns) {
        this.columns = columns;
    }
}
