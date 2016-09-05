package com.example.lanouhn.zhibo.contants.videoAuthor;

import java.io.Serializable;

/**
 * Created by lanouhn on 2016/8/20.
 */
public class BaseRoomInfo implements Serializable {

    private String Name;
    private String Avatar;
    private String Domain;
    private int Game;
    private String GameName;
    private int UserId;
    private String UserTitle;
    private String Desc;
    private int Type;
    private String AnchorCertification;
    private long onlineCount;
    private int AnchorCategory;
    private String BoardCastTitle;
    private String BoardCastAddress;
    private String VerifiedInformation;
    private int SubscribeCount;
    private int DailyPlayHourConfig;
    private String WriteTime;
    private int VideoPermission;
    private int LivePermission;
    private int Status;
    private int Id;

    public long getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(long onlineCount) {
        this.onlineCount = onlineCount;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getDomain() {
        return Domain;
    }

    public void setDomain(String domain) {
        Domain = domain;
    }

    public int getGame() {
        return Game;
    }

    public void setGame(int game) {
        Game = game;
    }

    public String getGameName() {
        return GameName;
    }

    public void setGameName(String gameName) {
        GameName = gameName;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getUserTitle() {
        return UserTitle;
    }

    public void setUserTitle(String userTitle) {
        UserTitle = userTitle;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getAnchorCertification() {
        return AnchorCertification;
    }

    public void setAnchorCertification(String anchorCertification) {
        AnchorCertification = anchorCertification;
    }

    public int getAnchorCategory() {
        return AnchorCategory;
    }

    public void setAnchorCategory(int anchorCategory) {
        AnchorCategory = anchorCategory;
    }

    public String getBoardCastTitle() {
        return BoardCastTitle;
    }

    public void setBoardCastTitle(String boardCastTitle) {
        BoardCastTitle = boardCastTitle;
    }

    public String getBoardCastAddress() {
        return BoardCastAddress;
    }

    public void setBoardCastAddress(String boardCastAddress) {
        BoardCastAddress = boardCastAddress;
    }

    public String getVerifiedInformation() {
        return VerifiedInformation;
    }

    public void setVerifiedInformation(String verifiedInformation) {
        VerifiedInformation = verifiedInformation;
    }

    public int getSubscribeCount() {
        return SubscribeCount;
    }

    public void setSubscribeCount(int subscribeCount) {
        SubscribeCount = subscribeCount;
    }

    public int getDailyPlayHourConfig() {
        return DailyPlayHourConfig;
    }

    public void setDailyPlayHourConfig(int dailyPlayHourConfig) {
        DailyPlayHourConfig = dailyPlayHourConfig;
    }

    public String getWriteTime() {
        return WriteTime;
    }

    public void setWriteTime(String writeTime) {
        WriteTime = writeTime;
    }

    public int getVideoPermission() {
        return VideoPermission;
    }

    public void setVideoPermission(int videoPermission) {
        VideoPermission = videoPermission;
    }

    public int getLivePermission() {
        return LivePermission;
    }

    public void setLivePermission(int livePermission) {
        LivePermission = livePermission;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
