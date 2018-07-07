package kb.command

import kb.config.ProjectConfig
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import kotlin.concurrent.thread

class RunApp(val projectConfig: ProjectConfig) {

    fun run() {
        val rt = Runtime.getRuntime()

        var cmd = "gradle run"
        projectConfig.appArgs?.let {
            cmd += " -Pargs=\"${it}\""
        }

        val pr = rt.exec(
                cmd,
                emptyArray(),
                File(projectConfig.rootPath)
        )

        val pipes = listOf(
                pipe(pr.inputStream, System.out),
                pipe(pr.errorStream, System.err)
        )

        pipes.forEach { it.join() }
    }

    fun pipe(input: InputStream, output: OutputStream): Thread {
        return thread {
            var n: Int
            val buffer = ByteArray(1024)
            n = input.read(buffer)
            while (n > -1) {
                output.write(buffer, 0, n)   // Don't allow any extra bytes to creep in, final write
                n = input.read(buffer)
            }
            output.close()
        }
    }

}