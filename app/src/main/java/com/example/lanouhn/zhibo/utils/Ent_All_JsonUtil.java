package com.example.lanouhn.zhibo.utils;

import com.example.lanouhn.zhibo.contants.ent.Ent;
import com.example.lanouhn.zhibo.contants.ent.banner;
import com.example.lanouhn.zhibo.contants.ent.channel;
import com.example.lanouhn.zhibo.contants.ent.channels;
import com.example.lanouhn.zhibo.contants.ent.columns;
import com.example.lanouhn.zhibo.contants.ent.data;
import com.example.lanouhn.zhibo.contants.ent.game;
import com.example.lanouhn.zhibo.contants.ent.more;
import com.example.lanouhn.zhibo.contants.ent.rooms;
import com.example.lanouhn.zhibo.contants.ent.roomses;
import com.example.lanouhn.zhibo.contants.ent.tabs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lanouhn on 2016/8/19.
 */
public class Ent_All_JsonUtil {

    public static Ent getData(String json) {

        Ent ent = new Ent();

        try {
            JSONObject jsonObject = new JSONObject(json);

            data data = new data();
            JSONObject object = jsonObject.optJSONObject("data");

            JSONArray bannerArray = object.optJSONArray("banner");
            List<banner> bannerList = new ArrayList<>();
            for (int x = 0; x < bannerArray.length(); x++) {
                banner banner = new banner();
                JSONObject o = bannerArray.optJSONObject(x);

                banner.setId(o.optInt("id"));
                banner.setCid(o.optInt("cid"));
                banner.setImage(o.optString("image"));
                banner.setHrefType(o.optInt("hrefType"));
                banner.setHrefTarget(o.optString("hrefTarget"));
                banner.set_index(o.optInt("_index"));
                banner.setTitle(o.optString("title"));
                banner.setUstream_cat(o.optInt("ustream_cat"));

                bannerList.add(banner);
            }
            data.setBanner(bannerList);

            JSONArray tabsArray = object.optJSONArray("tabs");
            List<tabs> tabsList = new ArrayList<>();
            tabs tabs = new tabs();
            tabs.setName("全部");
            tabsList.add(tabs);
            for (int y = 0; y < tabsArray.length(); y++) {
                tabs tab = new tabs();
                JSONObject b = tabsArray.optJSONObject(y);

                tab.setId(b.optInt("id"));
                tab.setName(b.optString("name"));
                tab.setIcon(b.optString("icon"));
                tab.set_index(b.optString("_index"));
                tab.setTemplate(b.optString("template"));
                tab.setTag(b.optString("tag"));
                tab.setSortby(b.optString("sortby"));

                tabsList.add(tab);
            }
            data.setTabs(tabsList);

            JSONArray columnsArray = object.optJSONArray("columns");
            List<columns> columnsList = new ArrayList<>();
            for (int z = 0; z < columnsArray.length(); z++) {
                columns columns = new columns();
                JSONObject j = columnsArray.optJSONObject(z);

                columns.setChannelsText(j.optString("channelsText"));

                JSONObject e = j.optJSONObject("game");
                game game = new game();
                game.setTitle(e.optString("title"));
                game.setIcon(e.optString("icon"));
                game.setSortby(e.optString("sortby"));
                game.setId(e.optInt("id"));
                game.setName(e.optString("name"));
                game.setTag(e.optString("tag"));
                columns.setGame(game);

                JSONArray roomsArray = j.optJSONArray("rooms");
                List<rooms> roomsList = new ArrayList<>();
                for (int i = 0; i < roomsArray.length(); i++) {
                    rooms rooms = new rooms();
                    JSONObject c = roomsArray.optJSONObject(i);

                    rooms.setPreview(c.optString("preview"));
                    rooms.setViewers(c.optInt("viewers"));
                    if (c.has("preview2"))
                        rooms.setPreview2(c.optString("preview2"));

                    channel channel = new channel();
                    JSONObject t = c.optJSONObject("channel");
                    channel.setId(t.optInt("id"));
                    channel.setUrl(t.optInt("url"));
                    channel.setName(t.optString("name"));
                    channel.setStatus(t.optString("status"));
                    rooms.setChannel(channel);

                    roomsList.add(rooms);
                }
                columns.setRooms(roomsList);

                columnsList.add(columns);
            }
            data.setColumns(columnsList);

            JSONObject jbjt = object.optJSONObject("more");
            more more = new more();
            more.setChannels(jbjt.optInt("channels"));
            more.setChannelsText(jbjt.optString("channelsText"));

            JSONArray roomsArray = jbjt.getJSONArray("rooms");
            List<roomses> roomsLists = new ArrayList<>();
            for (int i = 0; i < roomsArray.length(); i++) {
                roomses room = new roomses();
                JSONObject c = roomsArray.optJSONObject(i);

                room.setPreview(c.optString("preview"));
                room.setViewers(c.optInt("viewers"));
                if (c.has("preview2"))
                    room.setPreview2(c.optString("preview2"));

                channels channels = new channels();
                JSONObject t = c.optJSONObject("channel");
                channels.setId(t.optInt("id"));
                channels.setUrl(t.optInt("url"));
                channels.setName(t.optString("name"));
                channels.setStatus(t.optString("status"));
                room.setChannel(channels);

                roomsLists.add(room);
            }
            more.setRoomses(roomsLists);

            data.setMore(more);

            ent.setData(data);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ent;
    }
}
