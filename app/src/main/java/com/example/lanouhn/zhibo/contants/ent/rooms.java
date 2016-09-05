package com.example.lanouhn.zhibo.contants.ent;

/**
 * Created by lanouhn on 2016/8/19.
 */
public class rooms {

    private String preview;
    private String preview2;
    private int viewers;
    private games game;
    private channel channel;

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getPreview2() {
        return preview2;
    }

    public void setPreview2(String preview2) {
        this.preview2 = preview2;
    }

    public int getViewers() {
        return viewers;
    }

    public void setViewers(int viewers) {
        this.viewers = viewers;
    }

    public games getGame() {
        return game;
    }

    public void setGame(games game) {
        this.game = game;
    }

    public com.example.lanouhn.zhibo.contants.ent.channel getChannel() {
        return channel;
    }

    public void setChannel(com.example.lanouhn.zhibo.contants.ent.channel channel) {
        this.channel = channel;
    }
}
