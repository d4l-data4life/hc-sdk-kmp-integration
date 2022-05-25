adb pull "/sdcard/Download" tmp

for file in tmp/screenshot_*
do
  echo "Copy $file"
  FOLDER=$(cut -d '_' -f2 <<< "$file")
  FILE=$(cut -d '_' -f3 <<< "$file")

  cp "$file" "app-android/src/androidTest/assets/$FOLDER/$FILE"
done

rm -rf tmp
