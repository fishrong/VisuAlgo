# 算法可视化的Android实现
此项目主要是对一些经典的算法在Android上实现可视化的运行过程。项目目的是为了帮助理解算法的运行过程，所以app实现的是对数据类型为整型的数组排序，实际中的算法可以是对任何实现Comparable接口的数据类型进行排序,且app 中也没有各算法间的性能优劣进行体现。目前已完成排序的一些经典算法，项目仍在完善中。

## 已完成算法
* 选择排序
* 插入排序
* 希尔排序
* 归并排序
* 快速排序

## app实现功能
* 算法运行过程的可视化。排序算法利用柱状图的形式来表现各个过程，不同元素有颜色标注。
* 运行的代码高亮显示。对于算法所执行的代码配合柱状图以高亮的形式展现。
* 可自定义排序数组。app中有默认的排序数组，也可自己手动输入。只能输入数字且尽量不大于9，元素以空格分开。
* 两种运行模式。可以让程序自己运行，也可以自己通过按钮一步一步执行，且可以随时切换。
* 相应算法有文字说明。

## app截图
主界面：  
<div align=center><img s src="https://github.com/fishrong/VisuAlgo/raw/master/Screenshots/home.png"/></div>
算法界面：  
<div align=center><img width="216" height="386" src="https://github.com/fishrong/VisuAlgo/raw/master/Screenshots/algo.png"/>
算法说明：  
<div align=center><img width="216" height="386" src="https://github.com/fishrong/VisuAlgo/raw/master/Screenshots/help.png"/>
排序界面：  
<div align=center><img width="216" height="386" src="https://github.com/fishrong/VisuAlgo/raw/master/Screenshots/sort.png"/>
