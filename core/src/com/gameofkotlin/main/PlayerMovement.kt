package com.gameofkotlin.main

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.math.Vector3
import kotlin.math.*

class PlayerMovement(cam: OrthographicCamera ,reciever: InputReceiver) : GestureDetector.GestureAdapter() {
    val reciever: InputReceiver = reciever
    val camera: OrthographicCamera = cam

    override fun tap(x: Float, y: Float, count: Int, button: Int): Boolean {
        reciever.onTapped(camera.unproject(Vector3(x, y, 0f)))
        return false
    }

    override fun fling(velocityX: Float, velocityY: Float, button: Int): Boolean {
        var dir : SwipeDir = SwipeDir.None

        val angle = atan2(-velocityY, velocityX)
        val sina = sin(angle)
        val cosa = cos(angle)
        val offset = 0.1f

        Gdx.app.log("Angle", angle.toString())
        Gdx.app.log("SinCos", "sin = $sina, cos = $cosa")

        if(abs(sina) > abs(cosa)) {
            if(sina > offset)
                dir = SwipeDir.Up
            if(sina < offset)
                dir = SwipeDir.Down
        }else {
            if(cosa > offset)
                dir = SwipeDir.Right
            if(cosa < offset)
                dir = SwipeDir.Left
        }

        reciever.onSwipe(dir)
        return false
    }

}