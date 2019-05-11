# RUtils
[![](https://jitpack.io/v/rain9155/RUtils.svg)](https://jitpack.io/#rain9155/RUtils) 
[![](license.png)](http://www.apache.org/licenses/LICENSE-2.0)
### Rain的工具库，一个简单实用工具库，功能还在继续完善！
### Features
|工具|功能|
|--|--|
|[ActivityCollector](utilslibrary/src/main/java/com/example/utilslibrary/ActivityCollector.java)|活动管理器,随时随地退出活动|
|[AnimUtil](utilslibrary/src/main/java/com/example/utilslibrary/AnimUtil.java)|View显示和隐藏动画|
|[DisplayUtil](utilslibrary/src/main/java/com/example/utilslibrary/DisplayUtil.java)|屏幕相关工具类，如获取屏幕宽高|
|[FileUtils](utilslibrary/src/main/java/com/example/utilslibrary/FileUtils.java)|文件存储相关工具类|
|[HttpUnit](utilslibrary/src/main/java/com/example/utilslibrary/HttpUnit.java)|网络请求工具类,异步回调|
|[ImageResizer](utilslibrary/src/main/java/com/example/utilslibrary/ImageResizer.java)|图片解析压缩相关工具类|
|[KeyBoardUtil](utilslibrary/src/main/java/com/example/utilslibrary/KeyBoardUtil.java)|软键盘显示与隐藏工具类|
|[NetWorkUtil](utilslibrary/src/main/java/com/example/utilslibrary/NetWorkUtil.java)|判断网络情况工具类|
|[ShareUtil](utilslibrary/src/main/java/com/example/utilslibrary/ShareUtil.java)|分享工具类|
|[StatusBarUtil](utilslibrary/src/main/java/com/example/utilslibrary/StatusBarUtil.java)|状态栏变色工具类|
|[ToastUtil](utilslibrary/src/main/java/com/example/utilslibrary/ToastUtil.java)|Toast工具类|
|[ServceUtil](utilslibrary/src/main/java/com/example/utilslibrary/ServiceUtil.java)|Servce工具类|
|[TimeUtil](utilslibrary/src/main/java/com/example/utilslibrary/TimeUtil.java)|时间相关工具类|
### How to use?
* **Step one** <br>

在Project的build.gradle 中添加仓库地址, 示例如下：
```java
allprojects {
    repositories {
        jcenter()
        // JitPack仓库地址
        maven { url "https://jitpack.io" }
    }
}
```
* **Step two** <br>

.在Module目录下的build.gradle中添加依赖, 示例如下：
```java
dependencies {
    implementation 'com.github.rain9155:RUtils:v1.0'
}

```
### Version
```
v1.0.1 2018-12-16
添加ServiceUtil
添加TimeUtil
完善一些Util

v1.0 2018-12-3 
第一版发布，添加了基本的工具类
```
### License
```
 Copyright 2018 rain9155
 
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
