CircularRevealCompat
====================
[![Build Status](https://api.travis-ci.org/yangwencan2002/CircularRevealCompat.svg?branch=master)](https://travis-ci.org/yangwencan2002/CircularRevealCompat/)
[![Download](https://api.bintray.com/packages/yangwencan2002/maven/CircularRevealCompat/images/download.svg)](https://bintray.com/yangwencan2002/maven/CircularRevealCompat/_latestVersion)
[![License](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![QQ Approved](https://img.shields.io/badge/QQ_Approved-1.0.0-red.svg)](https://github.com/yangwencan2002/CircularRevealCompat)

Introduction
------
Android 5.0+ API ViewAnimationUtils.createCircularReveal compatible for 4.0+.

![CircularRevealCompat](https://raw.github.com/yangwencan2002/CircularRevealCompat/master/screencap.gif)

Gradle
------
```
dependencies {
    ...
    compile 'com.vincan:circularrevealcompat:1.0.0'
}
```

Usage:
------
XML
```xml
<com.vincan.circularrevealcompat.CircularRevealCompatLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/reveal_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <!-- your original layout here -->
    ...

</com.vincan.circularrevealcompat.CircularRevealCompatLayout>
```

Java
```java

    View touchView = ...;
    View revealView = findViewById(R.id.reveal_view);

    //get the center point for the clipping circle
    int startCenterX = (int) (touchView.getX() + touchView.getWidth() / 2);
    int startCenterY = (int) (touchView.getY() + touchView.getHeight() / 2);
    int endCenterX = revealView.getWidth() / 2;
    int endCenterY = revealView.getHeight() / 2;

    //get the radius for the clipping circle
    float startRadius = 0;
    float finalRadius = Math.max(revealView.getWidth(), revealView.getHeight()) * 1.1f;

    Animator animator =
            ViewAnimationCompatUtils.createCircularReveal(revealView, startCenterX, startCenterY, startRadius, endCenterX, endCenterY, finalRadius);
    animator.setDuration(500);
    animator.start();

```

Sample
------
See `sample` project.

Approved app
------
#### QQ
![QQ](https://raw.github.com/yangwencan2002/CircularRevealCompat/master/screencap_QQ.gif)

Release notes
------
[here](https://github.com/yangwencan2002/CircularRevealCompat/releases)

Where released
------
[bintray.com](https://bintray.com/yangwencan2002/maven/CircularRevealCompat)

License
-------

    Copyright 2016-2017 Vincan Yang

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
