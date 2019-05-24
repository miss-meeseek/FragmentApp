package com.example.myapp

import android.app.Activity
import android.app.AppComponentFactory
import android.os.Bundle
import android.support.v4.view.GestureDetectorCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import kotlinx.android.synthetic.main.activity_main.*

private const val DEBUG_TAG = "MyApp"

class MainActivity : AppCompatActivity(),  GestureDetector.OnGestureListener {

    private lateinit var mDetector: GestureDetectorCompat
    private val textFragment = MyFragment()
    private val imageFragment = ImageFragment()
    private val manager = supportFragmentManager

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mDetector = GestureDetectorCompat(this, this)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (mDetector.onTouchEvent(event)) {
            true
        } else {
            super.onTouchEvent(event)
        }
    }

    override fun onDown(event: MotionEvent): Boolean {
        Log.d(DEBUG_TAG, "onDown: $event")
        return true
    }

    override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        Log.i(DEBUG_TAG, "onFling has been called!")
        val SWIPE_MIN_DISTANCE = 120
        val SWIPE_THRESHOLD_VELOCITY = 200

        if (e1.x - e2.x > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            Log.i(DEBUG_TAG, "Right side")

            if(!textFragment.isVisible && !imageFragment.isVisible) {
                val transaction = manager.beginTransaction()
                transaction.replace(R.id.fragment_container,textFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }

        } else if (e2.x - e1.x > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            Log.i(DEBUG_TAG, "Left side")
            if(textFragment.isVisible) {
                manager.popBackStack()
            }

        } else if (e2.y - e1.y > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
            Log.i(DEBUG_TAG, " Up side")
            if(!textFragment.isVisible && !imageFragment.isVisible) {
                val transaction = manager.beginTransaction()
                transaction.replace(R.id.fragment_container,imageFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }

        } else if (e1.y - e2.y > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
            Log.i(DEBUG_TAG, " Down side")
            if(imageFragment.isVisible) {
                manager.popBackStack()
            }
        }else {
            return false
        }
        return true
    }

    override fun onLongPress(event: MotionEvent) {
        Log.d(DEBUG_TAG, "onLongPress: $event")
        //check how many fragments exists
        var list = manager.fragments
        Log.d(DEBUG_TAG, "EPIC LIST  $list")
    }

    override fun onScroll(
        event1: MotionEvent,
        event2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        Log.d(DEBUG_TAG, "onScroll: $event1 $event2")
        return true
    }

    override fun onShowPress(event: MotionEvent) {
        Log.d(DEBUG_TAG, "onShowPress: $event")
    }

    override fun onSingleTapUp(event: MotionEvent): Boolean {
        Log.d(DEBUG_TAG, "onSingleTapUp: $event")
        return true
    }
}