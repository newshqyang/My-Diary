package com.swsbt.secret.helper.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.File

object ImageUtil {

    fun load(path: String, imageView: ImageView) {
        Glide.with(BaseUtil.context).load(File(path)).into(imageView)
    }

    fun load(bm: Bitmap, imageView: ImageView) {
        Glide.with(BaseUtil.context).load(bm).into(imageView)
    }


    fun getBitmap(path: String) = BitmapFactory.decodeFile(path)


    fun cut(path: String) = cut(getBitmap(path))

    fun cut(path: String, maxWidth: Int) = cut(getBitmap(path), maxWidth)

    fun cut(bm: Bitmap): Bitmap {
        val width: Int = bm.getWidth()
        val height: Int = bm.getHeight()
        var squareWidth = if (width > height) height else width
        squareWidth /= 2
        squareWidth = if (squareWidth == 0) 1 else squareWidth
        return Bitmap.createBitmap(bm, width / 2, height / 2, squareWidth, squareWidth)
    }

    fun cut(bm: Bitmap, maxWidth: Int): Bitmap {
        val width: Int = bm.getWidth()
        val height: Int = bm.getHeight()
        var squareWidth = if (width > height) height else width
        squareWidth /= 2
        if (squareWidth > maxWidth) {        //不可大于maxWidth
            squareWidth = maxWidth
        }
        squareWidth = if (squareWidth == 0) 1 else squareWidth
        return Bitmap.createBitmap(bm, width / 2, height / 2, squareWidth, squareWidth)
    }
}