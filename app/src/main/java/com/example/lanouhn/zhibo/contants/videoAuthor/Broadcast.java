package com.example.lanouhn.zhibo.contants.videoAuthor;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lanouhn on 2016/8/20.
 */
public class Broadcast implements Serializable {

    private int LiveSource;
    private int LiveStreamType;
    private int PlayId;
    private int RoomId;
    private String BeginTime;
    private int GameId;
    private String GameName;
    private int ParentGameId;
    private String ParentGameName;
    private String Title;
    private String Html;
    private List<Channels> Channels;
    private int UserId;
    private String UpStreamUrl;
    private int Longitude;
    private int Latitude;
    private int LiveSourceType;
    private String WatchDirections;
    private int OS;

    public int getLiveSource() {
        return LiveSource;
    }

    public void setLiveSource(int liveSource) {
        LiveSource = liveSource;
    }

    public int getLiveStreamType() {
        return LiveStreamType;
    }

    public void setLiveStreamType(int liveStreamType) {
        LiveStreamType = liveStreamType;
    }

    public int getPlayId() {
        return PlayId;
    }

    public void setPlayId(int playId) {
        PlayId = playId;
    }

    public int getRoomId() {
        return RoomId;
    }

    public void setRoomId(int roomId) {
        RoomId = roomId;
    }

    public String getBeginTime() {
        return BeginTime;
    }

    public void setBeginTime(String beginTime) {
        BeginTime = beginTime;
    }

    public int getGameId() {
        return GameId;
    }

    public void setGameId(int gameId) {
        GameId = gameId;
    }

    public String getGameName() {
        return GameName;
    }

    public void setGameName(String gameName) {
        GameName = gameName;
    }

    public int getParentGameId() {
        return ParentGameId;
    }

    public void setParentGameId(int parentGameId) {
        ParentGameId = parentGameId;
    }

    public String getParentGameName() {
        return ParentGameName;
    }

    public void setParentGameName(String parentGameName) {
        ParentGameName = parentGameName;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getHtml() {
        return Html;
    }

    public void setHtml(String html) {
        Html = html;
    }

    public List<com.example.lanouhn.zhibo.contants.videoAuthor.Channels> getChannels() {
        return Channels;
    }

    public void setChannels(List<com.example.lanouhn.zhibo.contants.videoAuthor.Channels> channels) {
        Channels = channels;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getUpStreamUrl() {
        return UpStreamUrl;
    }

    public void setUpStreamUrl(String upStreamUrl) {
        UpStreamUrl = upStreamUrl;
    }

    public int getLongitude() {
        return Longitude;
    }

    public void setLongitude(int longitude) {
        Longitude = longitude;
    }

    public int getLatitude() {
        return Latitude;
    }

    public void setLatitude(int latitude) {
        Latitude = latitude;
    }

    public int getLiveSourceType() {
        return LiveSourceType;
    }

    public void setLiveSourceType(int liveSourceType) {
        LiveSourceType = liveSourceType;
    }

    public String getWatchDirections() {
        return WatchDirections;
    }

    public void setWatchDirections(String watchDirections) {
        WatchDirections = watchDirections;
    }

    public int getOS() {
        return OS;
    }

    public void setOS(int OS) {
        this.OS = OS;
    }
}
