package kb.command

import freemarker.template.Configuration
import kb.config.ProjectConfig
import java.io.File
import java.io.FileWriter

// generate build files and keep them in sync
class GenerateBuildFiles(val config: ProjectConfig) {
    private val cfg = Configuration()

    init {
        cfg.setClassForTemplateLoading(this::class.java, "/templates")
    }

    fun run() {
        println("Refreshing build config files...")

        val template = cfg.getTemplate("build.gradle.ftl")
        val params = mapOf(
                "languages" to config.languages.map { it.name.toLowerCase() },
                "repositories" to config.repositories,
                "testDependencies" to config.testDependencies,
                "dependencies" to config.dependencies,
                "sourcePaths" to config.sourcePaths,
                "testPaths" to config.testPaths
        )


        val fileWriter = FileWriter(File(config.rootPath, "build.gradle"))
        try {
            template.process(params, fileWriter)
        } finally {
            fileWriter.close()
        }
    }
}