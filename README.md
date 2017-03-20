ActionDialog
==========
[![Build Status](https://travis-ci.org/skyline1631/ActionDialog.svg?branch=master)](https://travis-ci.org/skyline1631/ActionDialog)
[ ![Download](https://api.bintray.com/packages/skyline1631/maven/action-dialog/images/download.svg?version=1.0.1) ](https://bintray.com/skyline1631/maven/action-dialog/1.0.1/link)

A action sheets dialog for Android, which representing similar UI to the UIActionSheet in iOS.

Snapshot
----------
<img width="225" height="400" src="/static/snapshot_i.png"/> <img width="225" height="400" src="/static/snapshot_ii.png"/> <img width="225" height="400" src="/static/snapshot_iii.png"/>

Download
----------
Gradle:

```gradle
repositories {
  jcenter()
}

dependencies {
  compile 'com.skyline.widget:action-dialog:1.0.1'
}
```

Maven:

```xml
<dependency>
  <groupId>com.skyline.widget</groupId>
  <artifactId>action-dialog</artifactId>
  <version>1.0.1</version>
  <type>pom</type>
</dependency>
```

Usage
----------
1. Basic

```java
ActionDialog dialog = new ActionDialog(this);
dialog.setTitle("Title");
dialog.setMessage("Message");
dialog.addAction("Default");
dialog.addAction("Destructive", true);
dialog.setEventListener(this);
dialog.show();
```

2. Actions only

```java
ActionDialog dialog = new ActionDialog(this);
dialog.setActions(R.array.mobile_os_entries);
dialog.setCancelVisible(false);
dialog.setEventListener(this);
dialog.show();
```

Sample
----------
Click [here](https://raw.githubusercontent.com/skyline1631/ActionDialog/master/static/action-dialog-sample.apk) to download the sample apk.

License
----------

    Copyright (C) 2017 Legolas Kwok.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
