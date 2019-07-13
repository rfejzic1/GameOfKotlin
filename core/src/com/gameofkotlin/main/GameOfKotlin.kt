package com.gameofkotlin.main

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader

class GameOfKotlin : ApplicationAdapter() {
    private lateinit var batch: SpriteBatch

    private lateinit var characterTexture: Texture
    private lateinit var batTexture: Texture
    private lateinit var character: Player

    // temp
    private var stateTime = 0f
    private lateinit var batAnimation: Animation<TextureRegion>

    override fun create() {
        // todo - explore asset loading and disposing
        // todo - implement map loading
        //  one map object to load the whole map, including objects and doing entity instantiation
        // todo - collision detection
        // todo - a level manager will be needed
        // todo - the map loader and the game design overall should be implemented having the level designing feature in mind

        // temporary
        val numOfCols = 8
        val numOfRows = 1
        batTexture = GameManager.assetManager["bat.png"]

        val spriteSheet = TextureRegion.split(batTexture, batTexture.width / numOfCols, batTexture.height / numOfRows)
        val spriteFrames : Array<TextureRegion> = Array(numOfCols * numOfRows) { i -> spriteSheet[i / numOfCols][i.rem(numOfCols)]}
        batAnimation = Animation(0.0625f, *spriteFrames)

        characterTexture = GameManager.assetManager["dude.png"]
        character = Player(characterTexture)
        GameManager.mainCamera.setTarget(character)

        batch = SpriteBatch()

        GameManager.inputManager.subscribe(character)
        Gdx.input.inputProcessor = GestureDetector(GameManager.inputManager)
    }

    override fun render() {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        GameManager.mainCamera.updatePosition()
        GameManager.currentMap.render()

        batch.projectionMatrix = GameManager.mainCamera.combined
        batch.begin()
        character.render(batch)
        stateTime += Gdx.graphics.deltaTime
        batch.draw(batAnimation.getKeyFrame(stateTime, true), 96f, 96f)
        batch.end()
    }

    override fun dispose() {
        batch.dispose()
        GameManager.assetManager.dispose()
    }
}

/*
Animation:
    private val numOfCols = 1
    private val numOfRows = 1
    private lateinit var batAnimation: Animation<TextureRegion>
    private var stateTime = 0f

create()
    val spriteSheet = TextureRegion.split(characterTexture, characterTexture.width / numOfCols, characterTexture.height / numOfRows)
    val spriteFrames : Array<TextureRegion> = Array(numOfCols * numOfRows) { i -> spriteSheet[i / numOfCols][i.rem(numOfCols)]}

    batAnimation = Animation(0.25f, *spriteFrames)

render()
    stateTime += Gdx.graphics.deltaTime
    batch.draw(batAnimation.getKeyFrame(stateTime, true), characterRect.x, characterRect.y)
*/

/*
Smooth Grid Movement:
    private var moving = false
    private val moveTime = 1f
    private var moveDir = SwipeDir.None
    private val step
        get() = (gridSize / moveTime) * Gdx.graphics.deltaTime
onSwipe(dir):
    if(moving || dir == SwipeDir.None)
            return
    moving = true
    moveDir = dir
    Gdx.app.log("Movement", "started")
move():
    if(!moving)
            return

    Gdx.app.log("Movement", "moving by $step")
    when(moveDir) {
        SwipeDir.Left -> {
            characterRect.x -= step
            camera.translate(-step, 0f)
        }
        SwipeDir.Right -> {
            characterRect.x += step
            camera.translate(step, 0f)
        }
        SwipeDir.Up -> {
            characterRect.y += step
            camera.translate(0f, step)
        }
        SwipeDir.Down -> {
            characterRect.y -= step
            camera.translate(0f, -step)
        }
        SwipeDir.None -> {}
    }

    val remX = abs(characterRect.x.rem(gridSize))
    val remY = abs(characterRect.y.rem(gridSize))
    val offset = step - 0.01f
    val snapped = (remX < offset || remX > gridSize - offset) && (remY < offset || remY > gridSize - offset)
    Gdx.app.log("Snapped", "remX: $remX, remY: $remY, offset: $offset")
    if(moving && snapped) {
        characterRect.x = round(characterRect.x)
        characterRect.y = round(characterRect.y)
        Gdx.app.log("Movement", "stopped")
        moving = false
    }

*/