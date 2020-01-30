#!/usr/bin/env bash

# fail in case of error
set -e
set -o pipefail


 # start headless emulator in background
run_headless_emulator() {
    emulator -no-window  \
      -avd test \
      -gpu swiftshader_indirect \
      -no-snapshot \
      -no-accel \
      -no-audio \
      -no-boot-anim \
      -camera-back none \
      -camera-front none &
}

# start emulator in background
run_emulator() {
    emulator -avd test \
        -no-snapshot \
        -no-audio \
        -no-boot-anim \
        -camera-back none \
        -camera-front none &
}


RUN_HEADLESS=0

for arg in "$@"
do
    case $arg in
        -h|--headless)
        RUN_HEADLESS=1
        shift # Remove -h|-headless from processing
        ;;
    esac
done


# ensure adb server has started
adb devices

sleep 2


if [[ ${RUN_HEADLESS} -eq 1 ]]
then
    run_headless_emulator
else
    run_emulator
fi


bootanim=""
failcounter=0
timeout_in_sec=600 # 10 minutes

until [[ "$bootanim" =~ "stopped" ]]; do
  bootanim=`adb -e shell getprop init.svc.bootanim 2>&1 &`
  # echo bootanim=\`$bootanim\`
  if [[ "$bootanim" =~ "device not found" || "$bootanim" =~ "device offline"
    || "$bootanim" =~ "running" || "$bootanim" =~  "error: no emulators found" ]]; then
    let "failcounter += 5"
    echo "Waiting for emulator to start"
    if [[ $failcounter -gt timeout_in_sec ]]; then
      echo "Timeout ($timeout_in_sec seconds) reached; failed to start emulator"
      exit 1
    fi
  fi
  sleep 5
done

echo "Emulator is ready"
