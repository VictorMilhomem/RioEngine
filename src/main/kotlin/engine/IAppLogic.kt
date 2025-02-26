package org.example.engine

interface IAppLogic {
    fun cleanUp()
    fun init(window: Window, scene : Scene, render: Render)
    fun input(window: Window, scene : Scene, diffTimeMillis: Long)
    fun update(window: Window, scene : Scene, diffTimeMillis: Long)
}