package kb.command

import java.io.File
import java.io.InputStream
import java.io.OutputStream
import kotlin.concurrent.thread

object Gradle {

    fun run(cmd: String, path: String) {
        val rt = Runtime.getRuntime()

        val pr = rt.exec(
                "gradle ${cmd}",
                emptyArray(),
                File(path)
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