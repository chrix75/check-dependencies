package csperandio.dependencies.dependencies

class DependencyExtraction {
    private val groupSearchRegex = Regex("<groupId>(?<groupId>.+?)</groupId>")
    private val artifactSearchRegex = Regex("<artifactId>(?<artifactId>.+?)</artifactId>")
    private val versionSearchRegex = Regex("<version>(?<version>.+?)</version>")
    private val exclusionsSearchRegex = Regex("<exclusions>.+?</exclusions>")

    fun fromDeclaration(dependency: String): Dependency? {
        val dependencyWithoutExclusions = dependency.replace(exclusionsSearchRegex, "")
        val group = groupSearchRegex.find(dependencyWithoutExclusions)?.groups?.get("groupId")?.value
        val artifact = artifactSearchRegex.find(dependencyWithoutExclusions)?.groups?.get("artifactId")?.value
        val version = versionSearchRegex.find(dependencyWithoutExclusions)?.groups?.get("version")?.value

        if (missingInfo(group, artifact, version)) {
            return null
        }

        return Dependency(group!!, artifact!!, version!!)
    }

    private fun missingInfo(group: String?, artifact: String?, version: String?) =
        group == null || artifact == null || version == null || version.startsWith("\${")
}
