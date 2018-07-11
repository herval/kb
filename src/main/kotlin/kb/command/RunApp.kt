package kb.command

import kb.config.ProjectConfig

class RunApp(val projectConfig: ProjectConfig) {

    fun run() {
        // TODO compile & java -jar instead
        val cmd = listOfNotNull(
                "run",
                projectConfig.appArgs?.let {
                    " -Pargs=\"${it}\""
                }
        ).joinToString(" ")

        Gradle.run(cmd, projectConfig.rootPath)
    }


}