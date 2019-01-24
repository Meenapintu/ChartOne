package com.yehigo.eagle.chartone

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import com.yehigo.eagle.chartone.R.id.chartone
import com.yehigo.eagle.ychart.BarCharData

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var chartOne :com.yehigo.eagle.ychart.BarChartView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        chartOne = findViewById(chartone)
        var i=0;
        val a :ArrayList<BarCharData> = ArrayList()
        val color: IntArray = intArrayOf(Color.RED, Color.BLACK,Color.BLUE, Color.MAGENTA, Color.YELLOW)
        while(i<30)
        {

            a.add(BarCharData(i.toFloat(),(0..1000).random().toFloat(),color[i%5]))
            i+=10;
        }

        chartOne.drawDataXAxisSorted(a)
        var j =2
        fab.setOnClickListener { view ->

            var i=0;
            a.clear()
            while(i<j)
            {
                a.add(BarCharData(i.toFloat(),(0..1000).random().toFloat(),color[(0..4).random()]))
                i+=1;
            }
            chartOne.drawDataXAxisSorted(a)
            Snackbar.make(view, "DataSize :"+j, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            j+=2

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
