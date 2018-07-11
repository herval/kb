# kb
## An opinionated low-config compiler for Kotlin & Java

`kb` follows the design philosophy behind compiler toolchains from modern languages such as Rust, Swift and Golang: make it simple, have as few knobs and twists as possible, and offer sensible defaults.

Java (and other JVM languages) have always been plagued by bulky, complex and multi-stage compiling processes. `kb` lets you focus on your code alone, and provides all the tooling to build libraries, "fat jar" binaries, run tests and manage your dependencies.

Behind the scenes, `kb` build and maintains a set of Gradle configurations for you.


## Project structure conventions

Every `kb` project requires code to be put on specific folder structures:

- `project.yaml` - the (optional) config file where you define project dependencies, plugins, etc

- `src/main/<java, kotlin, resources>` - the folders where source must be located. Everything under resources will automatically be bundled.

- `src/test/<java, kotlin, resources>` - the folders where tests must be located. Everything under resources will automatically be included on test runs.

- `out` - the output folder for compiled binariesd

## Commands

For detailed explanation of arguments and options, use `kb help`.

### `kb init`
Creates the default folder structure.

### `kb build`
Build a binary.

### `kb run`
Compiles and runs the main class of the project.

### `kb deps`
Manages dependencies.

### `kb test`
Run all tests on the test path.


## The project.yaml file

This is how you specify project dependencies, override default behaviors, include plugins, etc. The structure of a fully-loaded `project.yaml` file looks like the following:

```
group: foo
artifact: bar
version: 1.0.0

repositories:
  - "http://myserver/repo" 

dependencies:
  - "org.apache.commons:commons-collections4:4.1"
  - "org.apache.commons:commons-math3:4.0"
  - "../other-project"

testDependencies:
  - "org.junit:junit:4.1"

main-class: "foo.bar.Main"
```

## Specifying dependencies
The format of dependencies follow Gradle's convention of `<group>:<artifact>:<version>`

You can also specify _source dependencies_ by pointing to folders that either follow the `kb` structure or contain a `project.yaml` file.

## Why can't I...
- There are no submodules. You can use "source dependencies" to have multiple projects in the same folder structure.
- There are no "runtime dependencies". Bundle your JDBC drivers.


## Building kb
`kb` itself is built using Gradle (I know, I know! It'll be built using `kb` itself, eventually ¯\_(ツ)_/¯). To build from source:

```
# build it
gradle installDist

# ln it to your local $PATH folder. Something like:
sudo ln -s -F "$(pwd)/build/install/kb/bin/kb" /usr/local/bin/kb
```