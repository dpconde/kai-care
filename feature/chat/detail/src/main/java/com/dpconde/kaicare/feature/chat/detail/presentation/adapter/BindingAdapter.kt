package com.dpconde.kaicare.feature.chat.detail.presentation.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions


@BindingAdapter("imageFromUrl")
fun ImageView.bindImageFromUrl(url: String) {
    Glide.with(context)
        .load(url)
        .centerCrop()
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}
