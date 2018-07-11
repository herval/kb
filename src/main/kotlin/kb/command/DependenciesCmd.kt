package kb.command

import kb.ArgsParser
import kb.config.ProjectConfig

class DependenciesCmd(val projectConfig: ProjectConfig, val args: ArgsParser) {

    fun run() {
        if (args.list) { // TODO default is also list
            ListDependencies(projectConfig).run()
        }
    }

}