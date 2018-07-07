package kb.command

import freemarker.template.Configuration
import freemarker.template.Template
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
        // TODO add .gradle files to .gitignore if there is one

        writeTo(
                cfg.getTemplate("build.gradle.ftl"),
                mapOf(
                        "languages" to config.languages.map { it.toLowerCase() },
                        "repositories" to config.repositories,
                        "testDependencies" to config.testDependencies,
                        "dependencies" to config.dependencies,
                        "sourcePaths" to config.sourcePaths,
                        "testPaths" to config.testPaths,
                        "jvmArgs" to config.jvmArgs?.split(" ")?.map { "\"${it}\"" }?.joinToString(", "), // TODO do this concatenation on the freemarker code instead
                        "mainClass" to config.mainClass,
                        "version" to config.version,
                        "group" to config.group
                ),
                "build.gradle"
        )

        writeTo(
                cfg.getTemplate("settings.gradle.ftl"),
                mapOf(
                        "artifact" to config.artifact
                ),
                "settings.gradle"
        )
    }

    private fun writeTo(template: Template, params: Map<String, Any?>, target: String) {
        val fileWriter = FileWriter(File(config.rootPath, target))
        try {
            template.process(params, fileWriter)
        } finally {
            fileWriter.close()
        }
    }
}