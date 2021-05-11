package csperandio.dependencies.dependencies

class DependenciesSearch {

    fun dependencies(pom: String): List<Dependency> {
        val workingPom = pom.replace(Regex(" +"), "")
        val declarations = dependencyDefinitions(workingPom)

        val dependencyExtraction = DependencyExtraction()
        return declarations.map { dependencyExtraction.fromDeclaration(it) }
            .filterNotNull()
    }

    private fun dependencyDefinitions(block: String): List<String> {
        val current = mutableListOf<String>()
        val lines = block.split(Regex("\r?\n"))
        var started = false
        var declarations = mutableListOf<String>()

        for (l in lines) {
            if (l.contains("<dependency>")) {
                started = true
            }

            if (l.contains("</dependency>")) {
                started = false
                current.add(l)
                declarations.add(current.joinToString("\n"))
                current.clear()
            }

            if (started) {
                current.add(l)
            }
        }

        return declarations
    }
}
