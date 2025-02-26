package org.example.engine

import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.system.MemoryUtil.NULL
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.system.MemoryUtil
import java.util.concurrent.Callable
import org.tinylog.Logger



class Window(
    title: String,
    opts: WindowOptions,
    val resizeFunc: Callable<Void>
) {
    private var width : Int? = null
    private var height : Int? = null
    private var windowHandle : Long? = null

    init {

        if (!glfwInit()) {
            throw IllegalStateException("Unable to initialize GLFW")
        }

        glfwDefaultWindowHints()
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE)
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE)

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2)

        if(opts.compatibleProfile) {
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_COMPAT_PROFILE)
        }else {
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)
            glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE)
        }

        if ((opts.width > 0) and (opts.height > 0) ) {
            width = opts.width
            height = opts.height
        }else {
            glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE)
            val vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor()) ?: throw RuntimeException("Unable to get video mode")
            width = vidMode.width()
            height = vidMode.height()
        }

        windowHandle = glfwCreateWindow(width!!, height!!, title, NULL, NULL)
        if(windowHandle == NULL) {
            throw RuntimeException("Failed to initialize GLFW window")
        }

        glfwSetFramebufferSizeCallback(windowHandle!!) {window, w, h ->
            resized(w, h)
        }

        glfwSetErrorCallback {errorCode, msgPtr ->
            Logger.error("Error code [{}], msg [{}]", errorCode, MemoryUtil.memUTF8(msgPtr))
        }

        glfwSetKeyCallback(windowHandle!!) { _, key, _, action, _->
            keyCallBack(key, action)
        }

        glfwMakeContextCurrent(windowHandle!!)

        if(opts.fps > 0) {
            glfwSwapInterval(0)
        }else {
            glfwSwapInterval(1)
        }

        glfwShowWindow(windowHandle!!)

        val arrWidth = IntArray(1)
        val arrHeight = IntArray(1)
        glfwGetFramebufferSize(windowHandle!!, arrWidth, arrHeight)
        width = arrWidth[0]
        height = arrHeight[0]
    }

    fun cleanup() {
        glfwFreeCallbacks(windowHandle!!)
        glfwDestroyWindow(windowHandle!!)
        glfwTerminate()
        val callback = glfwSetErrorCallback(null)
        if (callback!!.equals(null)) {
            callback.free()
        }
    }

    fun keyCallBack(key: Int, action : Int) {
        if ((key == GLFW_KEY_ESCAPE) and (action == GLFW_RELEASE)) {
            glfwSetWindowShouldClose(windowHandle!!, true)
        }
    }

    fun getHeight() : Int {
        return height!!
    }

    fun getWidth() : Int {
        return width!!
    }

    fun getWindowHandle() : Long {
        return windowHandle!!
    }

    fun isKeyPressed(keyCode : Int) : Boolean {
        return glfwGetKey(windowHandle!!, keyCode) == GLFW_PRESS
    }

    fun pollEvents() {
        glfwPollEvents()
    }


    private fun resized(_width: Int, _height: Int) {
        width = _width
        height = _height

        try {
            resizeFunc.call()
        }catch (e : Exception) {
            Logger.error("Error calling resize callback", e)
        }
    }

    fun update() {
        glfwSwapBuffers(windowHandle!!)
    }

    fun windowShouldClose() : Boolean {
        return glfwWindowShouldClose(windowHandle!!)
    }
}