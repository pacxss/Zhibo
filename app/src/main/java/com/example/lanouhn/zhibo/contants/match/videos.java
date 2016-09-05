package com.example.lanouhn.zhibo.contants.match;

/**
 * Created by lanouhn on 16/8/22.
 */
public class videos {

    private int views;
    private int comments;
    private int gameid;
    private String game;
    private String gametag;
    private String url;
    private int id;
    private int createtime;
    private int duration;
    private String description;
    private String title;
    private String tags;

    private channel channel;
    private imageses imageses;

    public int getViews() {
        return views;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }


    public int getGameid() {
        return gameid;
    }

    public void setGameid(int gameid) {
        this.gameid = gameid;
    }

    public String getGametag() {
        return gametag;
    }

    public void setGametag(String gametag) {
        this.gametag = gametag;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCreatetime() {
        return createtime;
    }

    public void setCreatetime(int createtime) {
        this.createtime = createtime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public com.example.lanouhn.zhibo.contants.match.channel getChannel() {
        return channel;
    }

    public void setChannel(com.example.lanouhn.zhibo.contants.match.channel channel) {
        this.channel = channel;
    }

    public com.example.lanouhn.zhibo.contants.match.imageses getImageses() {
        return imageses;
    }

    public void setImageses(com.example.lanouhn.zhibo.contants.match.imageses imageses) {
        this.imageses = imageses;
    }
}
