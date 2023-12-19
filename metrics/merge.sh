#!/bin/bash

# Check if an argument is provided
if [ -z "$1" ]; then
  echo "Usage: $0 <your_argument>"
  exit 1
fi
# Access the first command-line argument
file_name="$1"

# Check if an argument is provided
if [ -z "$2" ]; then
  echo "Usage: $0 <your_argument>"
  exit 1
fi
# Access the first command-line argument
output_file="$2"

# Specify the input file names
file1="1.csv"
file2=$(find "./" -type f -name "*$file_name" -print -quit)

# Specify the output file name

cp "$output_file" "$file1"

# Check if the output file already exists and remove it
if [ -e "$output_file" ]; then
  rm "$output_file"
fi



# Copy the header from the first file to the output file
head -n 1 "$file1" > "$output_file"
# Use awk to merge the files, taking the row from the newer file in case of duplicates
awk -F, 'NR==FNR && FNR>1 {a[$2]=$0; next} FNR>1 {a[$2]=$0} END {for (i in a) print a[i]}' "$file1" "$file2" >> "$output_file"

echo "Files merged successfully. Output file: $output_file"
if [ -e "$file1" ]; then
  rm "$file1"
fi
if [ -e "$file2" ]; then
  rm "$file2"
fi