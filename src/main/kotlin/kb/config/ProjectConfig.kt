package kb.config

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

val DefaultRepositories = listOf("https://repo.maven.apache.org/maven2/")
val ProjectFileName = "project.yaml"

data class ProjectConfig(
        val dependencies: List<Dependency>,
        val repositories: List<String>,
        val testDependencies: List<String>,
        val mainClass: String?,
        val jvmArgs: String?,
        val appArgs: String?,
        val outputName: String?
) {
    companion object {
        // parse the project.yaml file or use the default configs
        fun parseOrDefault(params: ArgsParser, projectFilePath: String): ProjectConfig {
            val f = File(params.projectPath, projectFilePath)
            val conf = if (f.exists()) {
                val projectFile = f.toPath()

                val mapper = ObjectMapper(YAMLFactory())
                mapper.registerModule(KotlinModule())

                Files.newBufferedReader(projectFile).use {
                    mapper.readValue(it, ProjectConfig::class.java)
                }
            } else {
                ProjectConfig(
                        dependencies = emptyList(),
                        repositories = DefaultRepositories,
                        testDependencies = emptyList(),
                        mainClass = null, // TODO locate main class
                        jvmArgs = null,
                        appArgs = null,
                        outputName = null // TODO use folder name
                )
            }

            // TODO params override configs
            return conf
        }
    }
}
