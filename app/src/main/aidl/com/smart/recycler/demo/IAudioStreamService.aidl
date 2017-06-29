package com.smart.recycler.demo;

interface IAudioStreamService{
    void play(String url);//
    void stop();//
    int getCurrPosition();
    long getDuration();
    int getCurrState();
    String getFileName();
}