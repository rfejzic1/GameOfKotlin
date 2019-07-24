package com.gameofkotlin.main

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader

object GameManager : InputReceiver {
    const val gridSize = 16f
    private var gameSpeed = 1f
    val step
        get() = (gridSize / gameSpeed) * Gdx.graphics.deltaTime

    var gameState = GameState.Roam

    var assets = AssetManager()
    var mainCamera = Camera(GameObject())
    var currentMap : Map = Map(assets["world1.tmx"])
    var player: Entity

    init {
        assets.setLoader(TiledMap::class.java, TmxMapLoader(InternalFileHandleResolver()))

        assets.load("dude.png", Texture::class.java)
        assets.load("world1.tmx", TiledMap::class.java)
        assets.finishLoading()

        player = Entity(assets["dude.png"], 112f, 112f)
    }

    override fun onSwipe(dir: Direction) {
        if(gameState != GameState.Roam)
            return

        player.move(dir)
        if(currentMap.shouldStartBattle())
            startBattle()
    }

    private fun startBattle() {
        gameState = GameState.Battle
    }
}