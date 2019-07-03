package com.gameofkotlin.main

import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer

// todo - Think of a better way to implement this
class Map(private var camera: Camera, val tiledMap: TiledMap) {
    private val tiledMapRenderer = OrthogonalTiledMapRenderer(tiledMap)

    fun render() {
        tiledMapRenderer.setView(camera)
        tiledMapRenderer.render()
    }

    fun dispose() {
        tiledMap.dispose()
        tiledMapRenderer.dispose()
    }
}