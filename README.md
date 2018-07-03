# Gesundheitscloud SDK integration test suit

[![Build Status](https://travis-ci.com/gesundheitscloud/hc-sdk-android-integration.svg?token=NeEVpUUDpspgyYoAyV8A&branch=master)](https://travis-ci.com/gesundheitscloud/hc-sdk-android-integration)

This is an integration of the [Gesundheitscloud SDK](https://github.com/gesundheitscloud/hc-sdk-android) for testing purposes.

A suit of UI tests to identify breaking changes and regressions by SDK version changes.


### Requirements

* Android 5.0.1 (API 21)
* Kotlin 1.2.50
* Java 8
* Gradle 4.8.1
* Android Studio 3.2 Beta 2
* Android Emulator


### Dependencies

* [Gesundheitscloud SDK](https://github.com/gesundheitscloud/hc-sdk-android)


### Usage

Run gradle wrapper task for connected tests:

```bash
./gradlew connectedAndroidTest
```

### Test suit

- [ ] Login
- [ ] Logout
- [ ] Document CRUD
- [ ] Report CRUD
- [ ] Observation CRUD
- [ ] Fetch record by type
- [ ] Count total records
- [ ] Count records per type
