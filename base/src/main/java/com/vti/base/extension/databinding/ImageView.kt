package com.vti.base.extension.databinding

import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("src")
fun setImageResource(imageView: ImageView, imgId: Int) {
    Glide.with(imageView).load(imgId).into(imageView)
}

@BindingAdapter("src")
fun setImageResource(imageView: AppCompatImageView, imgId: Int) {
    Glide.with(imageView).load(imgId).into(imageView)
}