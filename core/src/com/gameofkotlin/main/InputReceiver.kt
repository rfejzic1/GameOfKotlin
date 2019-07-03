package com.gameofkotlin.main

import com.badlogic.gdx.math.Vector3

interface InputReceiver {
    fun onTapped(point: Vector3)
    fun onSwipe(dir: Direction)
}
