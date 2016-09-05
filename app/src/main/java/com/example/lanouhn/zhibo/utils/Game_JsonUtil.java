package com.example.lanouhn.zhibo.utils;

import com.example.lanouhn.zhibo.contants.game.Game;
import com.example.lanouhn.zhibo.contants.game.data;
import com.example.lanouhn.zhibo.contants.game.games;
import com.example.lanouhn.zhibo.contants.game.items;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 游戏页面的数据解析
 * Created by lanouhn on 2016/8/17.
 */
public class Game_JsonUtil {

    public static Game getData(String json){

        Game game = new Game();

        try {
            JSONObject jsonObject = new JSONObject(json);

            data data = new data();
            JSONObject object = jsonObject.optJSONObject("data");
            data.setTotalItems(object.optInt("totalItems"));

            JSONArray itemArray = object.optJSONArray("items");
            List<items> itemList = new ArrayList<>();
            for (int i = 0; i < itemArray.length(); i++) {
                items items = new items();
                JSONObject jbjt = itemArray.optJSONObject(i);

                items.setViewers(jbjt.optInt("viewers"));
                items.setChannels(jbjt.optInt("channels"));

                JSONObject jb = jbjt.optJSONObject("game");
                games games = new games();
                games.setId(jb.optInt("id"));
                games.setLogo(jb.optString("logo"));
                games.setName(jb.optString("name"));
                games.setTag(jb.optString("tag"));
                if (jb.has("icon"))
                    games.setIcon(jb.optString("icon"));
                if (jb.has("url"))
                    games.setUrl(jb.optString("url"));
                if (jb.has("parentid"))
                    games.setParentid(jb.optInt("parentid"));
                if (jb.has("subgames"))
                    games.setSubgames(jb.optString("subgames"));
                items.setGame(games);

                itemList.add(items);
            }
            data.setItems(itemList);

            game.setData(data);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return game;
    }
}
