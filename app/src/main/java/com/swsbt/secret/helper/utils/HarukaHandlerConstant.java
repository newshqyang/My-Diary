package com.swsbt.secret.helper.utils;

public class HarukaHandlerConstant {

    /**
     * 与下载有关的
     */
    public static final int DOWNLOAD_DONE = 2100;    // 下载完成
    public static final int DOWNLOAD_START = 2001; // 已开始下载
    public static final int DOWNLOAD_CANCEL = 2000; // 下载取消
    public static final int REFRESH_DOWNLOAD_LIST = 2200;    // 刷新下载队列

    /**
     * 与文件浏览器有关的
     */
    public static final int OPEN_FOLDER = 1001;   // 打开文件夹
    public static final int SHOW_QrCODE = 1002;   // 展示二维码
    public static final int REFRESH_INDEX_AND_EXPLORER = 1005;  // 刷新index列表和文件资源浏览器

    /**
     * 与搜索有关
     */
    public static final int SEARCH_SHOW_SAMPLE = 3001;  // 显示样例
    public static final int SEARCH_SHOW_RESULT = 3002;  // 显示搜索结果
    public static final int OPEN_SAMPLE_FOLDER = 3003;  // 打开样例文件夹

    /**
     * 与云文件夹有关
     */
    public static final int LOGIN_SUCCESS = 4001;   // 登陆成功
    public static final int SHOW_CLOUD_MENU = 4002; // 显示菜单
    public static final int SHOW_CLOUD_SHARE_CARD = 4003;   // 显示分享的文字提示卡片
    public static final int SHOW_CLOUD_DOWNLOAD_CARD = 4004;    // 显示下载卡片
    public static final int CLOUD_DOWNLOAD_FILE = 4005; // 下载文件
    public static final int REFRESH_CLOUD_UPLOAD_PROGRESS = 4006;   // 刷新上传进度
    public static final int FINISH_ACTIVITY = 4007;    // 关闭activity
}
