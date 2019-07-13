package com.gameofkotlin.main

import com.badlogic.gdx.graphics.g2d.SpriteBatch

interface Renderable {
    fun render(batch: SpriteBatch)
}