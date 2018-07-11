package kb.command

import kb.config.ProjectConfig

class ListDependencies(val projectConfig: ProjectConfig) {
    fun run() {
        Gradle.run(
                "dependencies",
                projectConfig.rootPath
        )
    }
}