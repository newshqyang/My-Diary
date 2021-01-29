package com.swsbt.secret.binds

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.swsbt.secret.helper.utils.ImageUtil

object NormalBinds {

    @JvmStatic
    @BindingAdapter(value = ["visibleGone"])
    fun visibleGone(v: View, visible: Boolean) {
        if (visible) v.visibility = View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter(value = ["path"])
    fun bindPath(imageView: ImageView, path: String) {
        ImageUtil.load(path, imageView)
    }

    /* 图片自动裁切 */
    @JvmStatic
    @BindingAdapter(value = ["cutPath"])
    fun bindCutPath(imageView: ImageView, path: String) {
        ImageUtil.load(ImageUtil.cut(path), imageView)
    }

    /* 图片自动裁切 */
    @JvmStatic
    @BindingAdapter(value = ["cutPath200"])
    fun bindCutPath2(imageView: ImageView, path: String) {
        ImageUtil.load(ImageUtil.cut(path, 200), imageView)
    }

}