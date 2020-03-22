package com.example.mobiquity.base

import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.mobiquity.R
import com.example.mobiquity.utils.BASE_URL
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

object CustomBindingAdapter{

    @JvmStatic
    @BindingAdapter("bind:image_url")
    fun loadImage(imageView: ImageView, url: String) {

        Picasso.with(imageView.context).load(BASE_URL + url)
            .placeholder(R.drawable.new_document)
            .into(imageView, object: Callback {
                override fun onSuccess() {
                    //set animations here

                }

                override fun onError() {

                    var  param: LinearLayout.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                    param.gravity = Gravity.CENTER
                    imageView.scaleType = ImageView.ScaleType.FIT_CENTER
                    imageView.layoutParams = param
                    imageView.setColorFilter(ContextCompat.getColor(imageView.context, R.color.white))
                }
            })
    }

}