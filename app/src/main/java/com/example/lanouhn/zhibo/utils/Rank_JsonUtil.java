package com.example.lanouhn.zhibo.utils;

import com.example.lanouhn.zhibo.contants.Rank;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lanouhn on 16/9/1.
 */
public class Rank_JsonUtil {

    public static List<Rank.Ranks> getData(String json) {

        List<Rank.Ranks> ranksList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                Rank.Ranks ranks = new Rank.Ranks();
                JSONObject object = jsonArray.optJSONObject(i);

                ranks.setAvatar(object.optString("avatar"));
                ranks.setCount(object.optInt("count"));
                ranks.setGrade(object.optInt("grade"));
                ranks.setTrend(object.optInt("trend"));
                ranks.setUserId(object.optInt("userId"));
                ranks.setUserName(object.optString("userName"));

                ranksList.add(ranks);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ranksList;
    }
}
