#!/usr/bin/env bash

PROG_NAME=$0
ACTION=$1

usage() {
    echo "Usage: $PROG_NAME {compile|makeJar|runClass|runJar|link}"
}

compile() {
    javac -d out/helloworld \
    module-info.java \
    src/blog/ml3426/helloworld/HelloWorld.java
}

makeJar() {
    mkdir mods
    jar -cfe mods/helloworld.jar blog.ml3426.helloworld.HelloWorld -C out/helloworld .
}

runClass() {
    java --module-path out \
         --module helloworld/blog.ml3426.helloworld.HelloWorld
}

runJar() {
    java --module-path mods \
         --module helloworld
}

link() {
    jlink --module-path mods/:$JAVA_HOME/jmods \
          --add-modules helloworld \
          --launcher hello=helloworld \
          --output helloworld-image
}

case "$ACTION" in
    compile)
        compile
    ;;
    makeJar)
        makeJar
    ;;
    runClass)
        runClass
    ;;
    runJar)
        runJar
    ;;
    link)
        link
    ;;
    *)
        usage
    ;;
esac