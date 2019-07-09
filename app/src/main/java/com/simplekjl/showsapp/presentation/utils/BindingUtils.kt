package com.simplekjl.showsapp.presentation.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import com.simplekjl.showsapp.R
import com.squareup.picasso.Picasso

class BindingUtils {
    companion object {
        private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
        @JvmStatic
        @BindingAdapter("android:text")
        fun setFloat(view: TextView, value: Float?) {
            view.text = value?.toInt().toString()
        }

        @JvmStatic
        @InverseBindingAdapter(attribute = "android:text")
        fun getFloat(view: TextView): Float {
            val number = view.text.toString()
            return if (!number.isEmpty()) number.toFloat() else "0.0".toFloat()
        }

        @JvmStatic
        @BindingAdapter("loadImage")
        fun loadImage(view: ImageView, url: String) {
            val posterUtl = getPathForImage(url)
            Picasso.get()
                .load(posterUtl)
                .placeholder(R.drawable.thumbnail)
                .error(R.drawable.thumbnail)
                .into(view)
        }

        private fun getPathForImage(url: String): String {
            val sb: StringBuilder = StringBuilder(150)
            sb.append(IMAGE_BASE_URL)
            sb.append(url)
            return sb.toString()
        }
    }
}