#!/bin/bash
source /etc/environment

args=''
for i in "${@}"; do
    args+="'$i' "
done

cd /node
eval "java -jar $BASE_DIR/build/libs/FullNode.jar -c config.conf $args"