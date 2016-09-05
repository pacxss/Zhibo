package com.example.lanouhn.zhibo.contants.videoAuthor;

import java.io.Serializable;

/**
 * Created by lanouhn on 2016/8/20.
 */
public class VideoAuthor implements Serializable{

    private static final long serialVersionUID = 4957203632335324754L;
    private int CityId;
    private int OnlineCount;
    private String IsBroadcasting;
    private String Vid;
    private int AppChatStatus;
    private BaseRoomInfo BaseRoomInfo;
    private Broadcast Broadcast;

    private boolean isLike;

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public int getCityId() {
        return CityId;
    }

    public void setCityId(int cityId) {
        CityId = cityId;
    }

    public int getOnlineCount() {
        return OnlineCount;
    }

    public void setOnlineCount(int onlineCount) {
        OnlineCount = onlineCount;
    }

    public String getIsBroadcasting() {
        return IsBroadcasting;
    }

    public void setIsBroadcasting(String isBroadcasting) {
        IsBroadcasting = isBroadcasting;
    }

    public String getVid() {
        return Vid;
    }

    public void setVid(String vid) {
        Vid = vid;
    }

    public int getAppChatStatus() {
        return AppChatStatus;
    }

    public void setAppChatStatus(int appChatStatus) {
        AppChatStatus = appChatStatus;
    }

    public com.example.lanouhn.zhibo.contants.videoAuthor.BaseRoomInfo getBaseRoomInfo() {
        return BaseRoomInfo;
    }

    public void setBaseRoomInfo(com.example.lanouhn.zhibo.contants.videoAuthor.BaseRoomInfo baseRoomInfo) {
        BaseRoomInfo = baseRoomInfo;
    }

    public com.example.lanouhn.zhibo.contants.videoAuthor.Broadcast getBroadcast() {
        return Broadcast;
    }

    public void setBroadcast(com.example.lanouhn.zhibo.contants.videoAuthor.Broadcast broadcast) {
        Broadcast = broadcast;
    }
}
