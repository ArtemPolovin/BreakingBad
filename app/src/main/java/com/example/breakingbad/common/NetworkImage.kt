package com.example.breakingbad.common

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.app.ActivityCompat.startPostponedEnterTransition
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

fun loadImage(image: ImageView, imageUrl: String?, context: Context) {
    Glide.with(context)
        .load(imageUrl)
        .fitCenter()
        .apply(RequestOptions.circleCropTransform())
        .into(image)
}
