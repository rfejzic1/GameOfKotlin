package com.gameofkotlin.main

import com.badlogic.gdx.Gdx

object GameManager {
    const val gridSize = 16f
    const val worldSize = 32
    var gameSpeed = 0.1f
    val step
        get() = (gridSize / gameSpeed) * Gdx.graphics.deltaTime

}