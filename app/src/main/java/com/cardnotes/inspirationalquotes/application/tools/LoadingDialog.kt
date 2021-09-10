package com.cardnotes.inspirationalquotes.application.tools

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import androidx.appcompat.app.AlertDialog
import com.cardnotes.inspirationalquotes.databinding.LayoutLoadingDialogBinding

class LoadingDialog(context: Context) : AlertDialog(context) {
    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setView(
            LayoutLoadingDialogBinding.inflate(
                LayoutInflater.from(context)
            ).root
        )
        setCancelable(false)
    }
}