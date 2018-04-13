package cn.iamtudou.entity;

import java.io.Serializable;

public class MagnetRecord implements Serializable {

    private String title;
    private String magnetUrl;
    private String releaseDate;
    private String fileSize;
    private String sourceSite;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMagnetUrl() {
        return magnetUrl;
    }

    public void setMagnetUrl(String magnetUrl) {
        this.magnetUrl = magnetUrl;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getSourceSite() {
        return sourceSite;
    }

    public void setSourceSite(String sourceSite) {
        this.sourceSite = sourceSite;
    }

    @Override
    public String toString() {
        return "{" +
                "title='" + title + '\'' +
                ", magnetUrl='" + magnetUrl + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", fileSize='" + fileSize + '\'' +
                ", sourceSite='" + sourceSite + '\'' +
                '}';
    }
}
