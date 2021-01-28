package com.swsbt.secret.helper.utils;

import android.graphics.Bitmap;

public class ImageUtils {

    /*
    裁切图片
     */
    public static Bitmap cutBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int squareWidth = width > height ? height : width;
        squareWidth /= 2;
        squareWidth = squareWidth == 0 ? 1 : squareWidth;
        return Bitmap.createBitmap(bitmap, width / 2, height / 2, squareWidth, squareWidth);
    }

    /*
    按指定宽度裁切图片
     */
    public static Bitmap cutBitmap(Bitmap bitmap, int w) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        return Bitmap.createBitmap(bitmap, width / 2, height / 2, w, w);
    }

}
