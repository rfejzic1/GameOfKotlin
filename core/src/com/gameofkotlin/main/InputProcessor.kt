package com.gameofkotlin.main

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.math.Vector3
import kotlin.math.*

class InputProcessor(private val camera: OrthographicCamera) : GestureDetector.GestureAdapter() {
    private val receivers = mutableSetOf<InputReceiver>()

    fun subscribe(receiver: InputReceiver) {
        receivers.add(receiver)
    }

    fun unsubscribe(receiver: InputReceiver) {
        receivers.remove(receiver)
    }

    override fun tap(x: Float, y: Float, count: Int, button: Int): Boolean {
        for(receiver in receivers)
            receiver.onTapped(camera.unproject(Vector3(x, y, 0f)))
        return false
    }

    override fun fling(velocityX: Float, velocityY: Float, button: Int): Boolean {
        var dir : Direction = Direction.None
        val offset = 0.0f

        val angle = atan2(-velocityY, velocityX)
        val sina = sin(angle)
        val cosa = cos(angle)

        Gdx.app.log("Angle", angle.toString())
        Gdx.app.log("SinCos", "sin = $sina, cos = $cosa")

        if(abs(sina) > abs(cosa)) {
            if(sina > offset)
                dir = Direction.Up
            else if(sina < -offset)
                dir = Direction.Down
        }else {
            if(cosa > offset)
                dir = Direction.Right
            else if(cosa < -offset)
                dir = Direction.Left
        }

        for(receiver in receivers)
            receiver.onSwipe(dir)
        return false
    }

}