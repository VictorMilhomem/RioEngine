package org.example.engine

import java.util.concurrent.Callable


class Engine(windowTitle: String, opts: WindowOptions, private val appLogic: IAppLogic) {
    private val window = Window(windowTitle, opts, Callable<Void> {
        resize()
        null
    })
    private var render = Render()
    private var running: Boolean
    private var scene = Scene()
    private val targetFps = opts.fps
    private val targetUps = opts.ups

    init {
        appLogic.init(window, scene, render)
        running = true
    }

    companion object {
        const val TARGET_UPS: Int = 30
    }

    private fun cleanup() {
        appLogic.cleanUp()
        render.cleanup()
        scene.cleanup()
        window.cleanup()
    }

    private fun resize() {

    }

    fun start() {
        running = true
        run()
    }

    fun stop() {
        running = false
    }

    private fun run() {
        var initialTime = System.currentTimeMillis()
        val timeU = 1000f / targetUps
        val timeR = if (targetFps > 0 )  1000f/targetFps else 0f
        var deltaUpdate = 0f
        var deltaFps = 0f

        var updateTime = initialTime
        while (running and !window.windowShouldClose()) {
            window.pollEvents()
            val now = System.currentTimeMillis()
            deltaUpdate += (now - initialTime) / timeU
            deltaFps += (now - initialTime) / timeR

            if ((targetFps <= 0) or (deltaFps >= 1)) {
                appLogic.input(window, scene, now - initialTime)
            }

            if (deltaUpdate >= 1 ) {
                val diffTimeMillis = now - updateTime
                appLogic.update(window, scene, diffTimeMillis)
                updateTime = now
                deltaUpdate--
            }

            if ((targetFps <= 0) or (deltaFps >= 1)) {
                render.render(window, scene)
                deltaFps--
                window.update()
            }

            initialTime = now
        }
        cleanup()
    }
}