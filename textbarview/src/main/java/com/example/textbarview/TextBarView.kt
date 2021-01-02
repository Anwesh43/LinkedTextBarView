package com.example.textbarview

import android.view.View
import android.view.MotionEvent
import android.graphics.Paint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF
import android.app.Activity
import android.content.Context

val text : String = "2021"
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

fun Int.inverse() : Float = 1f / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n
fun Float.sinify() : Float = Math.sin(this * Math.PI).toFloat()

fun Canvas.drawTextBar(scale : Float, w : Float, h : Float, paint : Paint) {
    val sf : Float = scale.sinify()
    val sf1 : Float = sf.divideScale(0, parts)
    val sf2 : Float = sf.divideScale(1, parts)
    val barH : Float = Math.min(w, h) / barHFactor
    val fontSize : Float = Math.min(w, h) / fontFactor
    paint.textSize = fontSize
    val tw : Float = paint.measureText(text)
    save()
    translate(0f, h)
    drawRect(RectF(0f, -barH * sf1, w, 0f), paint)
    paint.color = backColor
    save()
    translate(0f, -barH / 2 + (fontSize / 2 + barH / 2) * (1f - sf2))
    drawText(text, -tw / 2, -fontSize / 4, paint)
    restore()
    restore()
}

fun Canvas.drawTBNode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    paint.color = colors[i]
    drawTextBar(scale, w, h, paint)
}

class TextBarView(ctx : Context) : View(ctx) {

    override fun onDraw(canvas : Canvas) {

    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }

    data class State(var scale : Float = 0f, var dir : Float = 0f, var prevScale : Float = 0f) {

        fun update(cb : (Float) -> Unit) {
            scale += scGap * dir
            if (Math.abs(scale - prevScale) > 1) {
                scale = prevScale + dir
                dir = 0f
                prevScale = scale
                cb(prevScale)
            }
        }

        fun startUpdating(cb : () -> Unit) {
            if (dir == 0f) {
                dir = 1f - 2 * prevScale
                cb()
            }
        }
    }

    data class Animator(var view : View, var animated : Boolean = false) {

        fun animate(cb : () -> Unit) {
            if (animated) {
                cb()
                try {
                    Thread.sleep(delay)
                    view.invalidate()
                } catch(ex : Exception) {

                }
            }
        }

        fun start() {
            if (!animated) {
                animated = true
                view.postInvalidate()
            }
        }

        fun stop() {
            if (animated) {
                animated = false
            }
        }
    }
}