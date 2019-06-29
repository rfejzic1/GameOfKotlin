package com.gameofkotlin.main

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector3
import kotlin.math.abs
import kotlin.math.log
import kotlin.math.round

class GameOfKotlin : ApplicationAdapter(), InputReceiver {
    private lateinit var camera : OrthographicCamera
    private lateinit var batch: SpriteBatch
    private lateinit var sprMario : Texture
    private lateinit var rectMario: Rectangle

    private val numOfCols = 2
    private val numOfRows = 1
    private lateinit var runAnimation: Animation<TextureRegion>
    private var stateTime = 0f

    private var moving = false
    private val moveTime = 1f
    private val gridSize = 16
    private var moveDir = SwipeDir.None
    private var step = 0f
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

        sprMario = Texture(Gdx.files.internal("mario_run.png"))

        val spriteSheet = TextureRegion.split(sprMario, sprMario.width / numOfCols, sprMario.height / numOfRows)
        val spriteFrames : Array<TextureRegion> = Array(numOfCols * numOfRows) { i -> spriteSheet[i / numOfCols][i.rem(numOfCols)]}

        runAnimation = Animation(0.25f, *spriteFrames)

        rectMario = Rectangle(96f, 32f, 32f, 32f)

        Gdx.input.inputProcessor = GestureDetector(PlayerMovement(camera, this))
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
            SwipeDir.Left -> rectMario.x -= step
            SwipeDir.Right -> rectMario.x += step
            SwipeDir.Up -> rectMario.y += step
            SwipeDir.Down -> rectMario.y -= step
            SwipeDir.None -> {}
        }

        val remX = abs(rectMario.x.rem(gridSize))
        val remY = abs(rectMario.y.rem(gridSize))
        val offset = step - 0.01f
        // TODO - check snapping condition
        val snapped = (remX < offset || remX > gridSize - offset) && (remY < offset || remY > gridSize - offset)
        Gdx.app.log("Snapped", "remX: $remX, remY: $remY, offset: $offset")
        if(moving && snapped) {
            rectMario.x = round(rectMario.x)
            rectMario.y = round(rectMario.y)
            Gdx.app.log("Movement", "stopped")
            moving = false
        }
    }

    override fun render() {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        camera.update()
        batch.projectionMatrix = camera.combined

        stateTime += Gdx.graphics.deltaTime
        move()

        batch.begin()
        batch.draw(runAnimation.getKeyFrame(stateTime, true), rectMario.x, rectMario.y)
        batch.end()
    }

    override fun dispose() {
        sprMario.dispose()
        batch.dispose()
    }
}
