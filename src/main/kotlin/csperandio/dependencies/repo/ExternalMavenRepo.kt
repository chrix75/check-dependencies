package csperandio.dependencies.repo

import csperandio.dependencies.dependencies.Dependency
import org.jsoup.Jsoup

class ExternalMavenRepo(private val url: String) : MavenRepo {
    override fun date(dependency: Dependency): String? {
        val lines = fileList(dependency)
        return versionDate(lines, dependency.artifact)
    }

    private fun versionDate(lines: List<String>, artifact: String): String? {
        val found = lines.firstOrNull { l -> l.startsWith(artifact) }
        return found?.replace(Regex("^\\S+\\s+"), "")?.replace(Regex("\\s+.+$"), "")
    }

    private fun fileList(dependency: Dependency): List<String> {
        val uri = dependency.group.replace('.', '/')
        val depUrl = "${url}/${uri}/${dependency.artifact}/${dependency.version}"
        return try {
            val doc = Jsoup.connect(depUrl).get()
            doc.text().split("\n")
        } catch (e: Exception) {
            emptyList()
        }
    }

}