#!/bin/bash

if !(type python3.7 >/dev/null 2>&1)
then
	echo "Python não encontrado no PATH."
else
	for file in files/*.txt
	do
		echo $(basename "$file")
		#cat "$file"
		python3.7 roundrobin.py "$file"
		echo
	done
fi