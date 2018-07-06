package kb.command

import kb.Language
import java.io.File

class InitProject(val rootPath: String, val languages: List<Language>) {
    private val defaultPaths = listOf(
            "src/main/resources",
            "src/test/resources",
            "out"
    ) + languages.map { "src/main/${it.name.toLowerCase()}" }

    fun run() {
        println("Initializing project structure on ${rootPath}")
        makeSrc()
        makeProjectFile()
        println("🙌 Project tree initialized! Happy hacking! 🚀")
    }

    private fun makeProjectFile() {
        val projectFile = File(rootPath, "project.yaml")
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
            val fullPath = File(rootPath, it)
            if (fullPath.mkdirs()) {
                println("${fullPath.absolutePath} created!")
            } else {
                println("${fullPath.absolutePath} already exists, skipping...")
            }
        }
    }

}