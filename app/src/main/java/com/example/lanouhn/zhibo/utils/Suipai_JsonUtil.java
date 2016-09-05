package com.example.lanouhn.zhibo.utils;

import com.example.lanouhn.zhibo.contants.suipai.Suipai;
import com.example.lanouhn.zhibo.contants.suipai.channel;
import com.example.lanouhn.zhibo.contants.suipai.data;
import com.example.lanouhn.zhibo.contants.suipai.items;
import com.example.lanouhn.zhibo.contants.suipai.stream_category;
import com.example.lanouhn.zhibo.contants.suipai.streams;
import com.example.lanouhn.zhibo.contants.suipai.tabs;
import com.example.lanouhn.zhibo.contants.suipai.user;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lanouhn on 2016/8/18.
 */
public class Suipai_JsonUtil {

    public static Suipai getData(String json) {

        Suipai suipai = new Suipai();

        try {
            JSONObject jsonObject = new JSONObject(json);

            data data = new data();
            JSONObject object = jsonObject.getJSONObject("data");

            JSONArray tabsArray = object.getJSONArray("tabs");
            List<tabs> tabsList = new ArrayList<>();
            tabs tabs = new tabs();
            tabs.setId(0);
            tabs.setName("全部");
            tabsList.add(tabs);
            for (int x = 0; x < tabsArray.length(); x++) {
                tabs tab = new tabs();
                JSONObject o = tabsArray.getJSONObject(x);

                tab.setName(o.getString("name"));
                tab.setId(o.getInt("id"));

                tabsList.add(tab);
            }
            data.setTabs(tabsList);

            JSONObject b = object.getJSONObject("streams");
            streams streams = new streams();

            streams.setTotalItems(b.getInt("totalItems"));

            JSONArray itemsArray = b.getJSONArray("items");
            List<items> itemsList = new ArrayList<>();
            for (int z = 0; z < itemsArray.length(); z++) {
                items items = new items();
                JSONObject j = itemsArray.getJSONObject(z);

                items.setWeight(j.getInt("weight"));
                items.setPreview(j.getString("preview"));
                items.setViewers(j.getInt("viewers"));
                items.setAdmire(j.getInt("admire"));

                JSONObject e = j.getJSONObject("channel");
                channel channel = new channel();
                channel.setId(e.getInt("id"));
                channel.setName(e.getString("name"));
                channel.setAvatar(e.getString("avatar"));
                channel.setStatus(e.getString("status"));
                channel.setBroadcast_begin(e.getLong("broadcast_begin"));
                channel.setDomain(e.getString("domain"));
                channel.setLive_source(e.getInt("live_source"));
                channel.setStream_types(e.getInt("stream_types"));
                channel.setStream_id(e.getString("stream_id"));

                JSONObject c = e.getJSONObject("stream_category");
                stream_category stream_category = new stream_category();
                stream_category.setId(c.getInt("id"));
                stream_category.setName(c.getString("name"));
                stream_category.setTag(c.getString("tag"));

                channel.setStream_category(stream_category);

                items.setChannel(channel);

                JSONObject t = j.getJSONObject("user");
                user user = new user();
                user.setName(t.getString("name"));
                user.setAvatar(t.getString("avatar"));
                user.setUid(t.getInt("uid"));

                items.setUser(user);

                itemsList.add(items);
            }

            streams.setItems(itemsList);

            data.setStreams(streams);

            suipai.setData(data);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return suipai;
    }
}
