package com.example.breakingbad.common

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

 fun loadImage(image: ImageView, imageUrl: String?, context: Context) {
    Glide.with(context)
        .load(imageUrl)
        .centerCrop()
        .apply(RequestOptions.circleCropTransform())
        .into(image)
}
