package com.swsbt.secret.helper.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.swsbt.secret.App
import java.io.File
import java.io.FileNotFoundException

object ImageUtil {

    private val app by lazy {
        App.getInstance()
    }

    fun load(path: String, imageView: ImageView) {
        Glide.with(app!!).load(
            Uri.parse(path)
        ).into(imageView)
    }

    fun load(bm: Bitmap, imageView: ImageView) {
        Glide.with(app!!).load(bm).into(imageView)
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


    /* 通过文件管理器选择图片 */
    val SELECT_PICTURE = 1
    fun selectPic(act: AppCompatActivity) {
        act.startActivityForResult(Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
        }, SELECT_PICTURE)
    }

    /* 从uri获取Bitmap */
    fun bitmap(context: Context, uri: Uri): Bitmap? {
        val cr = context.contentResolver
        return try {
            BitmapFactory.decodeStream(cr.openInputStream(uri))
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            null
        }
    }
}