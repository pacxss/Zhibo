package com.example.lanouhn.zhibo.contants;

/**
 * Created by lanouhn on 2016/8/16.
 */
public class Contants {

    public static final int MAX_VALUE = 1314521;
    public static final int MIN_VALUE = -1314521;

    public static final String TOKEN = "oCf8AU96A3q6Bx3MhuFWbgf1j/Xnj9jp5F92A+cjz40VgcF6yT5vQRhGVVfxzygpoET334NYqSli18mWBHsrJA==";

    public static final String APP_KEY = "e5t4ouvpterma";

    //直播页面的接口
    public static String ZHIBO = "https://a4.plu.cn/api/home/personal?version=3.6.2&device=4";

    //游戏页面的接口
    public static String GAME = "https://a4.plu.cn/api/games/all?version=3.6.2&device=4";

    //随拍页面全部的接口
    public static String SUIPAI_ALL = "https://a4.plu.cn/api/ustream/home?version=3.6.2&device=4";

    //随拍页面的接口
    public static String SUIPAI = "https://a4.plu.cn/api/ustream/home?game=";

    //娱乐页面的全部的接口
    public static String ENT_ALL = "https://a4.plu.cn/api/entertainment/personal?version=3.6.2&device=4";

    //娱乐页面的接口
    public static String ENT = "https://a4.plu.cn/api/streams?start-index=0&max-results=30&game=";

    //视频播放页面主播信息的接口
    public static String VIDEOAUTHOR = "http://roomapicdn.plu.cn/room/RoomAppStatusV2?domain=";

    //直播播放页面主播信息的接口
    public static String LIVEAUTHOR = "http://liveapi.plu.cn/liveapp/roomstatus?roomId=";

    //视频播放的接口
    public static String VIDEOPLAY = "http://livestream.plu.cn/live/GetLivePlayUrl?roomId=";

    //所有房间的接口
    public static String ALL_ROOMS = "https://a4.plu.cn/api/streams?start-index=0&max-results=30&game=";

    //全部赛事的接口
    public static String ALL_MATCHES = "https://a4.plu.cn/api/matches?start-index=0&max-results=200&version=3.6.2&device=4 ";

    //主播页面贡献榜的接口
    public static String RANK = "http://roomapicdn.plu.cn/RankList/GetUserCountRankList?roomId=";

    //赛事回顾页面的接口
    public static String MATCH_AGO = "http://api.plu.cn/tga/videos?sort-by=newest&filter=4&channel=";

    //赛事回顾页面中正在直播的接口
    public static String MATCH_ING = "http://api.plu.cn/tga/streams?channel=";
}
