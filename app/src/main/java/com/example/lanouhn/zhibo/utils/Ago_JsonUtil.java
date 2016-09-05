package com.example.lanouhn.zhibo.utils;

import com.example.lanouhn.zhibo.contants.ago.Ago;
import com.example.lanouhn.zhibo.contants.ago.channel;
import com.example.lanouhn.zhibo.contants.ago.data;
import com.example.lanouhn.zhibo.contants.ago.items;
import com.example.lanouhn.zhibo.contants.ago.images;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lanouhn on 16/9/2.
 */
public class Ago_JsonUtil {

    public static Ago getData(String json){

        Ago ago = new Ago();

        try {
            JSONObject jsonObject = new JSONObject(json);

            data data = new data();
            JSONObject object = jsonObject.optJSONObject("data");

            data.setLimit(object.optInt("limit"));
            data.setOffset(object.optInt("offset"));
            data.setSort(object.optString("sort"));
            data.setTotalItems(object.optInt("totalItems"));

            List<items> itemsList = new ArrayList<>();
            JSONArray  itemsArray = object.optJSONArray("items");
            for (int i = 0; i < itemsArray.length(); i++) {
                items items = new items();
                JSONObject jbjt = itemsArray.optJSONObject(i);

                items.setViews(jbjt.optInt("views"));
                items.setComments(jbjt.optInt("comments"));
                items.setGame(jbjt.optString("game"));
                items.setGameid(jbjt.optInt("gameid"));
                items.setGametag(jbjt.optString("gametag"));
                items.setUrl(jbjt.optString("url"));
                items.setId(jbjt.optInt("id"));
                items.setCreatetime(jbjt.optLong("createtime"));
                items.setDuration(jbjt.optInt("duration"));
                items.setDescription(jbjt.optString("description"));
                items.setTags(jbjt.optString("tags"));
                items.setTitle(jbjt.optString("title"));

                JSONObject jb = jbjt.optJSONObject("channel");
                channel channel = new channel();
                channel.setUrl(jb.optString("url"));
                channel.setAvatar(jb.optString("avatar"));
                channel.setDomain(jb.optString("domain"));
                channel.setName(jb.optString("name"));
                items.setChannel(channel);

                JSONObject b = jbjt.optJSONObject("images");
                images images = new images();
                images.setNormal(b.optString("normal"));
                images.setOriginal(b.optString("original"));
                images.setThumb(b.optString("thumb"));
                items.setImages(images);

                itemsList.add(items);
            }

            data.setItemses(itemsList);
            ago.setData(data);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ago;
    }
}
