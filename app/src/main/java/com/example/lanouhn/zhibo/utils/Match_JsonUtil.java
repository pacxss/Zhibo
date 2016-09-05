package com.example.lanouhn.zhibo.utils;


import com.example.lanouhn.zhibo.contants.match.Match;
import com.example.lanouhn.zhibo.contants.match.channel;
import com.example.lanouhn.zhibo.contants.match.data;
import com.example.lanouhn.zhibo.contants.match.game;
import com.example.lanouhn.zhibo.contants.match.image;
import com.example.lanouhn.zhibo.contants.match.imageses;
import com.example.lanouhn.zhibo.contants.match.items;
import com.example.lanouhn.zhibo.contants.match.videos;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lanouhn on 16/8/23.
 */
public class Match_JsonUtil {

    public static Match getData(String json) {

        Match match = new Match();

        try {
            JSONObject jsonObject = new JSONObject(json);

            data data = new data();
            JSONObject object = jsonObject.optJSONObject("data");

            data.setTotalItems(object.optInt("totalItems"));
            data.setOffset(object.optInt("offset"));
            data.setLimit(object.optString("limit"));

            JSONArray itemsArray = object.optJSONArray("items");
            List<items> itemsList = new ArrayList<>();
            for (int i = 0; i < itemsArray.length(); i++) {
                items items = new items();
                JSONObject j = itemsArray.optJSONObject(i);

                items.setMatchid(j.optInt("matchid"));
                items.setName(j.optString("name"));
                items.setAvtar(j.optString("avtar"));
                items.setUrl(j.optString("url"));
                items.setStarttime(j.optString("starttime"));
                items.setStoptime(j.optString("stoptime"));
                items.setArea(j.optString("area"));
                items.setTags(j.optString("tags"));
                items.setDescription(j.optString("description"));
                items.setProcess(j.optString("process"));
                items.set_process(j.optInt("_process"));
                items.setYear(j.optString("year"));
                items.set_index(j.optString("_index"));
                items.setRoomid(j.optString("roomid"));
                items.setDomain(j.optString("domain"));

                game game = new game();
                JSONObject e = j.optJSONObject("game");
                game.setId(e.optString("id"));
                game.setName(e.optString("name"));
                game.setTag(e.optString("tag"));
                items.setGame(game);

                image image = new image();
                JSONObject c = j.optJSONObject("images");
                image.setNormal(c.optString("normal"));
                image.setOriginal(c.optString("original"));
                items.setImage(image);

                videos videos = new videos();
                JSONObject t = j.optJSONObject("videos");
//                if (j.has("videos") && t.isNull("videos"))
//                    items.setVideos("");
                if (j.has("videos") && !j.isNull("videos")) {
                    videos.setViews(t.optInt("views"));
                    videos.setComments(t.optInt("comments"));
                    videos.setGame(t.optString("game"));
                    videos.setGameid(t.optInt("gameid"));
                    videos.setGametag(t.optString("gametag"));
                    videos.setUrl(t.optString("url"));
                    videos.setId(t.optInt("id"));
                    videos.setCreatetime(t.optInt("createtime"));
                    videos.setDuration(t.optInt("duration"));
                    videos.setTitle(t.optString("title"));
                    videos.setTags(t.optString("tags"));

                    channel channel = new channel();
                    JSONObject o = t.optJSONObject("channel");
                    channel.setName(o.optString("name"));
                    channel.setAvatar(o.optString("avatar"));
                    channel.setDomain(o.optString("domain"));
                    channel.setUrl(o.optString("url"));
                    videos.setChannel(channel);

                    imageses imageses = new imageses();
                    JSONObject b = t.optJSONObject("images");
                    imageses.setThumb(b.optString("thumb"));
                    imageses.setOriginal(b.optString("original"));
                    imageses.setNormal(b.optString("normal"));
                    videos.setImageses(imageses);

                    items.setVideos(videos);

                }

                itemsList.add(items);

            }

            data.setItems(itemsList);

            match.setData(data);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return match;
    }
}
