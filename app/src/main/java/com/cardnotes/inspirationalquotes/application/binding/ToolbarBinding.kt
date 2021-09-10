package com.cardnotes.inspirationalquotes.application.binding

import androidx.annotation.DrawableRes
import com.cardnotes.inspirationalquotes.R
import com.cardnotes.inspirationalquotes.application.Dependency
import com.cardnotes.inspirationalquotes.application.image.ImageHolder

class ToolbarBinding(
    val dependency: Dependency? = null,
    var title: String? = null,
    @DrawableRes icon:  Int = R.drawable.ic_arrow_back,
    val onClick: () -> Unit
) {
    val image = ImageHolder.ImageDrawable(icon)

    fun click() {
        dependency?.customVibrate()
        onClick.invoke()
    }
}