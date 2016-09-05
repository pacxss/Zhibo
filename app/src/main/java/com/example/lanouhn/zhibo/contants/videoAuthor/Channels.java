package com.example.lanouhn.zhibo.contants.videoAuthor;

import java.io.Serializable;

/**
 * Created by lanouhn on 2016/8/20.
 */
public class Channels implements Serializable {

    private String Name;
    private String Code;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
