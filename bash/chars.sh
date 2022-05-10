#!/bin/bash

for (( i=0; i<256; i++ )); do
    printf "0x%02X (%d) '%s'\n" "$i" "$i" "$(echo -e \\x$( printf "%02X" $i ))"
done
