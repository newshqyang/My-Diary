package com.swsbt.secret.helper.binds

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.swsbt.secret.helper.utils.ImageUtil

@BindingAdapter(value = ["visibleGone"])
fun visibleGone(v: View, visible: Boolean) {
    v.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter(value = ["path"])
fun bindPath(imageView: ImageView, path: String) {
    ImageUtil.load(path, imageView)
}

/* 图片自动裁切 */
@BindingAdapter(value = ["cutPath"])
fun bindCutPath(imageView: ImageView, path: String) {
    ImageUtil.load(ImageUtil.cut(path), imageView)
}

/* 图片自动裁切 */
@BindingAdapter(value = ["cutPath200"])
fun bindCutPath2(imageView: ImageView, path: String) {
    ImageUtil.load(path, imageView)
}