package csperandio.dependencies

import csperandio.dependencies.dependencies.DependenciesSearch
import csperandio.dependencies.dependencies.Dependency
import csperandio.dependencies.repo.MavenRepo

class VersionChecker(private vararg val repos: MavenRepo) {
    fun date(dependency: Dependency): String? {
        return repos.mapNotNull { it.date(dependency) }.firstOrNull()
    }

    fun dates(pom: String): Map<Dependency, String> {
        val search = DependenciesSearch()
        val dependencies = search.dependencies(pom)

        val map = dependencies.associateWith { date(it) }
        return map.filterValues { it != null } as Map<Dependency, String>
    }
}