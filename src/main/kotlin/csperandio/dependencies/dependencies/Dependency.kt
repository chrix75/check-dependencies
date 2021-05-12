package csperandio.dependencies.dependencies

data class Dependency(val group: String, val artifact: String, val version: String) {
    val coordinates: String
        get() = "${group}:${artifact}:${version}"
}