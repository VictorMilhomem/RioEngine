package org.example

import org.example.engine.*
import java.nio.file.Files
import java.nio.file.Paths


class Game : IAppLogic {

    val gameEng = Engine("First Game",
        WindowOptions(true, 60,540, 960),
        this
    )


    override fun init(window: Window, scene: Scene, render: Render) {
        val positions = arrayOf<Float>(
            0f, 0.5f, 0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0f
        )

        val mesh = Mesh(positions, 3)
        scene.addMesh("triangle", mesh)
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