package org.example.engine


import org.lwjgl.opengl.GL30
import org.lwjgl.opengl.GL30.*
import org.lwjgl.system.MemoryUtil

class Mesh(positions : FloatArray, colors: FloatArray, indices : IntArray){
    val numVertices: Int = indices.size
    private var vaoId : Int
    private val vboIdList = mutableListOf<Int>()

    init {
        vaoId = glGenVertexArrays()
        glBindVertexArray(vaoId)

        // Positions VBO
        var vboId : Int  = glGenBuffers()
        vboIdList.add(vboId)
        val positionsBuffer = MemoryUtil.memCallocFloat(positions.size)
        positionsBuffer.put(0, positions)
        glBindBuffer(GL_ARRAY_BUFFER, vboId)
        glBufferData(GL_ARRAY_BUFFER, positionsBuffer, GL_STATIC_DRAW)
        glEnableVertexAttribArray(0)
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0,)



        // Colors VBO
        vboId = glGenBuffers()
        vboIdList.add(vboId)
        val colorsBuffer = MemoryUtil.memCallocFloat(colors.size)
        colorsBuffer.put(0, colors)
        glBindBuffer(GL_ARRAY_BUFFER, vboId)
        glBufferData(GL_ARRAY_BUFFER, colorsBuffer, GL_STATIC_DRAW)
        glEnableVertexAttribArray(1)
        glVertexAttribPointer(1,3, GL_FLOAT, false, 0, 0)

        // Index VBO
        vboId = glGenBuffers()
        vboIdList.add(vboId)
        val indicesBuffer = MemoryUtil.memCallocInt(indices.size)
        indicesBuffer.put(0, indices)
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboId)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW)


        glBindBuffer(GL_ARRAY_BUFFER, 0)
        glBindVertexArray(0)

        MemoryUtil.memFree(positionsBuffer)
        MemoryUtil.memFree(colorsBuffer)
        MemoryUtil.memFree(indicesBuffer)
    }

    fun cleanup() {
        vboIdList.forEach(GL30::glDeleteBuffers)
        glDeleteVertexArrays(vaoId)
    }

    fun getVaoId() : Int {
        return vaoId
    }

}