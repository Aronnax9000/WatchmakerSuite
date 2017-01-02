#!/bin/sh
DoExitAsm ()
{ echo "An error occurred while assembling $1"; exit 1; }
DoExitLink ()
{ echo "An error occurred while linking $1"; exit 1; }
echo Linking program_brand_new
OFS=$IFS
IFS="
"
/usr/bin/ld.bfd -b elf64-x86-64 -m elf_x86_64      -L. -o program_brand_new link.res
if [ $? != 0 ]; then DoExitLink program_brand_new; fi
IFS=$OFS
