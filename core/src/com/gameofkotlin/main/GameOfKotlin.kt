package com.gameofkotlin.main

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapRenderer
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector3
import kotlin.math.abs
import kotlin.math.round

class GameOfKotlin : ApplicationAdapter(), InputReceiver {
    // Rendering
    private lateinit var camera : OrthographicCamera
    private lateinit var batch: SpriteBatch
    private lateinit var characterTexture : Texture
    private lateinit var characterRect: Rectangle

    // Tilemap
    private lateinit var map : TiledMap
    private lateinit var tiledMapRenderer: OrthogonalTiledMapRenderer

    // Grid movement
    private var moving = false
    private val moveTime = 0.2f
    private val gridSize = 16f
    private var moveDir = SwipeDir.None
    private val step
        get() = (gridSize / moveTime) * Gdx.graphics.deltaTime

    override fun create() {
        val screenHeight = Gdx.graphics.height
        val screenWidth = Gdx.graphics.width


        val viewportWidth = gridSize * 10f
        val viewportHeight = (1f * screenHeight / screenWidth) * viewportWidth

        Gdx.app.log("Screen", "w: $screenWidth, h: $screenHeight")
        Gdx.app.log("Viewport", "w: $viewportWidth, h: $viewportHeight")
        Gdx.app.log("Movement data", "gridSize: ${gridSize}, step: ${step}")

        camera = OrthographicCamera()
        camera.setToOrtho(false, viewportWidth, viewportHeight)
        batch = SpriteBatch()

        characterTexture = Texture(Gdx.files.internal("dude.png"))
        val initXPos = round(viewportWidth / 2)
        val initYPos = round(viewportHeight / 2)
        Gdx.app.log("InitPos", "($initXPos, $initYPos)")
        characterRect = Rectangle(initXPos, initYPos, gridSize, gridSize)

        map = TmxMapLoader().load("world1.tmx")
        tiledMapRenderer = OrthogonalTiledMapRenderer(map)

        Gdx.input.inputProcessor = GestureDetector(InputProcessor(camera, this))
    }

    override fun onTapped(point: Vector3) {

    }

    override fun onSwipe(dir: SwipeDir) {
        if(moving || dir == SwipeDir.None)
            return
        moving = true
        moveDir = dir
        Gdx.app.log("Movement", "started")
    }

    fun move() {
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
        // TODO - check snapping condition
        val snapped = (remX < offset || remX > gridSize - offset) && (remY < offset || remY > gridSize - offset)
        Gdx.app.log("Snapped", "remX: $remX, remY: $remY, offset: $offset")
        if(moving && snapped) {
            characterRect.x = round(characterRect.x)
            characterRect.y = round(characterRect.y)
            Gdx.app.log("Movement", "stopped")
            moving = false
        }
    }

    override fun render() {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        camera.update()
        batch.projectionMatrix = camera.combined

        tiledMapRenderer.setView(camera)
        tiledMapRenderer.render()

        move()

        batch.begin()
        batch.draw(characterTexture, characterRect.x, characterRect.y)
        batch.end()
    }

    override fun dispose() {
        characterTexture.dispose()
        batch.dispose()
        map.dispose()
    }
}

/*
Animation:
    private val numOfCols = 1
    private val numOfRows = 1
    private lateinit var runAnimation: Animation<TextureRegion>
    private var stateTime = 0f

create()
    val spriteSheet = TextureRegion.split(characterTexture, characterTexture.width / numOfCols, characterTexture.height / numOfRows)
    val spriteFrames : Array<TextureRegion> = Array(numOfCols * numOfRows) { i -> spriteSheet[i / numOfCols][i.rem(numOfCols)]}

    runAnimation = Animation(0.25f, *spriteFrames)

render()
    stateTime += Gdx.graphics.deltaTime
    batch.draw(runAnimation.getKeyFrame(stateTime, true), characterRect.x, characterRect.y)
*/