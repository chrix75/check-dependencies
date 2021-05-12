package csperandio.dependencies.dependencies

class DependencyExtraction {
    private val groupSearchRegex = Regex("<groupId>.+</groupId>")
    private val groupReplaceRegex = Regex("</?groupId>")
    private val artifactSearchRegex = Regex("<artifactId>.+</artifactId>")
    private val artifactReplaceRegex = Regex("</?artifactId>")
    private val versionSearchRegex = Regex("<version>.+</version>")
    private val versionReplaceRegex = Regex("</?version>")

    fun fromDeclaration(decl: String): Dependency? {
        var group = ""
        var artifact = ""
        var version = ""
        val lines = decl.split(Regex("\r?\n"))

        for (l in lines) {
            if (l.matches(groupSearchRegex)) {
                group = l.replace(groupReplaceRegex, "")
            }
            if (l.matches(artifactSearchRegex)) {
                artifact = l.replace(artifactReplaceRegex, "")
            }
            if (l.matches(versionSearchRegex)) {
                version = l.replace(versionReplaceRegex, "")
            }

            if (isCompleteDeclaration(group, artifact, version) || hasStartedExclusions(l)) {
                break
            }
        }

        if (missingInfo(group, artifact, version)) {
            return null
        }

        return Dependency(group, artifact, version)
    }

    private fun hasStartedExclusions(line: String) = line.contains("<exclusions>")

    private fun isCompleteDeclaration(group: String, artifact: String, version: String) =
        group.isNotEmpty() && artifact.isNotEmpty() && version.isNotEmpty()

    private fun missingInfo(group: String, artifact: String, version: String) =
        group.isEmpty() || artifact.isEmpty() || version.isEmpty() || version.startsWith("\${")
}