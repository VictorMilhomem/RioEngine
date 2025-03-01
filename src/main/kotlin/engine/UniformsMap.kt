package org.example.engine

import org.joml.Matrix4f
import org.lwjgl.opengl.GL20.glGetUniformLocation
import org.lwjgl.opengl.GL20.glUniformMatrix4fv
import org.lwjgl.system.MemoryStack

class UniformsMap(val programId: Int) {
    private val uniforms : MutableMap<String, Int> = mutableMapOf()

    fun createUniform(uniformName : String) {
        val uniformLocation = glGetUniformLocation(programId, uniformName)
        if (uniformLocation < 0) {
            throw RuntimeException("Could not find uniform [$uniformName]")
        }
        uniforms[uniformName] = uniformLocation
    }

    fun setUniform(uniformName: String, value: Matrix4f) {
        try {
            val stack = MemoryStack.stackPush()
            val location = uniforms[uniformName] ?: throw RuntimeException("Could not find uniform [$uniformName]")
            glUniformMatrix4fv(location.toInt(), false, value.get(stack.mallocFloat(16)))

        } catch(e : Exception) {
            throw RuntimeException("Could not push stack: ${e.message}")
        }
    }
}