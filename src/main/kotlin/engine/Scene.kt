package org.example.engine

class Scene {
    private val meshMap = mutableMapOf<String, Mesh>()

    fun addMesh(meshId: String, mesh : Mesh) {
        meshMap[meshId] = mesh
    }

    fun cleanup() {
        meshMap.values.forEach(Mesh::cleanup)
    }

    fun getMeshMap() : Map<String, Mesh> {
        return meshMap
    }
}