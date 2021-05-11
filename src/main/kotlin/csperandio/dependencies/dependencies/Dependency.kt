package csperandio.dependencies.dependencies

data class Dependency(val group: String, val artefact: String, val version: String) {
    val coordinates: String
        get() = "${group}:${artefact}:${version}"
}