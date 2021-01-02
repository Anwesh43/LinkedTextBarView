package com.example.textbarview

import android.view.View
import android.view.MotionEvent
import android.graphics.Paint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF
import android.app.Activity
import android.content.Context

val parts : Int = 2
val scGap : Float = 0.02f / parts
val strokeFactor : Float = 90f
val fontFactor : Float = 15f
val barHFactor : Float = 11f
val backColor : Int = Color.parseColor("#BDBDBD")
val delay : Long = 20
val colors : Array<Int> = arrayOf(
    "#F44336",
    "#795548",
    "#2196F3",
    "#009688",
    "#FF9800"
).map {
    Color.parseColor(it)
}.toTypedArray()
