#!/bin/bash


function tf {
    if eval "$1"
    then
        echo "$1 is TRUE"
    else
        echo "$1 is FALSE"
    fi
}

function ret {
    eval $2
    return $1
}


function tf_t {
    tf "[ $1 ]"
}

function tf_tt {
    tf "[[ $1 ]]"
}

function tf_a {
    tf "(( $1 ))"
}

function tf_r {
    tf "ret $1"
}

unset X


echo "---- EMPTY ----"
echo

unset X
tf "$X"
# tf "! $X"         # Illegal
tf "[ $X ]"
tf "[ ! $X ]"
# tf "[[ $X ]]"     # Illegal
tf "(( $X ))"       # Illegal
# tf "(( ! $X ))"

echo
echo "---- true, false ----"
echo

tf "false"
tf "true"

tf "[ false ]"
tf "[ true ]"

tf "[[ false ]]"
tf "[[ true ]]"

tf "(( false ))"
tf "(( true ))"

# tf "ret false"  # Illegal
# tf "ret true"   # Illegal

echo
echo "---- RetVal ----"
echo

tf "ret 0"
tf "ret 1"
tf "ret 2"
tf "ret -1"

echo
echo "---- 0, 1 ----"
echo

tf "[ 0 ]"
tf "[ 1 ]"

tf "[[ 0 ]]"
tf "[[ 1 ]]"

tf "(( 0 ))"
tf "(( 1 ))"
tf "(( 2 ))"
tf "(( -1 ))"

echo
echo "---- string ----"
echo

tf "[ '' ]"
tf "[ 'S' ]"
tf "[ '0' ]"
tf "[ '1' ]"

tf "[[ '' ]]"
tf "[[ 'S' ]]"
tf "[[ '0' ]]"
tf "[[ '1' ]]"

tf "(( '' ))"
tf "(( 'S' ))"
tf "(( '0' ))"
tf "(( '1' ))"
tf "(( '2' ))"

echo
echo "---- Negative ----"
echo

tf "[ $X ]"
tf "[ ! $X ]"
tf "[ '' ]"
tf "[ ! '' ]"
tf "[ 'S' ]"
tf "[ ! 'S' ]"

tf "[[ '' ]]"
tf "[[ ! '' ]]"
tf "[[ 'S' ]]"
tf "[[ ! 'S' ]]"

tf "(( 0 ))"
tf "(( ! 0 ))"
tf "(( 1 ))"
tf "(( ! 1 ))"

echo
echo "---- [[]] ----"
echo

echo "-- diect substitution"
tf "[[ '' ]]"
tf "[[ 'S' ]]"
tf "[[ ! '' ]]"
tf "[[ ! 'S' ]]"

echo "-- is empty"
tf "[[ -z '' ]]"
tf "[[ -z 'S' ]]"
tf "[[ ! -z '' ]]"
tf "[[ ! -z 'S' ]]"
