package com.gameofkotlin.main


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