package csperandio.dependencies

import csperandio.dependencies.dependencies.DependenciesSearch
import csperandio.dependencies.dependencies.Dependency
import csperandio.dependencies.repo.MavenRepo
import java.io.Reader

class VersionChecker(private vararg val repos: MavenRepo) {
    fun date(dependency: Dependency) = repos
        .mapNotNull { it.date(dependency) }
        .firstOrNull()

    fun dates(poms: Iterable<Reader>) = poms
        .flatMap { DependenciesSearch(it).dependencies() }
        .distinct()
        .associateWith { date(it) }
        .filterValues { it != null } as Map<Dependency, String>
}
