XDroid-Databinding 轻量级的Android快速开发框架

[![](https://jitpack.io/v/fodroid/XDroid-DataBinding.svg)](https://jitpack.io/#fodroid/XDroid-DataBinding)

# 概述

<p align="center">
	<img src="xdroid_logo_128.png"/>
</p>

XDroid-Databinding是XDroid Android快速开发框架的Databinding版本，其使用方式类似于XDroid，大部分源码也来自XDroid。由UI、Cache、Event、ImageLoader、Kit、Log、Router、Net等几个部分组成。

> Demo详细使用可参看Demo

## XDroid-Base

XDroid-Base包含开发中常用的一些快速开发类，可作为一个简单的开发框架,由Base、Cache、ImageLoader、Kit、Log、Router等几个部分组成。可快速、自由的按需扩展进行App开发。

传送门：[**https://github.com/fodroid/XDroid-Base**](https://github.com/fodroid/XDroid-Base)

## XDroid

XDroid是一个轻量级的Android快速开发框架，基于XDroid-Base，在此基础上增加了由UI、Event、Net等几个部分组成。其设计思想是使用接口对各模块解耦规范化，不强依赖某些明确的三方类库，使得三方类库可自由搭配组装，方便替换。可快速、自由的进行App开发。

传送门：[**https://github.com/limedroid/XDroid**](https://github.com/limedroid/XDroid)

## XDroidMvp

XDroidMvp是基于XDroid的MVP实现，不是传统意义的MVP，不需写接口，可无缝切换MVC，可能是当前最好用的MVP框架。

传送门：[**https://github.com/limedroid/XDroidMvp**](https://github.com/limedroid/XDroidMvp)

## XDroidMvp-Databinding

XDroidMvp-Databinding是基于XDroidMvp的Databinding版本，大部分源码也与XDroidMvp类似。

传送门：开发中

XDroid推出以来，得到了很多朋友的快速反馈，目前有很多朋友在新项目或是老项目中使用XDroid，为了方便学习和交流，可以加入QQ群：

XDroid交流群：**153569290**

# v1.4更新

* 更新网络请求为OkHttp+Retrofit2+RxJava2
* 使用Rxlifecycle管理，防止rx出现内存泄漏


# 特性

**XDroid-Databinding**主要有这些特性：

* 基于MVC，可快速切换到MVP
* 提供`XActivity`、`XFragment`、`XLazyFragment`等基类，可快速进行开发
* 内置了`EventBus`，可自由切换到其他事件订阅库
* 使用retrofit网络加载库，搭配OKhttp与Rxjava
* 引入三方库极少
* 使用Google官方Databinding
* 基于XDroid-Base，包含常用的快速开发工具类。
* 提供`SimpleRecAdapter`、`SimpleListAdapte`、`XRecyclerAdapter`、`XListAdapter`等基类
* 实现了Memory、Disk、SharedPreferences三种方式的缓存，可自由扩展
* 内置`Glide`，可自由切换其他图片加载库
* 可输出漂亮的`Log`，支持Json、Xml、Throwable等，蝇量级实现
* 内置链式路由
* 内置`Toast`工具类
* 内置常用工具类：`package`、`random`、`file`...,提供的都是非常常用的方法
* 内置加密工具类 `XCodec`，你想要的加密姿势都有

详细说明请前往[XDroid-Databinding wiki](https://github.com/fodroid/XDroid-Databinding/wiki)


# Get Started
首先在您的app module 中 添加如下依赖: 
```groovy
android {
    dataBinding {
        enabled true
    }
}
```

### 通过JitPack引入

#### step1 在根目录的gradle文件中配置:
```groovy
allprojects {
    repositories {
        jcenter()
        maven { url "https://jitpack.io" }
    }
}
```

#### step2 添加依赖:
```groovy
dependencies {
	        compile 'com.github.fodroid:XDroid-Databinding:v1.4'
}
```


# Demo效果

<p align="center">
	<img src="art/snapshot_2.png"/>
</p>

<p align="center">
	<img src="art/snapshot_1.png"/>
</p>


# 重要说明

* [EventBus](https://github.com/greenrobot/EventBus)使用的是3.0.0版本,使用注解`@Subscribe`，具体如何使用可以查看官网。


# 相关文档

[我是如何搭建Android快速开发框架的(概述)](http://www.jianshu.com/p/cde5468029b4)

[我是如何搭建Android快速开发框架的之UI篇(上)](http://www.jianshu.com/p/c909f72cdd02)

# 感谢

* 感谢干货提供的api


# 关于我们
### droidlover
**Email** : droidlover@126.com

**Github** : https://github.com/limedroid

**简书**：http://www.jianshu.com/u/276be5744ca0
### fodroid
**Email** : me.shihao@qq.com

**Github** : https://github.com/fodroid

**简书**：http://www.jianshu.com/u/caf7ea3607ed
