package com.smart.recycler.demo.loader;

/**
 * Description: AudioModel
 * <p>
 * User: qiangzhang <br/>
 * Date: 2017/6/27 上午10:06 <br/>
 */
public class AudioModel {

    private String fileName;
    private String title;
    private int duration;
    private String singer;
    private String album;
    private String year;
    private String type;
    private String size;
    private String fileUrl;

    public AudioModel() {
    }

    public AudioModel(String fileName, String title, int duration, String singer, String album, String year,
                      String type, String size, String fileUrl) {
        this.fileName = fileName;
        this.title = title;
        this.duration = duration;
        this.singer = singer;
        this.album = album;
        this.year = year;
        this.type = type;
        this.size = size;
        this.fileUrl = fileUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Override
    public String toString() {
        return "AudioModel{" +
                "fileName='" + fileName + '\'' +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                ", singer='" + singer + '\'' +
                ", album='" + album + '\'' +
                ", year='" + year + '\'' +
                ", type='" + type + '\'' +
                ", size='" + size + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                '}';
    }
}