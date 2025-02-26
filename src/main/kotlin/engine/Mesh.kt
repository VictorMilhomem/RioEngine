package org.example.engine


import org.lwjgl.opengl.GL30
import org.lwjgl.opengl.GL30.*
import org.lwjgl.system.MemoryUtil

class Mesh(positions : Array<Float>, val numVertices: Int){
    private var vaoId : Int
    private val vboIdList = mutableListOf<Int>()

    init {
        vaoId = glGenVertexArrays()
        glBindVertexArray(vaoId)

        val vboId = glGenBuffers()
        vboIdList.add(vboId)
        val positionsBuffer = MemoryUtil.memCallocFloat(positions.size)
        positionsBuffer.put(0, positions.toFloatArray())
        glBindBuffer(GL_ARRAY_BUFFER, vboId)
        glBufferData(GL_ARRAY_BUFFER, positionsBuffer, GL_STATIC_DRAW)
        glEnableVertexAttribArray(0)
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0,)

        glBindBuffer(GL_ARRAY_BUFFER, 0)
        glBindVertexArray(0)

        MemoryUtil.memFree(positionsBuffer)
    }

    fun cleanup() {
        vboIdList.forEach(GL30::glDeleteBuffers)
        glDeleteVertexArrays(vaoId)
    }

    fun getVaoId() : Int {
        return vaoId
    }

}