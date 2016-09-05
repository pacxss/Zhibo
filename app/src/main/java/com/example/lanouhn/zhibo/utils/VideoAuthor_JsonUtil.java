package com.example.lanouhn.zhibo.utils;

import com.example.lanouhn.zhibo.contants.videoAuthor.BaseRoomInfo;
import com.example.lanouhn.zhibo.contants.videoAuthor.Broadcast;
import com.example.lanouhn.zhibo.contants.videoAuthor.VideoAuthor;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lanouhn on 2016/8/20.
 */
public class VideoAuthor_JsonUtil {

    public static VideoAuthor getData(String json) {

        VideoAuthor videoAuthor = new VideoAuthor();

        try {

            JSONObject jsonObject = new JSONObject(json);

            videoAuthor.setCityId(jsonObject.optInt("CityId"));
            videoAuthor.setOnlineCount(jsonObject.optInt("OnlineCount"));
            videoAuthor.setIsBroadcasting(jsonObject.optString("IsBroadcasting"));

            JSONObject object = jsonObject.optJSONObject("BaseRoomInfo");
            BaseRoomInfo baseRoomInfo = new BaseRoomInfo();
            baseRoomInfo.setName(object.optString("Name"));
            baseRoomInfo.setAvatar(object.optString("Avatar"));
            baseRoomInfo.setDomain(object.optString("Domain"));
            baseRoomInfo.setGame(object.optInt("Game"));
            baseRoomInfo.setOnlineCount(object.optLong("onlineCount"));
            baseRoomInfo.setGameName(object.optString("GameName"));
            baseRoomInfo.setUserId(object.optInt("UserId"));
            baseRoomInfo.setUserTitle(object.optString("UserTitle"));
            baseRoomInfo.setDesc(object.optString("Desc"));
            baseRoomInfo.setBoardCastTitle(object.optString("BoardCastTitle"));
            baseRoomInfo.setBoardCastAddress(object.optString("BoardCastAddress"));
            baseRoomInfo.setSubscribeCount(object.optInt("SubscribeCount"));
            baseRoomInfo.setDailyPlayHourConfig(object.optInt("DailyPlayHourConfig"));
            baseRoomInfo.setVideoPermission(object.optInt("VideoPermission"));
            baseRoomInfo.setLivePermission(object.optInt("LivePermission"));
            baseRoomInfo.setStatus(object.optInt("Status"));
            baseRoomInfo.setId(object.optInt("Id"));

            videoAuthor.setBaseRoomInfo(baseRoomInfo);


            JSONObject jbjt = jsonObject.optJSONObject("Broadcast");
            Broadcast broadcast = new Broadcast();

            broadcast.setRoomId(jbjt.getInt("RoomId"));
            broadcast.setTitle(jbjt.getString("Title"));
            videoAuthor.setBroadcast(broadcast);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return videoAuthor;
    }
}
