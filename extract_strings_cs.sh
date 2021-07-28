#!/bin/bash

xmllint --xpath "string(//*)" res/values-cs/cm_strings.xml | sed -e 's/^ *//' -e '/^[[:space:]]*$/d' > translate_cs.txt

xmllint --xpath "string(//*)" res/values-cs/arrays.xml | sed -e 's/^ *//' -e 's/^"//' -e 's/"$//' -e '/^[[:space:]]*$/d' >> translate_cs.txt

xmllint --xpath "string(//*)" res/values-cs/strings.xml | sed -e 's/^ *//' -e 's/^"//' -e 's/"$//' -e '/^[[:space:]]*$/d' >> translate_cs.txt

echo "Lines Words Bytes:"

wc translate_cs.txt

echo "Characters:"

wc -c translate_cs.txt

echo "Standardized lines @ 60 characters (incl space):"

echo -n $(cat translate_cs.txt | wc -c)" / 60 = "
echo $(cat translate_cs.txt | wc -c)" / 60" | bc

echo "Total price per standardized line:"

echo "("$(cat translate_cs.txt | wc -c)" / 60) * 1.65 " | bc

