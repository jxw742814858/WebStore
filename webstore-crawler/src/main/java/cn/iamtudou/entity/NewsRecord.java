package cn.iamtudou.entity;

import java.io.Serializable;

/**
 * 资讯实体
 */
public class NewsRecord implements Serializable {

    private String id;
    private String url;
    private String title;
    private String cotent;
    private String[] img_url; //页面图片链接
    private long timestamp;
    private String site; //来源网站
    private String type; //所属分类
    private String[] img_path; //图片保存地址
    private long createtime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCotent() {
        return cotent;
    }

    public void setCotent(String cotent) {
        this.cotent = cotent;
    }

    public String[] getImg_url() {
        return img_url;
    }

    public void setImg_url(String[] img_url) {
        this.img_url = img_url;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getImg_path() {
        return img_path;
    }

    public void setImg_path(String[] img_path) {
        this.img_path = img_path;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }
}
