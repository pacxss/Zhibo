package com.example.lanouhn.zhibo.utils;

import com.example.lanouhn.zhibo.contants.ent_Others.Others;
import com.example.lanouhn.zhibo.contants.ent_Others.channel;
import com.example.lanouhn.zhibo.contants.ent_Others.data;
import com.example.lanouhn.zhibo.contants.ent_Others.items;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lanouhn on 2016/8/19.
 */
public class Ent_Others_JsonUtil {

    public static Others getData(String json) {

        Others others = new Others();

        try {
            JSONObject jsonObject = new JSONObject(json);

            data data = new data();
            JSONObject object = jsonObject.optJSONObject("data");

            data.setLimit(object.optInt("limit"));
            data.setOffset(object.optInt("offset"));
            data.setSort(object.optString("sort"));
            data.setTotalItems(object.optInt("totalItems"));

            JSONArray itemsArray = object.optJSONArray("items");
            List<items> itemsList = new ArrayList<>();
            for (int i = 0; i < itemsArray.length(); i++) {
                items items = new items();
                JSONObject jbjt = itemsArray.optJSONObject(i);

                items.setPreview(jbjt.optString("preview"));
                items.setViewers(jbjt.optInt("viewers"));
                if (jbjt.has("preview2"))
                    items.setPreview2(jbjt.optString("preview2"));

                channel channels = new channel();
                JSONObject jb = jbjt.optJSONObject("channel");
                channels.setId(jb.optInt("id"));
                channels.setUrl(jb.optString("url"));
                channels.setName(jb.optString("name"));
                channels.setStatus(jb.optString("status"));
                items.setChannel(channels);

//                game game = new game();
//                JSONObject j = jb.optJSONObject("game");
//                game.setId(j.optInt("id"));
//                game.setName(j.optString("name"));
//                game.setTag(j.optString("tag"));
//                items.setGame(game);

                itemsList.add(items);
            }

            data.setItems(itemsList);

            others.setData(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return others;
    }
}
