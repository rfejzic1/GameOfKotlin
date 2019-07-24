package com.gameofkotlin.main

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class Entity(private val texture : Texture, xPos: Float = 0f, yPos: Float = 0f) : GameObject(xPos, yPos), Renderable {

    override fun render(batch: SpriteBatch) {
        batch.draw(texture, x, y)
    }

    fun move(dir: Direction) {
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