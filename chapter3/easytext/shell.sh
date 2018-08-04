#!/usr/bin/env bash

PROG_NAME=$0
ACTION=$1
TXT_PATH=$2

usage() {
    echo "Usage: $PROG_NAME {compile|makeJar|runClass \`txt path\`|runJar \`txt path\`|link}"
}

compile() {
    javac -d out/easytext \
    src/module-info.java \
    src/blog/ml3426/easytext/Main.java
}

makeJar() {
    mkdir mods
    jar -cfe mods/easytext.jar blog.ml3426.easytext.Main -C out/easytext .
}

runClass() {
    java --module-path out \
         --module easytext/blog.ml3426.easytext.Main "$TXT_PATH"
}

runJar() {
    java --module-path mods \
         --module easytext "$TXT_PATH"
}

link() {
    jlink --module-path mods/:$JAVA_HOME/jmods \
          --add-modules easytext \
          --launcher easybin=easytext \
          --output easytext-image
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