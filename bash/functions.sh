#!/bin/bash

function test {
    local name=$1
    if [ -z $name ]; then name="Bash"; fi
    echo "Hello, $name"
}

test $1
echo $name
