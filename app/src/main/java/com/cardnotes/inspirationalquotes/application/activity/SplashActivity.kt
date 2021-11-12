package com.cardnotes.inspirationalquotes.application.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import com.cardnotes.inspirationalquotes.R
import com.cardnotes.inspirationalquotes.application.enums.Destination
import com.cardnotes.inspirationalquotes.databinding.LayoutActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    private val binding by lazy {
        LayoutActivitySplashBinding.inflate(LayoutInflater.from(this))
    }

    override fun canShowInterstitial(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val item = SplashManager(this).getItem()
        binding.message.apply {
            setTextColor(item.textColor)
            text = item.title
            item.typeface?.let {
                typeface = it
            }
        }
        binding.initial.apply {
            setTextColor(item.textColor)
            text = item.subtitle
            item.typeface?.let {
                typeface = it
            }
        }
        binding.splashImageLayout.setBackgroundColor(item.backgroundColor)
        statusColor(item.backgroundColor)
        Handler(Looper.getMainLooper()).postDelayed(
            {
                navigate(Destination.MAIN)
            },
            3500L
        )
    }

    override fun navigate(destination: Destination) {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    //This is the class for the splash manager
    private class SplashManager(private val context: Context) {

        val fonts = arrayOf("bebas.otf", "comfortaa.ttf", "dephion.otf", "limerock.ttf")

        val message = arrayOf(
            "Life is what happens when you are busy making other plans" to "John Lennon",
            "The way to get started is to quit talking and begin doing" to "Walt Disney",
            "Always remember that you are absolutely unique. Just like everyone else" to "Margaret Mead",
            "Life is either a daring adventure or nothing at all" to "Hellen Keller",
            "The only impossible journey is the one you never begin" to "Tony Robbins",
            "You only live once, but if you do it right, once is enough" to "Mae West",
            "Success is walking from failure to failure with no loss of enthusiasm" to "Winston Churchill",
            "You miss 100% of the shots you don't take" to "Wayne Gretzky",
            "Believe you can and you're halfway there" to "Theodore Roosevelt",
            "Dream big and dare to fail" to "Norman Vaughan",
            "It does not matter how slowly you go as long as you do not stop" to "Confucius"
        )

        class Item(
            val typeface: Typeface?,
            val title: String,
            val subtitle: String,
            val textColor: Int,
            val backgroundColor: Int
        )

        fun getItem(): Item {
            val split = f23727b.random().split("@")
            val typeface = Typeface.createFromAsset(context.assets, "fonts/" + fonts.random())
            val pair = message.random()
            return Item(
                typeface = typeface,
                title = "“${pair.first}”",
                subtitle = "-${pair.second}-",
                textColor = m19660d(split[1]),
                backgroundColor = m19660d(split[0])
            )
        }

        /**********************************************/
        /* renamed from: a */

        /*
         var f23726a = intArrayOf(
             R.color.color11,
             R.color.color12,
             R.color.color13,
             R.color.color14,
             R.color.color15,
             R.color.color16,
             R.color.color17,
             R.color.color18,
             R.color.color27
         )
         */


        /* renamed from: b */
        var f23727b = arrayOf(
            "#fdcd00@#26231c",
            "#1c1b21@#ffffff",
            "#3D155F@#DF678C",
            "#4831D4@#CCF381",
            "#317773@#E2D1F9",
            "#121c37@#ffa937",
            "#79bbca@#39324b",
            "#ffadb1@#202f34",
            "#373a3c@#e3b94d",
            "#e38285@#fbfdea",
            "#eebb2c@#6c2c4e",
            "#170e35@#94daef"
        )

        /* renamed from: c */
        fun m19659c(str: String): String {
            return if (str.contains("#")) str else "#$str"
        }

        /* renamed from: d */
        fun m19660d(str: String): Int {
            return Color.parseColor(m19659c(str))
        }
    }
}