package com.example.lanouhn.zhibo.contants.agoPlay;

import java.util.List;

/**
 * Created by lanouhn on 16/9/2.
 */
public class items {

    private String preview;
    private String preview2;
    private String viewers;

    private channel channel;
    private List<game> game;

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

    public String getViewers() {
        return viewers;
    }

    public void setViewers(String viewers) {
        this.viewers = viewers;
    }

    public com.example.lanouhn.zhibo.contants.agoPlay.channel getChannel() {
        return channel;
    }

    public void setChannel(com.example.lanouhn.zhibo.contants.agoPlay.channel channel) {
        this.channel = channel;
    }

    public List<com.example.lanouhn.zhibo.contants.agoPlay.game> getGame() {
        return game;
    }

    public void setGame(List<com.example.lanouhn.zhibo.contants.agoPlay.game> game) {
        this.game = game;
    }
}
