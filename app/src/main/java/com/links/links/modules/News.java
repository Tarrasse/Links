package com.links.links.modules;

/**
 * Created by mahmoud on 9/20/2016.
 */
public class News {


    /**
     * _id : 1
     * title : title
     * body : body
     * img_link : link
     */

    private String _id;
    private String title;
    private String body;
    private String img_link;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImg_link() {
        return img_link;
    }

    public void setImg_link(String img_link) {
        this.img_link = img_link;
    }
}
