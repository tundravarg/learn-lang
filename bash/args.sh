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
  echo SRC: $SRC
  echo DST: $DST
  echo AUX: ${AUX[@]}
  echo HELP: $HELP
  if [[ $HELP == "Y" ]]; then
      print_help
      return 1
    fi
}

function print_args {
  echo ARG: $*
}


##
# Parse arguments with parameters separated by spaces
#
function args_spaces {
  AUX=()
  HELP=N
  SRC=
  DST=
  while [[ $# -gt 0 ]]; do
    case $1 in
      -i|--input)
        SRC=$2
        shift
        shift
        ;;
      -o|--output)
        DST=$2
        shift
        shift
        ;;
      -h|--help)
        HELP=Y
        shift
        ;;
      --*|-*)
        print_unknown $1
        HELP=Y
        shift
        ;;
      *)
        AUX+=("$1")
        shift
        ;;
    esac
  done
}


##
# Parse arguments with parameters separated by =
#
function args_eq {
  AUX=()
  HELP=N
  SRC=
  DST=
  for a in "$@"; do
    case $a in
      -i=*|--input=*)
        SRC="${a#*=}"
        ;;
      -o=*|--output=*)
        DST="${a#*=}"
        ;;
      -h|--help)
        HELP=Y
        ;;
      --*|-*)
        print_unknown $a
        HELP=Y
        ;;
      *)
        AUX+=("$a")
        ;;
    esac
  done
}


##
# Parse arguments with parameters separated by space or =
#
function args_space_or_eq {
  AUX=()
  HELP=N
  SRC=
  DST=
  for (( i=1; i<=${#*}; i++ )); do
    a=${@:$i:1}
    case $a in
      -i|--input)
        ((i++))
        SRC=${@:$i:1}
        ;;
      -i=*|--input=*)
        SRC="${a#*=}"
        ;;
      -o|--output)
        ((i++))
        DST=${@:$i:1}
        ;;
      -o=*|--output=*)
        DST="${a#*=}"
        ;;
      -h|--help)
        HELP=Y
        ;;
      --*|-*)
        print_unknown $a
        HELP=Y
        ;;
      *)
        AUX+=("$a")
        ;;
    esac
  done
}


##
# Parse arguments with parameters separated by space or =
#
function args_getopts {
  AUX=()
  HELP=N
  SRC=
  DST=

  OPTIND=1
  while getopts ":hi:o:" a; do
    case $a in
      i|input)
        SRC=$OPTARG
        ;;
      o|output)
        DST=$OPTARG
        ;;
      h|help)
        HELP=Y
        ;;
      *)
        print_unknown "${*:$OPTIND-1:1}"
        HELP=Y
        ;;
    esac
  done
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

function test_args_spaces {
  test "Args with spaces" "args_spaces" $*
}

function test_args_eq {
  test "Args with spaces" "args_eq" $*
}

function test_args_space_or_eq {
  test "Args with spaces" "args_space_or_eq" $*
}

function test_args_getopts {
  test "Args with spaces" "args_getopts" $*
}


#test_args_spaces -i Input A1 --output Output A2 A3
#test_args_spaces -i Input A1 --output=Output A2 A3
#test_args_spaces -h
#test_args_spaces --help
#test_args_spaces -a A --Bbb B --Ccc=C

#test_args_eq -i=Input A1 --output=Output A2 A3
#test_args_eq -i Input A1 --output=Output A2 A3
#test_args_eq -h
#test_args_eq --help
#test_args_eq -a A --Bbb B --Ccc=C

#test_args_space_or_eq -i=Input A1 --output=Output A2 A3
#test_args_space_or_eq -i Input A1 --output=Output A2 A3
#test_args_space_or_eq -h
#test_args_space_or_eq --help
#test_args_space_or_eq -a A --Bbb B --Ccc=C

test_args_getopts -i Input -e -o=Output -- A1 A2 A3
