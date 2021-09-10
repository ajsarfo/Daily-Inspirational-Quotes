package com.cardnotes.inspirationalquotes.application.tools

import kotlin.random.Random

class Colors private constructor() {

    //Store of all color combinations in the app
    private lateinit var colorPallet: List<Int>

    private val loveListColors = listOf(
        (0xFFe75874).toInt(),
        (0xFFbe1558).toInt(),
        (0xFF5c3c92).toInt(),
        (0xFF4a536b).toInt(),
    )

    private val proverbListColors = listOf(
        (0xFF52733B).toInt(),
        (0xFFe0a96d).toInt(),
        (0xFF818A6F).toInt(),
        (0xFF715E4E).toInt()
    )

    private val repListColors = listOf(
        (0xFF4BA754).toInt(),
        (0xFFD86161).toInt(),
        (0xFFAA7AB2).toInt(),
        (0xFF8B47FF).toInt(),
        (0xFF322514).toInt(),
        (0xFFe0a96d).toInt(),
        (0xFFff9a8d).toInt(),
        (0xFF4a536b).toInt(),
        (0xFF1978a5).toInt(),
    )

    fun getPallet(): List<Int> {
        if (!::colorPallet.isInitialized) {
            colorPallet = arrayOf(
                listOf(
                    (0xFFA3586D).toInt(),
                    (0xFF5C4A72).toInt(),
                    (0xFFF4874B).toInt(),
                    (0xFFF46A4E).toInt(),
                ),
                listOf(
                    (0xFF118C8B).toInt(),
                    (0xFFBCA18D).toInt(),
                    (0xFFF2746B).toInt(),
                    (0xFFF14D49).toInt(),
                ),
            ).random()
        }
        return colorPallet
    }

    fun getRandomColorId(): Int = Random.nextInt(repListColors.size)

    fun getRandomLoveColorId(): Int = Random.nextInt(loveListColors.size)

    fun getRandomProverColorId(): Int = Random.nextInt(proverbListColors.size)

    fun getColor(id: Int): Int = repListColors[id]

    fun getLoveColor(id: Int): Int = loveListColors[id]

    fun getProverbColor(id: Int): Int = proverbListColors[id]

    companion object {
        private lateinit var colorsInstance: Colors

        fun getInstance(): Colors {
            if (!Companion::colorsInstance.isInitialized) {
                colorsInstance = Colors()
            }
            return colorsInstance
        }
    }
}