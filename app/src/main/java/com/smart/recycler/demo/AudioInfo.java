package com.smart.recycler.demo;

/**
 * Created by qiangzhang on 2017/6/29.
 */

public class AudioInfo {

    private String name;
    private String url;
    private int state;
    private int progress;

    public AudioInfo(String name,String url){
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
