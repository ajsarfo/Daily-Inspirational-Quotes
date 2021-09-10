package com.cardnotes.inspirationalquotes.application.file

import android.view.View
import android.widget.ImageView
import androidx.databinding.BaseObservable
import androidx.databinding.BindingAdapter
import com.google.android.material.card.MaterialCardView
import com.cardnotes.inspirationalquotes.application.image.ImageHolder
import kotlin.reflect.KProperty

class Bindable<T : Any>(private var value: T, private val tag: Int) {
    operator fun <U : BaseObservable> getValue(ref: U, property: KProperty<*>): T = value
    operator fun <U : BaseObservable> setValue(ref: U, property: KProperty<*>, newValue: T) {
        value = newValue
        ref.notifyPropertyChanged(tag)
    }
}


fun <T : Any> bindable(value: T, tag: Int): Bindable<T> = Bindable(value, tag)

@BindingAdapter("image")
fun changeImage(imageView: ImageView, imageHolder: ImageHolder?) {
    imageHolder?.let {
        when (it) {
            is ImageHolder.ImageBitmap -> imageView.setImageBitmap(it.image)
            is ImageHolder.ImageDrawable -> imageView.setImageResource(it.icon)
            else -> {
            }
        }
    }
}

@BindingAdapter("color")
fun changeBackgroundColor(cardView: MaterialCardView, color: Int) {
    cardView.setCardBackgroundColor(color)
}

@BindingAdapter("imageColor")
fun changeImageColor(imageView: ImageView, color: Int) {
    imageView.setBackgroundColor(color)
}


@BindingAdapter("viewHeight")
fun setViewHeight(view: View, viewHeight: Float) {
    view.layoutParams.apply {
        height = viewHeight.toInt()
    }
}