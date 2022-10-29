package com.dpconde.feature.chat.directory.presentation.adapter

import android.content.res.ColorStateList
import android.graphics.Typeface
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dpconde.feature.chat.directory.domain.entities.MessageThread
import java.text.SimpleDateFormat
import java.util.*


@BindingAdapter("imageFromUrl")
fun ImageView.bindImageFromUrl(thread: MessageThread) {
    Glide.with(context)
        .load(thread.imageUrl)
        .centerCrop()
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

@BindingAdapter("showNotificationBadge")
fun TextView.bindShowNotificationBadge(thread: MessageThread) {
    visibility = when(thread.unreadMessages){
        0 -> View.GONE
        else -> View.VISIBLE
    }
}

@BindingAdapter("showProfileBadge")
fun TextView.bindShowProfileBadge(thread: MessageThread) {
    visibility = when(thread.role){
        "professional" -> View.VISIBLE
        else -> View.GONE
    }
}


@BindingAdapter("lastMessageDate")
fun TextView.bindLastMessageDate(thread: MessageThread) {
    thread.lastMessageDate?.let {
        text = SimpleDateFormat(
            "HH:mm", Locale.getDefault())
            .format(it)
    }

    if (thread.unreadMessages != null && thread.unreadMessages!! > 0){
        val colorInt = resources.getColor(com.dpconde.kaicare.R.color.md_theme_light_primary)
        val csl = ColorStateList.valueOf(colorInt)
        setTypeface(null, Typeface.BOLD)
        setTextColor(csl)
    }else{
        val colorInt = resources.getColor(com.dpconde.kaicare.R.color.md_theme_light_onSurfaceVariant)
        val csl = ColorStateList.valueOf(colorInt)
        setTypeface(null, Typeface.NORMAL)
        setTextColor(csl)
    }
}