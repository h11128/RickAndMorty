package com.jason.rickandmorty.ui.helper

import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import kotlin.math.abs


const val swipe_error = "SwipeDetector error"
const val default_log = "please pass SwipeDetector.OnSwipeEvent Interface instance"

class SwipeDetector(private val v: View) : OnTouchListener {
    private var minDistance1 = 100
    private var downX = 0f
    private var downY = 0f
    private var upX = 0f
    private var upY = 0f
    private var swipeEventListener: OnSwipeEvent? = null
    fun setOnSwipeListener(listener: OnSwipeEvent?) {
        try {
            swipeEventListener = listener
        } catch (e: ClassCastException) {
            Log.e("ClassCastException", default_log, e)
        }
    }

    private fun onRightToLeftSwipe() {
        onSwipe(SwipeTypeEnum.RIGHT_TO_LEFT)
    }

    private fun onLeftToRightSwipe() {
        onSwipe(SwipeTypeEnum.LEFT_TO_RIGHT)
    }

    private fun onTopToBottomSwipe() {
        onSwipe(SwipeTypeEnum.TOP_TO_BOTTOM)
    }

    private fun onBottomToTopSwipe() {
        Log.d("abc", "OnBottomToTopSwipe")
        onSwipe(SwipeTypeEnum.BOTTOM_TO_TOP)
    }

    private fun onSwipe(swipeType: SwipeTypeEnum?) {
        if (swipeEventListener != null) {
            swipeEventListener!!.swipeEventDetected(v, swipeType)
        } else Log.e(swipe_error, default_log)
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y
                return true
            }
            MotionEvent.ACTION_UP -> {
                v.performClick()
                upX = event.x
                upY = event.y
                Log.d("anc", "$downX $downY $upX $upY")
                val deltaX = downX - upX
                val deltaY = downY - upY

                //HORIZONTAL SCROLL
                if (abs(deltaX) > abs(deltaY)) {
                    if (abs(deltaX) > minDistance1) {
                        // left or right
                        if (deltaX < 0) {
                            onLeftToRightSwipe()
                            return true
                        }
                        if (deltaX > 0) {
                            onRightToLeftSwipe()
                            return true
                        }
                    } else {
                        //not long enough swipe...
                        return false
                    }
                } else {
                    if (abs(deltaY) > minDistance1) {
                        // top or down
                        if (deltaY < 0) {
                            onTopToBottomSwipe()
                            return true
                        }
                        if (deltaY > 0) {
                            onBottomToTopSwipe()
                            return true
                        }
                    } else {
                        //not long enough swipe...
                        return false
                    }
                }
                return true
            }
        }
        return false
    }

    interface OnSwipeEvent {
        fun swipeEventDetected(v: View?, swipeType: SwipeTypeEnum?)
    }

    enum class SwipeTypeEnum {
        RIGHT_TO_LEFT, LEFT_TO_RIGHT, TOP_TO_BOTTOM, BOTTOM_TO_TOP
    }

    init {
        v.setOnTouchListener(this)
    }
}