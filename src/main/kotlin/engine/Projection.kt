package org.example.engine

import org.joml.Matrix4f

class Projection (width : Int, height : Int) {
    private val FOV : Float = Math.toRadians(60.0).toFloat()
    private val Z_FAR : Float = 1000f
    private val Z_NEAR : Float = 0.01f
    private var projMatrix : Matrix4f = Matrix4f()
    init {
        updateProjMatrix(width, height)
    }


    fun getProjectionMatrix() : Matrix4f {
        return projMatrix
    }


    fun updateProjMatrix(width : Int, height : Int) {
        projMatrix.setPerspective(FOV, (width/height).toFloat(), Z_NEAR, Z_FAR)
    }
}