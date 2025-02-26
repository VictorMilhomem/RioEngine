package org.example.engine

import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*

class Render {
    private var sceneRender: SceneRender
    init {
        GL.createCapabilities()
        sceneRender = SceneRender()
    }

    fun cleanup() {
        sceneRender.cleanup()
    }

    fun render(window: Window, scene: Scene) {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        glViewport(0, 0, window.getWidth(), window.getHeight())
        sceneRender.render(scene)
    }
}