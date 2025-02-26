package org.example

import org.example.engine.*


class Game : IAppLogic {

    val gameEng = Engine("First Game",
        WindowOptions(true, 60,540, 960),
        this
    )

    override fun cleanUp() {

    }

    override fun init(window: Window, scene: Scene, render: Render) {

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