package com.sample.android.news.util

import android.text.format.DateUtils
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.github.florent37.picassopalette.PicassoPalette
import com.sample.android.news.R
import com.squareup.picasso.Picasso

/**
 * Binding adapter used to hide the spinner once data is available
 */
@BindingAdapter("goneIfNotNull")
fun goneIfNotNull(view: View, it: Any?) {
    view.visibility = if (it != null) View.GONE else View.VISIBLE
}

/**
 * Binding adapter used to display images from URL using Picasso
 */
@BindingAdapter("imageUrl")
fun bindImage(layout: FrameLayout, url: String?) {

    val imageView: ImageView = layout.findViewById(R.id.article_poster)
    Picasso.with(layout.context).load(url).into(
        imageView,
        PicassoPalette.with(url, imageView)
            .use(PicassoPalette.Profile.VIBRANT)
            .intoBackground(layout.findViewById(R.id.article_background), PicassoPalette.Swatch.RGB)
            .intoTextColor(layout.findViewById(R.id.article_name), PicassoPalette.Swatch.BODY_TEXT_COLOR)
    )
}

/**
 * Binding adapter used to display image from URL using Picasso
 */
@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, url: String?) {
    Picasso.with(imageView.context).load(url).into(imageView)
}

@BindingAdapter("descriptive_date")
fun setDescriptiveDate(view: TextView, timestamp: Long) {
    val now = System.currentTimeMillis()
    val relativeDate = DateUtils.getRelativeTimeSpanString(
        timestamp,
        now,
        DateUtils.SECOND_IN_MILLIS,
        DateUtils.FORMAT_ABBREV_RELATIVE
    )
    view.text = relativeDate
}