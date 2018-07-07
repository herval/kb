package kb.config

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule
import kb.ArgsParser
import java.io.File
import java.nio.file.Files


data class Dependency(
        val project: String,
        val group: String,
        val version: String
)

val ProjectFileName = "project.yaml"

data class ProjectConfig(
        val dependencies: List<Dependency>?,
        val repositories: List<String>?,
        val version: String?,
        val sourcePaths: List<String>?,
        val testPaths: List<String>?,
        val languages: List<Language>,
        val rootPath: String?,
        @JsonProperty("test-dependencies") val testDependencies: List<String>?,
        @JsonProperty("main-class") val mainClass: String? = null,
        @JsonProperty("jvm-args") val jvmArgs: String? = null,
        @JsonProperty("app-args") val appArgs: String? = null,
        @JsonProperty("output-name") val outputName: String? = null
) {
    companion object {
        // parse the project.yaml file and override configs passed as param
        fun parseWithOverrides(params: ArgsParser, projectFilePath: String): ProjectConfig {
            val f = File(params.rootPath, projectFilePath)
            val conf = if (f.exists()) {
                val projectFile = f.toPath()

                val mapper = ObjectMapper(YAMLFactory())
                mapper.registerModule(KotlinModule())
                // TODO support empty config
                mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true)

                Files.newBufferedReader(projectFile).use {
                    mapper.readValue(it, ProjectConfig::class.java)
                }
            } else {
                throw IllegalStateException("Project file not found - run 'kb init' to start")
            }

            println(conf)

            // override config with params and/or set defaults
            return conf.copy(
                    // making sure there's no nils
                    dependencies = conf.dependencies ?: emptyList(),
                    testDependencies = conf.testDependencies ?: emptyList(),
                    repositories = conf.repositories ?: emptyList(),

                    // override some if a param is supplied
                    languages = nonEmpty(conf.languages, params.languages),
                    jvmArgs = nonEmptyOrElse(params.jvmArgs, conf.jvmArgs),
                    mainClass = nonEmptyOrElse(params.mainClass, conf.mainClass),
                    outputName = nonEmptyOrElse(params.outputName, conf.outputName),
                    appArgs = nonEmptyOrElse(params.appArgs, conf.appArgs),

                    // cannot override those via config, sorry!
                    rootPath = params.rootPath,
                    sourcePaths = sourcePaths(conf.languages),
                    testPaths = testPaths(conf.languages)
            )
        }

        private fun <T> nonEmpty(list1: List<T>, fallback: List<T>): List<T> {
            return if (list1.isEmpty()) {
                fallback
            } else {
                list1
            }
        }

        private fun nonEmptyOrElse(value: String, default: String?): String? {
            return if (value != "") {
                value
            } else {
                default
            }
        }
    }
}
