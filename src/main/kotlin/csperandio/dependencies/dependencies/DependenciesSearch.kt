package csperandio.dependencies.dependencies

class DependenciesSearch() {

    private val dependencyExtraction = DependencyExtraction()
    private val dependencyRegex = Regex("<dependency>.*?</dependency>")

    fun dependencies(pom: String): List<Dependency> =
        dependencyRegex.findAll(pom.replace(Regex("\\s"), ""))
            .map { it.value }
            .mapNotNull { dependencyExtraction.fromDeclaration(it) }
            .toList()
}
