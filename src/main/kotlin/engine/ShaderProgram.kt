package org.example.engine

import org.lwjgl.opengl.GL30
import org.lwjgl.opengl.GL30.*


class ShaderProgram(
    val shaderModuleDataList: List<ShaderModuleData>
) {
    private val programId = glCreateProgram()
    init {
        if (programId == 0) {
            throw RuntimeException("Could not create Shader")
        }

        val shaderModules = mutableListOf<Int>()
        shaderModuleDataList.forEach{
            s -> shaderModules.add(createShader(Utils.readFile(s.shaderFile), s.shaderType))
        }

        link(shaderModules)
    }

    fun bind() {
        glUseProgram(programId)
    }

    fun cleanup() {
        unbind()
        if(programId != 0) {
            glDeleteProgram(programId)
        }
    }

    private fun createShader(shaderCode: String, shaderType : Int) : Int {
        val shaderId = glCreateShader(shaderType)
        if (shaderId == 0) {
            throw RuntimeException("Error creating shader. Type: $shaderType")
        }

        glShaderSource(shaderId, shaderCode)
        glCompileShader(shaderId)

        if(glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
            throw RuntimeException("Error compiling Shader code: ${glGetProgramInfoLog(programId, 1024)}")
        }

        glAttachShader(programId, shaderId)
        return shaderId
    }

    private fun link(shaderModules : List<Int>) {
        glLinkProgram(programId)
        if(glGetProgrami(programId, GL_LINK_STATUS) == 0) {
            throw RuntimeException("Error linking Shader code: ${glGetProgramInfoLog(programId, 1024)}")
        }

        shaderModules.forEach {
            s -> glDetachShader(programId, s)
        }
        shaderModules.forEach(GL30::glDeleteShader)
    }

    fun unbind() {
        glUseProgram(0)
    }

    fun validate() {
        glValidateProgram(programId)
        if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) {
            throw RuntimeException("Error validating Shader code: ${glGetProgramInfoLog(programId, 1024)}")
        }
    }

    @JvmRecord
    data class ShaderModuleData(val shaderFile: String, val shaderType: Int)
}