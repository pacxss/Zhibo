package com.example.lanouhn.zhibo.contants.suipai;

/**
 * Created by lanouhn on 2016/8/18.
 */
public class channel {

    private int id;
    private String name;
    private String avatar;
    private String status;
    private long broadcast_begin;
    private String domain;
    private String tag;
    private int live_source;
    private int stream_types;
    private String stream_id;
    private stream_category stream_category;

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getBroadcast_begin() {
        return broadcast_begin;
    }

    public void setBroadcast_begin(long broadcast_begin) {
        this.broadcast_begin = broadcast_begin;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getLive_source() {
        return live_source;
    }

    public void setLive_source(int live_source) {
        this.live_source = live_source;
    }

    public int getStream_types() {
        return stream_types;
    }

    public void setStream_types(int stream_types) {
        this.stream_types = stream_types;
    }

    public String getStream_id() {
        return stream_id;
    }

    public void setStream_id(String stream_id) {
        this.stream_id = stream_id;
    }

    public com.example.lanouhn.zhibo.contants.suipai.stream_category getStream_category() {
        return stream_category;
    }

    public void setStream_category(com.example.lanouhn.zhibo.contants.suipai.stream_category stream_category) {
        this.stream_category = stream_category;
    }
}
