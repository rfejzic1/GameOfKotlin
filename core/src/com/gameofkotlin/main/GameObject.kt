package com.gameofkotlin.main

import com.badlogic.gdx.math.Vector2

open class GameObject(public var x: Float = 0f, public var y: Float = 0f) {
    fun getPosition() = Vector2(x, y)

    fun setPosition(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    fun setPosition(pos: Vector2) {
        x = pos.x
        y = pos.y
    }
}