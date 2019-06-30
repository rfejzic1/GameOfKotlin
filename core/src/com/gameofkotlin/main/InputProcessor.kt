package com.gameofkotlin.main

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.math.Vector3
import kotlin.math.*

class InputProcessor(private val camera: OrthographicCamera, private val receiver: InputReceiver) : GestureDetector.GestureAdapter() {

    override fun tap(x: Float, y: Float, count: Int, button: Int): Boolean {
        receiver.onTapped(camera.unproject(Vector3(x, y, 0f)))
        return false
    }

    override fun fling(velocityX: Float, velocityY: Float, button: Int): Boolean {
        var dir : SwipeDir = SwipeDir.None
        val offset = 0.0f

        val angle = atan2(-velocityY, velocityX)
        val sina = sin(angle)
        val cosa = cos(angle)

        Gdx.app.log("Angle", angle.toString())
        Gdx.app.log("SinCos", "sin = $sina, cos = $cosa")

        if(abs(sina) > abs(cosa)) {
            if(sina > offset)
                dir = SwipeDir.Up
            else if(sina < -offset)
                dir = SwipeDir.Down
        }else {
            if(cosa > offset)
                dir = SwipeDir.Right
            else if(cosa < -offset)
                dir = SwipeDir.Left
        }

        receiver.onSwipe(dir)
        return false
    }

}