package csperandio.dependencies

import csperandio.dependencies.dependencies.DependenciesSearch
import csperandio.dependencies.dependencies.Dependency
import csperandio.dependencies.repo.LocalMavenRepo
import csperandio.dependencies.repo.MavenRepo

class VersionChecker(val localRepo: LocalMavenRepo, val externalRepo: MavenRepo? = null) {
    fun date(dependency: Dependency): String? {
        val foundDate = localRepo.date(dependency)

        if (foundDate != null || externalRepo == null) {
            return foundDate
        }

        val dateFromExternal = externalRepo.date(dependency)
        if (dateFromExternal != null) {
            localRepo[dependency] = dateFromExternal
        }
        return dateFromExternal
    }

    fun dates(pom: String): List<Pair<Dependency, String>> {
        val search = DependenciesSearch()
        val dependencies = search.dependencies(pom)

        return dependencies.map {
            val d = date(it)
            if (d == null) return@map null else return@map Pair(it, d)
        }.filterNotNull()
    }
}