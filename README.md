# hat-view

[![API](https://img.shields.io/badge/API-15%2B-blue.svg?style=flat)](https://android-arsenal.com/api?level=15)
[![License](https://img.shields.io/badge/license-Apache%202-red.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)
[![](https://jitpack.io/v/lndmflngs/hat-view.svg)](https://jitpack.io/#lndmflngs/hat-view)

<img src="https://github.com/lndmflngs/hat-view/blob/master/screenshots/1.png?raw=true" width="35%" />

## What?
Library that allow to put "hat" on TextView. Inspired by Telegram appbar title with Santa Claus hat 🎅🏻

## Usage
See `app` directory

### From XML
The simplest way is to use `HatTextView` like a normal `TextView`
```xml
   <com.lockwood.hat.HatTextView
        android:id="@+id/test"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Argh!"
        app:hat="@drawable/ic_hat_pirate" />
```

## Download
Download the [latest release][1] or grab via Gradle:

```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
```
dependencies {
    	implementation 'com.github.lndmflngs:hat-view:1.0.4'
}
```
## Issue Tracking
Found a bug? Have an idea for an improvement? Feel free to [add an issue](../../issues)

## License

```
Copyright (C) 2020 Ivan Zinovyev (https://github.com/lndmflngs)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
[1]: https://github.com/lndmflngs/hat-view/releases/latest
