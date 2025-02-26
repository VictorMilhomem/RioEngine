package org.example.engine

import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*

class Render {
    init {
        GL.createCapabilities()
    }

    fun cleanup() {

    }

    fun render(window: Window, scene: Scene) {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
    }
}