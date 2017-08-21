package com.smart.recycler.demo.loader;

/**
 * Description: AudioModel
 * <p>
 * User: qiangzhang <br/>
 * Date: 2017/6/27 上午10:06 <br/>
 */
public class VideoModel {

    private String filePath;
    private String mimeType;
    private String thumbPath;
    private String title;

    public VideoModel() {
    }

    public VideoModel(String filePath, String mimeType, String thumbPath, String title) {
        this.filePath = filePath;
        this.mimeType = mimeType;
        this.thumbPath = thumbPath;
        this.title = title;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "VideoModel{" +
                "filePath='" + filePath + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", thumbPath='" + thumbPath + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
