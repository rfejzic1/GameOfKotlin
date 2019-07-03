package com.gameofkotlin.main

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector3

class Entity(private var texture: Texture) : InputReceiver {
    // TODO - Think of a way to get this info from the main game class or a map object
    private val gridSize = 16f

    var x = 0f
        private set
    var y = 0f
        private set
    var width = gridSize
        private set
    var height = gridSize
        private set

    constructor(texture: Texture, x: Float, y: Float, w: Float, h: Float) : this(texture){
        this.x = x
        this.y = y
        this.width = w
        this.height = h
    }

    fun render(batch: SpriteBatch) {
        batch.draw(texture, x, y)
    }

    override fun onTapped(point: Vector3) {

    }

    override fun onSwipe(dir: Direction) {
        move(dir)
    }

    private fun move(dir: Direction) {
        when(dir) {
            Direction.Left -> x -= gridSize
            Direction.Right -> x += gridSize
            Direction.Up -> y += gridSize
            Direction.Down -> y -= gridSize
            Direction.None -> {}
        }
    }

    private fun step() {

    }

    fun disposeTexture() {
        texture.dispose()
    }
}