package csperandio.dependencies

import csperandio.dependencies.dependencies.DependenciesSearch
import csperandio.dependencies.dependencies.Dependency
import csperandio.dependencies.repo.MavenRepo
import java.io.Reader

class VersionChecker(private vararg val repos: MavenRepo) {
    fun date(dependency: Dependency) = repos
        .mapNotNull { it.date(dependency) }
        .firstOrNull()

    fun dates(pom: Reader): Map<Dependency, String> {
        val search = DependenciesSearch(pom)
        val dependencies = search.dependencies()

        val map = dependencies.associateWith { date(it) }
        return map.filterValues { it != null } as Map<Dependency, String>
    }
}
