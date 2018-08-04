#!/usr/bin/env bash

PROG_NAME=$0
ACTION=$1
TXT_PATH=$2

usage() {
    echo "Usage: $PROG_NAME {compile|runClass \`txt path\`}"
}

compile() {
    javac -d out \
    --module-source-path src \
    -m easytext.cli,easytext.analysis
}

runClass() {
    java --module-path out \
         --module easytext.cli/blog.ml3426.easytext.cli.Main "$TXT_PATH"
}

case "$ACTION" in
    compile)
        compile
    ;;
    runClass)
        runClass
    ;;
    *)
        usage
    ;;
esac