package kb.command

import kb.config.ProjectConfig

class RunApp(val projectConfig: ProjectConfig) {

    fun run() {
        val cmd = listOfNotNull(
                "run",
                projectConfig.appArgs?.let {
                    " -Pargs=\"${it}\""
                } ?: null
        ).joinToString(" ")

        Gradle.run(cmd, projectConfig.rootPath)
    }


}