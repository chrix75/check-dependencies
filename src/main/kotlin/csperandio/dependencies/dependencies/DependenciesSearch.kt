package csperandio.dependencies.dependencies

import org.apache.maven.model.Model
import org.apache.maven.model.io.xpp3.MavenXpp3Reader
import java.util.*

class DependenciesSearch {

    fun dependencies(pom: String): List<Dependency> {
        val reader = MavenXpp3Reader()
        val model = reader.read(pom.reader())

        val allDependencies = mutableListOf<Dependency>()
        with(allDependencies) {
            addAll(mavenDependencies(model))
            addAll(mavenPluginDependencies(model))
        }
        return allDependencies
    }

    private fun mavenPluginDependencies(model: Model): List<Dependency> {
        return model.build?.plugins?.filter { it.groupId != null && it.artifactId != null && it.version != null }
            ?.mapNotNull { Dependency(it.groupId, it.artifactId, it.version) }
            ?.mapNotNull { updateVersionByProperty(it, model.properties) }
            ?: emptyList()
    }

    private fun mavenDependencies(model: Model): List<Dependency> {
        return model.dependencies
            ?.filter { it.groupId != null && it.artifactId != null && it.version != null }
            ?.mapNotNull { Dependency(it.groupId, it.artifactId, it.version) }
            ?.mapNotNull { updateVersionByProperty(it, model.properties) }
            ?: emptyList()
    }

    private fun updateVersionByProperty(dependency: Dependency, properties: Properties): Dependency? {
        if (dependency.version.startsWith("\${")) {
            val propName = dependency.version.substring(2, dependency.version.length - 1)
            val realVersion = properties[propName] as String?
            return if (realVersion == null) null else Dependency(dependency.group, dependency.artifact, realVersion)
        } else {
            return dependency
        }
    }
}
