package org.example.engine

data class WindowOptions (
    val compatibleProfile: Boolean,
    val fps: Int,
    val height: Int,
    val width: Int,
    val ups : Int = Engine.TARGET_UPS
)