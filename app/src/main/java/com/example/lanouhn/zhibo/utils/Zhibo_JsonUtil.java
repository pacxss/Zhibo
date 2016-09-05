package com.example.lanouhn.zhibo.utils;

import com.example.lanouhn.zhibo.contants.zhibo.Zhibo;
import com.example.lanouhn.zhibo.contants.zhibo.banner;
import com.example.lanouhn.zhibo.contants.zhibo.channel;
import com.example.lanouhn.zhibo.contants.zhibo.columns;
import com.example.lanouhn.zhibo.contants.zhibo.data;
import com.example.lanouhn.zhibo.contants.zhibo.game;
import com.example.lanouhn.zhibo.contants.zhibo.games;
import com.example.lanouhn.zhibo.contants.zhibo.quickbutton;
import com.example.lanouhn.zhibo.contants.zhibo.rooms;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 直播页面的数据解析
 * Created by lanouhn on 2016/8/17.
 */
public class Zhibo_JsonUtil {

    public static Zhibo getData(String json) {

        Zhibo zhibo = new Zhibo();

        try {
            JSONObject jsonObject = new JSONObject(json);

            data data = new data();
            JSONObject object = jsonObject.optJSONObject("data");

            JSONArray bannerArray = object.optJSONArray("banner");
            List<banner> bannerList = new ArrayList<>();
            for (int x = 0; x < bannerArray.length(); x++) {
                banner banner = new banner();
                JSONObject o = bannerArray.optJSONObject(x);

                banner.setImage(o.optString("image"));
                banner.setTitle(o.optString("title"));
                banner.setHrefTarget(o.optString("hrefTarget"));

                bannerList.add(banner);
            }

            data.setBanner(bannerList);

            JSONArray quickbuttonArray = object.optJSONArray("quickbutton");
            List<quickbutton> quickbuttonList = new ArrayList<>();
            for (int y = 0; y < quickbuttonArray.length(); y++) {
                quickbutton quickbutton = new quickbutton();
                JSONObject b = quickbuttonArray.optJSONObject(y);

                quickbutton.setImage(b.optString("image"));
                quickbutton.setHrefTarget(b.optString("hrefTarget"));
                quickbutton.setTitle(b.optString("title"));

                quickbuttonList.add(quickbutton);
            }

            data.setQuickbutton(quickbuttonList);

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
                game.setId(e.optInt("id"));
                game.setTag(e.optString("tag"));
                game.setSortby(e.optString("sortby"));
                game.setName(e.optString("name"));
                game.setTemplate(e.optString("template"));
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
                    if (t.has("url"))
                        channel.setUrl(t.optInt("url"));
                    else
                        channel.setUrl(0);
                    channel.setName(t.optString("name"));
                    channel.setStatus(t.optString("status"));
                    rooms.setChannel(channel);

//                    games games = new games();
//                    JSONObject jb = c.optJSONObject("game");
//                    games.setTag(jb.optString("tag"));
//                    games.setId(jb.optInt("id"));
//                    games.setName(jb.optString("name"));
//                    rooms.setGames(games);

                    roomsList.add(rooms);
                }
                columns.setRooms(roomsList);

                columnsList.add(columns);
            }

            data.setColumns(columnsList);

            zhibo.setData(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return zhibo;
    }
}
