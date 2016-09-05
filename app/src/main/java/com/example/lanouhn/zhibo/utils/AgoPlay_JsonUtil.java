package com.example.lanouhn.zhibo.utils;

import com.example.lanouhn.zhibo.contants.agoPlay.AgoPlay;
import com.example.lanouhn.zhibo.contants.agoPlay.channel;
import com.example.lanouhn.zhibo.contants.agoPlay.data;
import com.example.lanouhn.zhibo.contants.agoPlay.items;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lanouhn on 16/9/2.
 */
public class AgoPlay_JsonUtil {

    public static AgoPlay getData(String json){

        AgoPlay agoPlay = new AgoPlay();

        try {
            JSONObject jsonObject = new JSONObject(json);

            data data = new data();
            JSONObject object = jsonObject.optJSONObject("data");

            data.setLimit(object.optInt("limit"));
            data.setOffset(object.optInt("offset"));
            data.setSort(object.optString("sort"));
            data.setTotalItems(object.optInt("totalItems"));

            List<items> itemsList = new ArrayList<>();
            JSONArray itemsArray = object.optJSONArray("items");
            for (int i = 0; i < itemsArray.length(); i++) {
                items items = new items();
                JSONObject jbjt = itemsArray.optJSONObject(i);

                items.setPreview(jbjt.optString("preview"));
                items.setViewers(jbjt.optString("viewers"));

                channel channel = new channel();
                JSONObject jb = jbjt.optJSONObject("channel");
                channel.setId(jb.optString("id"));
                channel.setStatus(jb.optString("status"));

                items.setChannel(channel);

                itemsList.add(items);
            }

            data.setItemses(itemsList);

            agoPlay.setData(data);
            agoPlay.setLastupdate(jsonObject.optInt("lastupdate"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return agoPlay;
    }
}
