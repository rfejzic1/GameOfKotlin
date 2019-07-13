package com.gameofkotlin.main

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer

// todo - Think of a better way to implement this
class Map(val tiledMap: TiledMap) {
    private val tiledMapRenderer = OrthogonalTiledMapRenderer(tiledMap)

    fun render() {
        tiledMapRenderer.setView(GameManager.mainCamera)
        tiledMapRenderer.render()
    }

    fun dispose() {
        tiledMap.dispose()
        tiledMapRenderer.dispose()
    }

    fun isCellSolid(x: Float, y: Float) : Boolean {
        val cellX = (x / GameManager.gridSize).toInt()
        val cellY = (y / GameManager.gridSize).toInt()

        val collsLayer = tiledMap.layers.get("Colls") as TiledMapTileLayer?
        val cell = collsLayer?.getCell(cellX, cellY)

        val solid = cell?.tile?.properties?.get("solid", Boolean::class.java)
        return solid?: false
    }
}