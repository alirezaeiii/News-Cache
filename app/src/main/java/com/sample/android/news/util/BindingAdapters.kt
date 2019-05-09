package com.sample.android.news.util

import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import com.github.florent37.picassopalette.PicassoPalette
import com.sample.android.news.R
import com.squareup.picasso.NetworkPolicy
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
fun bindImage(cardView: CardView, url: String) {

    val imageView: ImageView = cardView.findViewById(R.id.article_poster)
    Picasso.with(cardView.context).load(url).networkPolicy(NetworkPolicy.OFFLINE).into(
        imageView,
        PicassoPalette.with(url, imageView)
            .use(PicassoPalette.Profile.VIBRANT)
            .intoBackground(cardView.findViewById(R.id.article_background), PicassoPalette.Swatch.RGB)
            .intoTextColor(cardView.findViewById(R.id.article_name), PicassoPalette.Swatch.BODY_TEXT_COLOR)
    )
}