package com.gameofkotlin.main

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.input.GestureDetector

class GameOfKotlin : ApplicationAdapter() {
    private lateinit var batch: SpriteBatch

    override fun create() {
        // todo - explore asset loading and disposing (partially done)
        // todo - texture packing
        // todo - object texture animation
        // todo - implement map loading
        //  one map object to load the whole map, including objects and doing entity instantiation
        // todo - a level manager will be needed
        // todo - the map loader and the game design overall should be implemented having the level designing feature in mind
        // todo - what object will be in charge of handling GameObject instantiation and deletion and what object should manage the game?
        // todo - implement events on certain tiles such as scene change or teleportation, in game event triggers etc.
        // todo - enemy and NPC AI?
        // todo - build UI and textBox-es
        // todo - inventory system
        // todo - battle system - turn based or real-time?
        // todo - implement transitions between levels.
        //  This feature can be used to transition to the battle scene. The previous level state should be remembered.

        GameManager.mainCamera.setTarget(GameManager.player)

        batch = SpriteBatch()

        InputManager.subscribe(GameManager)
        Gdx.input.inputProcessor = GestureDetector(InputManager)
    }

    override fun render() {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        GameManager.mainCamera.updatePosition()
        GameManager.currentMap.render()

        batch.projectionMatrix = GameManager.mainCamera.combined
        batch.begin()
        GameManager.player.render(batch)
        batch.end()
    }

    override fun dispose() {
        batch.dispose()
        GameManager.assets.dispose()
    }
}
