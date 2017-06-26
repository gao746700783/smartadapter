# SmartRecyclerView-master

### Description

开始使用RecyclerView已经好几年了，使用都局限在最基本的使用方式上，只有在用到某个特性时才会去研究一番，这次索性把以前用到过
的，和其他类似的布局中用到过的都一并整理下，方便以后的使用；

相似的ListView，GridView ，都已经有大量的开源框架了，实现的够好，优化也基本上无盲点了，唯独RecyclerView感觉用过几个，不
是特别好用，总是出现各种各样的问题，累觉不爱，不如从头写一个来的彻底。

基本使用方法，经常使用到的方法，哈哈，都比较完备了

> 贴上google官方地址：
https://developer.android.google.cn/reference/android/support/v7/widget/RecyclerView.html

介绍如下：
> A flexible view for providing a limited window into a large data set.


### Features：

- 基本用法
- 分割线DividerItemDecoration
- 点击效果
- 下拉刷新和上拉加载更多
- 空状态（EmptyView），网络异常状态
- Drag-n-drop
- 线性布局／表格布局／流布局切换
- Filter／search
- 和ListView和GridView的无缝切换
- Animation
- 和SearchView的结合使用

### Usage

Quick Setup (Basic Usage)
1. Using Gradle:

`
repositories {
    jcenter()
}
dependencies {
    ...
    compile 'com.marshalchen.ultimaterecyclerview:library:0.7.2'
}
`

or grab via Maven

`
<dependency>
  <groupId>com.marshalchen.ultimaterecyclerview</groupId>
  <artifactId>library</artifactId>
  <version>0.7.2</version>
</dependency>
`

2. Usage:

`
<com.marshalchen.ultimaterecyclerview.UltimateRecyclerView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/ultimate_recycler_view"
/>
`

For more details, you can read the Wiki and the demo of the project.

### Version Log


### TODO


### Screenshot

### Thanks

- RecyclerView详细介绍&使用。([http://blog.csdn.net/Crystal_Plum9/article/details/51913891]) 
- 

### Donations

### License


to be continued...