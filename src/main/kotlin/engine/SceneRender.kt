package org.example.engine

import org.lwjgl.opengl.GL30.*

class SceneRender {
    private var shaderProgram : ShaderProgram
    init {
        val shaderModuleDataList = mutableListOf<ShaderProgram.ShaderModuleData>()
        shaderModuleDataList.add(ShaderProgram.ShaderModuleData("src/main/resources/shaders/scene.vert", GL_VERTEX_SHADER))
        shaderModuleDataList.add(ShaderProgram.ShaderModuleData("src/main/resources/shaders/scene.frag", GL_FRAGMENT_SHADER))
        shaderProgram = ShaderProgram(shaderModuleDataList)
    }

    fun cleanup() {
        shaderProgram.cleanup()
    }

    fun render(scene : Scene) {
        shaderProgram.bind()
        scene.getMeshMap().values.forEach{
            mesh -> glBindVertexArray(mesh.getVaoId())
            glDrawArrays(GL_TRIANGLES, 0, mesh.numVertices)
        }

        glBindVertexArray(0)
        shaderProgram.unbind()
    }
}