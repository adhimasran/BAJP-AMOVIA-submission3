package com.adhi.amovia.utils

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Color
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
import com.adhi.amovia.R
import com.adhi.amovia.utils.Constants.BASE_URL_IMG
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object Utility {

    fun ImageView.loadImage(path: String) {
        Glide.with(context)
            .load("${BASE_URL_IMG}${path}")
            .into(this)
    }

    fun Int.convertToTime(): String {
        return "${if (this / 60 > 0) "${this / 60}h " else ""}${this % 60}m"
    }

    fun String.convertToDate(): String {
        val format = DateTimeFormatter.ofPattern("MMMM dd, yyyy")
        val date = LocalDate.parse(this)
        return date.format(format)
    }

    private fun view(state: Boolean): Int {
        return if (state) View.VISIBLE else View.GONE
    }

    fun Fragment.isPortrait() =
        resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT


    fun ProgressBar.loading(state: Boolean) {
        visibility = view(state)
    }

    fun ChipGroup.addChip(context: Context, label: String) {
        Chip(context).apply {
            id = View.generateViewId()
            text = label
            isClickable = false
            isCheckable = false
            isFocusable = false
            chipBackgroundColor =
                ColorStateList.valueOf(getColor(context, R.color.gold))
            addView(this)
        }
    }

    fun ImageButton.isFavorite(state: Boolean) {
        if (state) {
            setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_favorite_24
                )
            )
            setColorFilter(Color.RED)
        } else {
            setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_favorite_outline_grey_24
                )
            )
            setColorFilter(R.color.grey_500)
        }
    }

    fun LinearLayout.notFound(state: Boolean) {
        visibility = view(state)
    }
}