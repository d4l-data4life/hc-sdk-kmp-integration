#!/bin/bash

gcloud firebase test android run \
          --type instrumentation \
          --app app/build/outputs/apk/development/debug/app-development-debug.apk \
          --test app/build/outputs/apk/androidTest/development/debug/app-development-debug-androidTest.apk \
          --device model=NexusLowRes,version=24,locale=de,orientation=portrait \
          --timeout 300s
