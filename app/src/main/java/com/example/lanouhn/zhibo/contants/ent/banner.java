package com.example.lanouhn.zhibo.contants.ent;

/**
 * Created by lanouhn on 2016/8/19.
 */
public class banner {

    private int id;
    private int cid;
    private String image;
    private int hrefType;
    private String hrefTarget;
    private int _index;
    private String title;
    private int ustream_cat;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getHrefType() {
        return hrefType;
    }

    public void setHrefType(int hrefType) {
        this.hrefType = hrefType;
    }

    public String getHrefTarget() {
        return hrefTarget;
    }

    public void setHrefTarget(String hrefTarget) {
        this.hrefTarget = hrefTarget;
    }

    public int get_index() {
        return _index;
    }

    public void set_index(int _index) {
        this._index = _index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUstream_cat() {
        return ustream_cat;
    }

    public void setUstream_cat(int ustream_cat) {
        this.ustream_cat = ustream_cat;
    }
}
