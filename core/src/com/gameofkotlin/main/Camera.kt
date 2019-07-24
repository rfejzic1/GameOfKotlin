package com.gameofkotlin.main

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.MathUtils
import kotlin.math.abs
import kotlin.math.sign

class Camera(private var target: GameObject) : OrthographicCamera() {
    private val mapWidth = GameManager.currentMap.worldSize * GameManager.gridSize
    private val mapHeight = GameManager.currentMap.worldSize * GameManager.gridSize
    private val tilesHigh = 6
    private val camSpeed = 8f

    private var leftBound = 0f
    private var rightBound = 0f
    private var upperBound = 0f
    private var lowerBound = 0f

    private val xOffset = 1 * GameManager.gridSize
    private val yOffset = 1 * GameManager.gridSize

    init {
        val screenHeight = Gdx.graphics.height
        val screenWidth = Gdx.graphics.width

        val viewportHeight = GameManager.gridSize * tilesHigh
        val viewportWidth = viewportHeight * screenWidth / screenHeight

        Gdx.app.log("Screen", "w: $screenWidth, h: $screenHeight")
        Gdx.app.log("Viewport", "w: $viewportWidth, h: $viewportHeight")

        setToOrtho(false, viewportWidth, viewportHeight)

        leftBound = 0 + viewportWidth / 2f
        rightBound = mapWidth - viewportWidth / 2f
        upperBound = mapHeight - viewportHeight / 2f
        lowerBound = 0 + viewportHeight / 2f
    }

    fun setTarget(newTarget: GameObject) {
        target = newTarget
    }

    fun updatePosition() {
        val deltaX = (target.x + GameManager.gridSize / 2) - position.x
        val deltaY = (target.y + GameManager.gridSize / 2) - position.y

        var xStep = sign(deltaX) * GameManager.step * camSpeed
        var yStep = sign(deltaY) * GameManager.step * camSpeed

        if(deltaX < xOffset + GameManager.step && deltaX > -xOffset - GameManager.step)
            xStep = 0f

        if(deltaY < yOffset + GameManager.step && deltaY > -yOffset - GameManager.step)
            yStep = 0f

        translate(xStep, yStep)

        position.x  = MathUtils.clamp(position.x, leftBound, rightBound)
        position.y  = MathUtils.clamp(position.y, lowerBound, upperBound)

        update()
    }

}