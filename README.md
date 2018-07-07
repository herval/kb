# kb
## An opinionated zero-config compiler for Kotlin & Java

kb follows the design philosophy behind compiler toolchains from modern languages such as Rust, Swift and Golang. Java (and other JVM languages) have always been plagued by bulky, complex and multi-stage compiling processes. kb lets you focus on your code alone, and provides all the tooling to build libraries, "fat jar" binaries, run tests and manage your dependencies.

Behind the scenes, kb essentially build and maintains a set of Gradle configurations for you.


## Project structure conventions

Every kb project requires code to be put on specific folder structures:

`project.yaml` - the (optional) config file where you define project dependencies, plugins, etc
`src/main/<java, kotlin, resources>` - the folders where source must be located. Everything under resources will automatically be bundled.
`src/test/<java, kotlin, resources>` - the folders where tests must be located. Everything under resources will automatically be included on test runs.
`out` - the output folder for compiled binariesd

## Commands

### `kb init`
Creates the default folder structure and a `project.yaml` config file on the current folder or the folder specified by the `--root-path` param.

Arguments:
  `--language` - specify which languages the project contain. This will create a separate source folder per language specified. Supported values: java, kotlin

### `kb build`
Build a binary containing all sources on your project, and puts it (along with all dependency jars) on an `out` folder.

Arguments:
  `--main-class` - specify the main class of the project. Omit if you're building a library or jar without a main class. You can also define this via the `project.yaml` file.
  `--fatjar` - build a fatjar. This will include all the dependencies in a single jar file. Ideal for distributing a binary with no external dependencies.
  `--binary` - build a native binary. This requires GraalVM to be installed.


### `kb run`
Compiles and runs the main class of the project.

Arguments:
  `--binary`
  `--app-args` - pass down parameters to the app as-is. Eg.: `--app-args "-foo=true -bar=false"`
  `--jvm-args` - pass down these arguments to the JVM. Eg.: `--jvm-args "-server -Xms2G"`


### `kb deps`
Manages dependencies. The following commands are available:

  `list` - list all current project dependencies
  `add` - add a dependency. 
  `upgrade <group>:<project>` - upgrade a dependency to the latest available release


### `kb test`
Run all tests on the test path. The following commands are available:

  `--target` - run a single target. The format can be either a package (in which case all tests in that package will be ran - eg `com.foo`), a class file (`com.foo.BarTest`) or a specific test (`com.foo.BarTest:methodName`).


## The project.yaml file

This is how you specify project dependencies, override default behaviors, include plugins, etc. The structure of a fully-loaded `project.yaml` file looks like the following:

```
version: "1.0.0"

repositories:
  - "http://myserver/repo" 

dependencies:
  - "org.apache.commons:commons-collections4:4.1"
  - "org.apache.commons:commons-math3:4.0"
  - "../other-project"

testDependencies:
  - "org.junit:junit:4.1"

main-class: "foo.bar.Main"

output-name: "custom-jar-name"
```

## Specifying dependencies
The format of dependencies follow Gradle's convention of `<group>:<project>:<version>`

You can also specify _source dependencies_ by pointing to folders that either follow the kb structure or contain a `project.yaml` file.

## Why can't I...
There are no submodules. You can use source dependencies to have multiple projects in the same folder structure.
There are no "runtime dependencies". Bundle your JDBC drivers.


## Building kb
kb itself is built using Gradle (I know, I know! It'll be built using kb itself, eventually). To build a distribution:

```
gradle shadowJar
```