package com.gameofkotlin.main

import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import kotlin.random.Random

class Map(val tiledMap: TiledMap) {
    val worldSize = 32
    private val tiledMapRenderer = OrthogonalTiledMapRenderer(tiledMap)

    private val collsLayer = tiledMap.layers.get("Colls") as TiledMapTileLayer?
    private val enemyRegionLayer= tiledMap.layers.get("Enemy") as TiledMapTileLayer?

    // todo - Load enemyOccuranceChance from map properties
    val enemyOccuranceChance: Float = 0.6f

    init {
        enemyRegionLayer?.isVisible = false
    }

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

        val cell = collsLayer?.getCell(cellX, cellY)

        val solid = cell?.tile?.properties?.get("solid", Boolean::class.java)
        return solid?: false
    }

    private fun isEnemyRegionCell(x: Float, y: Float) : Boolean {
        val cellX = (x / GameManager.gridSize).toInt()
        val cellY = (y / GameManager.gridSize).toInt()

        val cell = enemyRegionLayer?.getCell(cellX, cellY)

        val enemy = cell?.tile?.properties?.get("enemy", Boolean::class.java)
        return enemy?: false
    }

    fun shouldStartBattle() : Boolean {
        val chance = Random.nextFloat()
        val isEnemyRegion = GameManager.currentMap.isEnemyRegionCell(GameManager.player.x, GameManager.player.y)
        val shouldStart = chance > GameManager.currentMap.enemyOccuranceChance

        return isEnemyRegion && shouldStart
    }
}