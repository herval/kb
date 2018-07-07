package kb.command

import kb.ArgsParser
import kb.config.DefaultLanguages
import kb.config.sourcePaths
import kb.config.testPaths
import java.io.File

class InitProject(val params: ArgsParser) {
    private val languages = if (params.languages.isNotEmpty()) {
        params.languages
    } else {
        DefaultLanguages
    }

    private val defaultPaths = listOf(
            "src/main/resources",
            "src/test/resources",
            "out"
    ) + sourcePaths(languages) + testPaths(languages)

    fun run() {
        println("Initializing project structure on ${params.rootPath}")
        makeSrc()
        makeProjectFile()

        println("🙌 Project tree initialized! Happy hacking! 🚀")
    }

    private fun makeProjectFile() {
        // TODO build from template
        val projectFile = File(params.rootPath, "project.yaml")
        if (projectFile.exists()) {
            println("project.yaml already exists, skipping...")
        } else {
            println("project.yaml created!")
            val sample = this.javaClass.getResource("/project.yaml.example").readBytes()
            projectFile.writeBytes(sample)
        }
    }

    private fun makeSrc() {
        defaultPaths.forEach {
            val fullPath = File(params.rootPath, it)
            if (fullPath.mkdirs()) {
                println("${fullPath.absolutePath} created!")
            } else {
                println("${fullPath.absolutePath} already exists, skipping...")
            }
        }
    }

}