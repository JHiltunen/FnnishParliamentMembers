package com.jhiltunen.finnishparliamentmembers.ui.adapters

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String) {
    imgUrl?.let{
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()

        Glide.with(imgView.context)
            .load(imgUri)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .apply(
                RequestOptions()
            )
            .into(imgView)
    }
}