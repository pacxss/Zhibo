package com.example.lanouhn.zhibo.contants.game;

import java.util.List;

/**
 * Created by lanouhn on 2016/8/17.
 */
public class games {

    private int id;
    private String name;
    private String logo;
    private String tag;
    private String icon;
    private String url;
    private int parentid;
    private String subgames;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public String getSubgames() {
        return subgames;
    }

    public void setSubgames(String subgames) {
        this.subgames = subgames;
    }
}
