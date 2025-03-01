package org.example

import org.example.engine.*


class Game : IAppLogic {

    val gameEng = Engine("First Game",
        WindowOptions(true, 60,540, 960),
        this
    )


    override fun init(window: Window, scene: Scene, render: Render) {
        val positions = floatArrayOf(
            -0.5f, 0.5f, -3.0f,
            -0.5f, -0.5f, -3.0f,
            0.5f, -0.5f, -3.0f,
            0.5f, 0.5f, -3.0f,
        )
        val colors = floatArrayOf(
            0.5f, 0.0f, 0.0f,
            0.0f, 0.5f, 0.0f,
            0.0f, 0.0f, 0.5f,
            0.0f, 0.5f, 0.5f,
        )
        val indices = intArrayOf(
            0, 1, 3, 3, 1, 2,
        )
        val mesh = Mesh(positions, colors, indices)
        scene.addMesh("quad", mesh)
    }

    override fun cleanUp() {

    }

    override fun input(window: Window, scene: Scene, diffTimeMillis: Long) {

    }

    override fun update(window: Window, scene: Scene, diffTimeMillis: Long) {

    }

}


fun main() {

    val game = Game()
    game.gameEng.start()
}