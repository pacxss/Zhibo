package com.example.lanouhn.zhibo.utils;

import com.example.lanouhn.zhibo.contants.LiveAuthor;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lanouhn on 16/8/26.
 */
public class LiveAuthor_JsonUtil {

    public static LiveAuthor getData(String json) {

        LiveAuthor liveAuthor = new LiveAuthor();

        JSONObject object = null;
        try {
            object = new JSONObject(json);
            liveAuthor.setOnlineCount(object.optInt("onlineCount"));
            liveAuthor.setName(object.optString("name"));
            liveAuthor.setGrade(object.optInt("grade"));
            liveAuthor.setLogo(object.optString("logo"));
            liveAuthor.setSubscribeCount(object.optInt("subscribeCount"));
            liveAuthor.setDesc(object.optString("desc"));
            liveAuthor.setModel(object.optString("model"));
            liveAuthor.setBeginTime(object.optString("beginTime"));
            liveAuthor.setTitle(object.optString("title"));
            liveAuthor.setCover(object.optString("cover"));
            liveAuthor.setDomain(object.optString("domain"));
            liveAuthor.setItemCount(object.optInt("itemCount"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return liveAuthor;
    }
}
