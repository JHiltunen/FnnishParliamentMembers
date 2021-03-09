package com.jhiltunen.finnishparliamentmembers.repositorys

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

fun getImage(context : Context, imageView : ImageView, imageUrl : String) {
    Glide.with(context)
        .load(imageUrl)
        .into(imageView)
}