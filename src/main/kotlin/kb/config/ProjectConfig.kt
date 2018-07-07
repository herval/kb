package kb.config

import com.fasterxml.jackson.annotation.JsonProperty
import kb.ArgsParser
import java.io.File


data class Dependency(
        val project: String,
        val group: String,
        val version: String
)

val ProjectFileName = "project.yaml"

// all attributes configurable via a project.yaml file
data class ProjectConfigFile(
        val dependencies: List<String>?,
        val repositories: List<String>?,
        val version: String?,
        val languages: List<String>?,
        @JsonProperty("run-jvm-args") val jvmArgs: String? = null,
        @JsonProperty("run-app-args") val appArgs: String? = null,
        @JsonProperty("test-dependencies") val testDependencies: List<String>?,
        @JsonProperty("main-class") val mainClass: String? = null,
        @JsonProperty("output-name") val outputName: String? = null
)

// all the configs, including command line overrides
data class ProjectConfig(
        val dependencies: List<String>,
        val repositories: List<String>,
        val version: String,
        val testDependencies: List<String>,
        val mainClass: String?,
        val outputName: String,
        val sourcePaths: List<String>,
        val testPaths: List<String>,
        val languages: List<String>,
        val rootPath: String,
        val jvmArgs: String? = null,
        val appArgs: String? = null
) {
    companion object {
        // parse the project.yaml file and override configs passed as param
        fun parseWithOverrides(params: ArgsParser, projectFilePath: String, parser: YamlParser): ProjectConfig {
            val f = File(params.rootPath, projectFilePath)
            val conf = if (f.exists()) {
                val projectFile = f.toPath()

                parser.load(projectFile, ProjectConfigFile::class.java)
            } else {
                throw IllegalStateException("Project file not found - run 'kb init' to start")
            }

            val languages = nonEmpty(conf.languages, params.languages)

            // override config with params and/or set defaults
            return ProjectConfig(
                    // making sure there's no nulls
                    dependencies = conf.dependencies ?: emptyList(),
                    testDependencies = conf.testDependencies ?: emptyList(),
                    repositories = conf.repositories ?: emptyList(),

                    // override some if a param is supplied
                    languages = languages,
                    mainClass = nonEmptyOrElse(params.mainClass, conf.mainClass),
                    outputName = nonEmptyOrElse(params.outputName, conf.outputName) ?: "main",
                    jvmArgs = nonEmptyOrElse(params.jvmArgs, conf.jvmArgs),
                    appArgs = nonEmptyOrElse(params.appArgs, conf.appArgs),

                    // cannot override those via config, sorry!
                    rootPath = params.rootPath,
                    sourcePaths = sourcePaths(languages),
                    testPaths = testPaths(languages),
                    version = conf.version ?: params.version
            )
        }

        private fun <T> nonEmpty(list1: List<T>?, fallback: List<T>): List<T> {
            return if (list1 == null || list1.isEmpty()) {
                fallback
            } else {
                list1
            }
        }

        private fun nonEmptyOrElse(value: String, default: String?): String? {
            return if (value.isNotBlank()) {
                value
            } else {
                default
            }
        }
    }
}
