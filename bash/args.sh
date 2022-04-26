#!/bin/bash


#
# See: https://stackoverflow.com/questions/192249/how-do-i-parse-command-line-arguments-in-bash
#


function print_help {
  echo "Args <flags> <files>"
  echo "<flags>:"
  echo "  -h --help            Print this help"
  echo "  -e                   Encrypt"
  echo "  -d                   Decrypt"
  echo "  -i --input <file>    Input file"
  echo "  -o --output <file>   Ouput file"
  echo "  -k --key <file>      Key"
  echo "<files>:"
  echo "  *                    Auxiliary files"
}

function print_unknown {
  echo Unknown option: $1
}

function print_result {
  echo ENC: $ENC
  echo DEC: $DEC
  echo SRC: $SRC
  echo DST: $DST
  echo KEY: $KEY
  echo AUX: ${AUX[@]}
  echo HLP: $HLP
  echo RET: $1
  if [[ $HLP ]] || (( $RET )); then
    print_help
    if (( $RET )); then return $RET; fi
  fi
}

function clear_args {
  unset ENC DEC SRC DST KEY AUX HLP RET
}

function print_args {
  echo ARGS: $*
}


##
# Parse arguments with parameters separated by spaces
#
function args_spaces {
  clear_args
  local RET=0
  while [[ $# -gt 0 ]]; do
    case $1 in
      -e|--encrypt)
        ENC=Y
        shift
        ;;
      -d|--decrypt)
        DEC=Y
        shift
        ;;
      -i|--input)
        SRC=$2
        shift 2
        ;;
      -o|--output)
        DST=$2
        shift 2
        ;;
      -k|--key)
        DST=$2
        shift 2
        ;;
      -h|--help)
        HLP=Y
        shift
        ;;
      --*|-*)
        print_unknown $1
        RET=1
        shift
        ;;
      *)
        AUX+=("$1")
        shift
        ;;
    esac
  done
  return $RET
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
# Parse arguments sing `getopts` shell built-in command
# Supports only short options
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


##
# Parse arguments sing `getopt` programm
# Not standartized, may be absent on some systems
#
function args_getopt {
  AUX=()
  HELP=N
  SRC=
  DST=

  # Mac variant of `getopt`
  # TODO: Linux variant of `getopt`
  # Use $@ instead of $*
  ARGS=$(getopt "hi:o:" "$@")
  R=$?

  if [ $R -ne 0 ]; then
    echo "Errors while parsing arguments"
    HELP=Y
  fi

  # Put $ARGS to positioning parameters
  eval set -- "$ARGS"

  while [ true ]; do
    case $1 in
      -i|--input)
        SRC=$2
        shift 2
        ;;
      -o|--output)
        DST=$2
        shift 2
        ;;
      -h|--help)
        HELP=Y
        shift
        ;;
      --)
        shift
        break
        ;;
      *)
        print_unknown "$1"
        HELP=Y
        shift
        ;;
    esac
  done

  AUX=${@:1}
}


function test {
  local NAME=$1
  local FUNC=$2
  local ARGS=${*:3}
  echo "---- $NAME ----"
  print_args $ARGS
  $FUNC $ARGS
  local RET=$?
  print_result $RET && echo "DONE" || echo "ERROR"
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
  test "Args with getopts" "args_getopts" $*
}

function test_args_getopt {
  test "Args with getopt" "args_getopt" $*
}


test_args_spaces -e -i Input A1 --output Output A2 A3
test_args_spaces -h
test_args_spaces -E A1 --Error A2 A3

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

# test_args_getopts -i Input -oOutput -- A1 A2 A3
# test_args_getopts -i=Input -e -o=Output -- A1 A2 A3

# test_args_getopt -i Input -oOutput -- A1 A2 A3
# test_args_getopt -i=Input -e -o=Output -- A1 A2 A3