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
        local RET=1
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
  clear_args
  for a in "$@"; do
    case $a in
      -e|--encrypt)
        ENC=Y
        ;;
      -d|--decrypt)
        DEC=Y
        ;;
      -i=*|--input=*)
        SRC="${a#*=}"
        ;;
      -o=*|--output=*)
        DST="${a#*=}"
        ;;
      -k=*|--key=*)
        DST="${a#*=}"
        ;;
      -h|--help)
        HLP=Y
        ;;
      --*|-*)
        print_unknown $a
        local RET=1
        ;;
      *)
        AUX+=("$a")
        ;;
    esac
  done
  return $RET
}


##
# Parse arguments with parameters separated by space or =
#
function args_space_or_eq {
  clear_args
  for (( i=1; i<=${#*}; i++ )); do
    a=${@:$i:1}
    case $a in
      -e|--encrypt)
        ENC=Y
        ;;
      -d|--decrypt)
        DEC=Y
        ;;
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
      -k|--key)
        ((i++))
        KEY=${@:$i:1}
        ;;
      -k=*|--key=*)
        KEY="${a#*=}"
        ;;
      -h|--help)
        HLP=Y
        ;;
      --*|-*)
        print_unknown $a
        local RET=1
        ;;
      *)
        AUX+=("$a")
        ;;
    esac
  done
  return $RET
}


##
# Parse arguments sing `getopts` shell built-in command
# Supports only short options
#
function args_getopts {
  clear_args
  OPTIND=1
  while getopts ":edi:o:k:h" a; do
    case $a in
      e)
        ENC=Y
        ;;
      d)
        DEC=Y
        ;;
      i)
        SRC=$OPTARG
        ;;
      o)
        DST=$OPTARG
        ;;
      k)
        KEY=$OPTARG
        ;;
      h)
        HLP=Y
        ;;
      *)
        print_unknown "${*:$OPTIND-1:1}"
        local RET=1
        ;;
    esac
  done
  return $RET
}


##
# Parse arguments sing `getopt` programm
# Not standartized, may be absent on some systems
#
function args_getopt {
  clear_args

  # # Mac variant of `getopt`
  # # In Linux it also works, but here is extended variant (see below)
  # # Use `$@` instead of `$*`
  # ARGS=$(getopt "edi:o:k:h" "$@")

  # Linux variant of `getopt`
  # Here is two variants of command: simple and extended (see `man getopt`).
  # Dont forget `--` before `$@`
  # Use `$@` instead of `$*`
  ARGS=$(getopt -o "edi:o:k:h" -l "encrypt,decrypt,input:,output:,key:,help" -- "$@")

  R=$?

  if (( $R )); then
    echo "Errors while parsing arguments"
    local RET=1
  fi

  # Put $ARGS to positioning parameters
  eval set -- "$ARGS"

  while [ true ]; do
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
        KEY=$2
        shift 2
        ;;
      -h|--help)
        HLP=Y
        shift
        ;;
      --)
        shift
        break
        ;;
      *)
        print_unknown "$1"
        local RET=1
        shift
        ;;
    esac
  done

  AUX=${@:1}

  return $RET
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


# test_args_spaces -e -i Input A1 --output Output A2 A3
# test_args_spaces -h
# test_args_spaces -E A1 --Error A2 A3

# test_args_eq -e -i=Input A1 --output=Output A2 A3
# test_args_eq -d -i=Input A1 --output=Output -k Key A2 A3
# test_args_eq -h

# test_args_space_or_eq -e -i=Input A1 --output=Output -k Key A2 A3
# test_args_space_or_eq -h
# test_args_space_or_eq -X

# test_args_getopts -i Input -oOutput -- A1 A2 A3
# test_args_getopts -i=Input -o=Output -- A1 A2 A3
# test_args_getopts -edh
# test_args_getopts -h
# test_args_getopts -X

test_args_getopt -i Input -oOutput -k=Key -- A1 A2 A3
test_args_getopt --input Input --output Output --key=Key -- A1 A2 A3
test_args_getopt -edh
test_args_getopt -ekKey
test_args_getopt -h
test_args_getopt --help
test_args_getopt -X