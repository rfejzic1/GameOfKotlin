package com.gameofkotlin.main

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader

object GameManager {
    const val gridSize = 16f
    const val worldSize = 32
    var gameSpeed = 0.1f
    val step
        get() = (gridSize / gameSpeed) * Gdx.graphics.deltaTime

    var assetManager = AssetManager()
    var mainCamera = Camera(GameObject())
    var inputManager = InputManager()
    var currentMap : Map

    init {
        assetManager.setLoader(TiledMap::class.java, TmxMapLoader(InternalFileHandleResolver()))
        assetManager.load("dude.png", Texture::class.java)
        assetManager.load("bat.png", Texture::class.java)
        assetManager.load("world1.tmx", TiledMap::class.java)
        assetManager.finishLoading()

        currentMap = Map(assetManager["world1.tmx"])
    }
}