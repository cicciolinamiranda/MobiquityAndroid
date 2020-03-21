package com.example.mobiquity.base

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

object CustomBindingAdapter{

    @JvmStatic
    @BindingAdapter("bind:image_url")
    fun loadImage(imageView: ImageView, url: String) {
        Picasso.with(imageView.context).load(url).into(imageView)
    }

}