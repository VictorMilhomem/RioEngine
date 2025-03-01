package org.example.engine

import org.lwjgl.opengl.GL30.*

class SceneRender {
    private var shaderProgram : ShaderProgram
    private var uniformsMap : UniformsMap? = null
    init {
        val shaderModuleDataList = mutableListOf<ShaderProgram.ShaderModuleData>()
        shaderModuleDataList.add(ShaderProgram.ShaderModuleData("src/main/resources/shaders/scene.vert", GL_VERTEX_SHADER))
        shaderModuleDataList.add(ShaderProgram.ShaderModuleData("src/main/resources/shaders/scene.frag", GL_FRAGMENT_SHADER))
        shaderProgram = ShaderProgram(shaderModuleDataList)
        createUniforms()
    }

    fun createUniforms(){
        uniformsMap = UniformsMap(shaderProgram.getProgramId())
        uniformsMap!!.createUniform("projectionMatrix")
    }

    fun cleanup() {
        shaderProgram.cleanup()
    }

    fun render(scene : Scene) {
        shaderProgram.bind()

        uniformsMap!!.setUniform("projectionMatrix", scene.getProjection().getProjectionMatrix())
        scene.getMeshMap().values.forEach{
            mesh -> glBindVertexArray(mesh.getVaoId())
            glDrawElements(GL_TRIANGLES, mesh.numVertices, GL_UNSIGNED_INT, 0)
        }

        glBindVertexArray(0)
        shaderProgram.unbind()
    }
}