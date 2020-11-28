package com.swsbt.secret.model.entity;

import java.util.List;

public class AvgClip {

    private String imgPath;
    private List<String> wordList;

    public AvgClip() {
    }

    public AvgClip(String imgPath, List<String> wordList) {
        this.imgPath = imgPath;
        this.wordList = wordList;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public List<String> getWordList() {
        return wordList;
    }

    public void setWordList(List<String> wordList) {
        this.wordList = wordList;
    }
}
