# ChartOne
A bar chart view  implementation for android  , 


#how to use  (Android Studio)
include ychart Module in application 
this use CustomView BarChartView 

Like.

    <com.yehigo.eagle.ychart.BarChartView
                  android:id="@+id/chartone"
                  android:layout_width="match_parent"
                  android:layout_height="match_parent"
                  app:labelText="X-Axis Level"
                  app:labelTextSize="20sp"
                  android:layout_gravity="right"
                  android:layout_margin="10dp"
          />


#set data programatically 

An example (Kotlin):

        chartOne = findViewById(chartone)
        var i=0;
        val a :ArrayList<BarCharData> = ArrayList()
        val color: IntArray = intArrayOf(Color.RED, Color.BLACK,Color.BLUE, Color.MAGENTA, Color.YELLOW)
        while(i<11)
        {

            a.add(BarCharData(i.toFloat(),(0..1000).random().toFloat(),color[i%5]))
            i++;
        }

        chartOne.drawDataXAxisSorted(a)
  
BarChartData is a data class as follows 

      data class BarCharData(val x: Float = 0.0f, val y: Float = 0.0f,val color: Int = 0)

#!important :
#  Array a should be  sorted value by X axis Value (BarChartData.x ) 



![alt text](https://github.com/Meenapintu/ChartOne/blob/master/imgs/File1.png)



![alt text](https://github.com/Meenapintu/ChartOne/blob/master/imgs/File9.png)

![alt text](https://github.com/Meenapintu/ChartOne/blob/master/imgs/File10.png)


![alt text](https://github.com/Meenapintu/ChartOne/blob/master/imgs/File11.png)

![alt text](https://github.com/Meenapintu/ChartOne/blob/master/imgs/File2.png)

![alt text](https://github.com/Meenapintu/ChartOne/blob/master/imgs/File4.png)

![alt text](https://github.com/Meenapintu/ChartOne/blob/master/imgs/File5.png)
![alt text](https://github.com/Meenapintu/ChartOne/blob/master/imgs/File6.png)

![alt text](https://github.com/Meenapintu/ChartOne/blob/master/imgs/File7.png)
