package kb.command

import kb.ArgsParser
import kb.config.ProjectConfig

class BuildApp(val projectConfig: ProjectConfig, val params: ArgsParser) {

    fun run() {
        val cmd = if (projectConfig.mainClass == null) {
            "jar"
        } else if (params.buildFatJar) {
            "shadowJar"
        } else {
            "installDist"
        }

        Gradle.run(
                cmd,
                projectConfig.rootPath
        )
    }
}