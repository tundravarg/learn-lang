#!/bin/bash


function print_help {
  echo "Args <flags> <files>"
  echo "<flags>:"
  echo "  -h --help            Print this help"
  echo "  -i --input <file>    Input file"
  echo "  -o --output <file>   Ouput file"
  echo "<files>:"
  echo "  *                    Auxiliary files"
}

function print_unknown {
  echo Unknown option: $1
}

function print_result {
  echo HELP: $HELP
  if [[ $HELP == "Y" ]]; then
    print_help
    return 1
  fi
  echo SRC: $SRC
  echo DST: $DST
  echo AUX: ${AUX[@]}
}

function print_args {
  echo ARG: $*
}


function test {
  NAME=$1
  FUNC=$2
  ARGS=${*:3}
  echo "---- $NAME ----"
  print_args $ARGS
  $FUNC $ARGS
  print_result
  echo "---- End ----"
}


test "Echo" "echo Echo: " -i Input A1 --output Output A2 A3
