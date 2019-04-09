package com.vti.base.extension.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageId")
fun setImageResource(imageView: ImageView, imgId: Int) {
    Glide.with(imageView).load(imgId).into(imageView)
}