package com.gameofkotlin.main

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class Player(private val texture : Texture) : GameObject(112f, 112f), InputReceiver, Renderable {

    override fun render(batch: SpriteBatch) {
        batch.draw(texture, x, y)
    }

    override fun onSwipe(dir: Direction) {
        move(dir)
    }

    private fun move(dir: Direction) {
        when(dir) {
            Direction.Left -> {
                val newX = x - GameManager.gridSize
                if(!GameManager.currentMap.isCellSolid(newX, y))
                    x = newX
            }
            Direction.Right -> {
                val newX = x + GameManager.gridSize
                if(!GameManager.currentMap.isCellSolid(newX, y))
                    x = newX
            }
            Direction.Up -> {
                val newY = y + GameManager.gridSize
                if(!GameManager.currentMap.isCellSolid(x, newY))
                    y = newY
            }
            Direction.Down -> {
                val newY = y - GameManager.gridSize
                if(!GameManager.currentMap.isCellSolid(x, newY))
                    y = newY
            }
            Direction.None -> {}
        }
    }
}