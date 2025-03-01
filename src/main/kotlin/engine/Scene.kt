package org.example.engine

class Scene(width: Int, height: Int) {
    private val meshMap = mutableMapOf<String, Mesh>()
    private var projection : Projection

    init {
        projection = Projection(width, height)
    }

    fun getProjection() : Projection {
        return projection
    }

    fun resize(width: Int, height: Int) {
        projection.updateProjMatrix(width, height)
    }

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