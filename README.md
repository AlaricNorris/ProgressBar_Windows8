ProgressBar_Windows8
山寨Windows8的加载进度框
====================
仿windows8进度框
使用方法
====================
1.将项目文件中的libs文件夹下的jar包取出《win8progressbar.jar》，放入你项目中的libs中去

2.在layout中想要用到进度框的地方添加以下代码即可

    <com.alaric.norris.widget.progressbar.Win8ProgressBar
        android:layout_width="100dip"
        android:layout_height="100dip"
        android:background="#FF0000" />
3.代码说明：

	其中width和height最好保持一致，若不一样，则会由最小的一方来决定进度框的大小，并且gravity是left|top的！
	其次，若想改变小球的颜色，只要改掉android:background属性即可，只能用color(@color/~~~~)，切忌用图片(@drawable/~~~~)。
