package com.yehigo.eagle.ychart

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast

/**
 * TODO: document your custom view class.
 */
class BarChartView : View {

    /**
     * input data holder . assuming
     */
    private  var _ploatingData: ArrayList<BarCharData>? = null

    private  var _minX : Float = Float.MAX_VALUE
    private  var _maxX :Float = 0.0f
    private  var _minY : Float = Float.MAX_VALUE
    private  var _maxY :Float = 0.0f
    private var _labelText :String = "X-Axis"
    private  var _labelTextSize:Float = 0.0f
    private  var _shiftX:Float=0.0f
    private  var _shiftY:Float=0.0f
    private var xfactor :Float = 0.0f
    private var yfactor :Float = 0.0f
    private var _barWidth : Float=0.0f
    private  var _toast : Toast? = null
    private var chartHeight: Float = 0.0f
    private var chartWidth: Float = 0.0f

    private lateinit var paint: Paint


    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {

        context?.theme?.obtainStyledAttributes(
            attrs,
            R.styleable.BarChartView,
            0, 0).apply {

            try {
                _labelText = this?.getString(R.styleable.BarChartView_labelText)!!
                _labelTextSize = this.getDimension(R.styleable.BarChartView_labelTextSize,40f)

            } finally {
                this?.recycle()

            }

            paint = Paint(Paint.ANTI_ALIAS_FLAG)
            /**
            paint.color = Color.BLUE
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 4.0f
            paint.textSize = _labelTextSize
            paint.textAlign = Paint.Align.CENTER
             **/
        }
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {

            // drawColor(Color.RED);
            drawBars(this)

        }
    }





    fun drawDataXAxisSorted(dataArray:ArrayList<BarCharData>?)
    {
        _ploatingData=dataArray

        setRange()

    }



    private fun drawBars(canvas: Canvas?)
    {
        if (canvas != null  ) {

            /**
             * get the canvas height and width in float type  for drawing objects on canvas
             */
            chartHeight  = measuredHeight.toFloat()
            chartWidth = measuredWidth.toFloat()

            /**
             * setPaint
             */
            paint.apply {
                color = Color.BLUE
                style = Paint.Style.STROKE
                strokeWidth = 3.0f
                textSize = _labelTextSize
                textAlign = Paint.Align.CENTER
            }

            /**
             * Draw the X-Axis label
             */
            canvas.drawText(_labelText, chartWidth / 2, chartHeight, paint)

            /**
             * get the height of drawable chart ( total height - height of X-Axis label
             * so X-Axis and and label don't overlap
             */
            chartHeight -= (paint.fontMetrics.descent - paint.fontMetrics.ascent)

            /**
             * Draw the Axises
             * |
             * |
             * |
             * |
             * |_____________________
             *      X-Axis label
             */
            canvas.drawLine(0.0f, chartHeight, 0.0f, 0.0f, paint)
            canvas.drawLine(0.0f, chartHeight, chartWidth, chartHeight, paint)
            // canvas.d


            /**
             * Shift the bars to by X-Axis height (which is X-Axis line width) to up
             */
            chartHeight -= paint.strokeWidth
            chartWidth -= paint.strokeWidth


            /**
             * get the width of bar
             */
            _barWidth = chartWidth / (_ploatingData?.size ?: 1)

            /**
             * Shift the bar line by width/2+axis-width/2 to +ve X
             */
            _shiftX = (_barWidth+paint.strokeWidth)/2

            chartWidth -= _barWidth


            /**
             * calculating the scaling factor
             */

            if((_maxX -_minX)>0)
                xfactor = chartWidth / (_maxX -_minX)
            else  xfactor = chartWidth

            yfactor = chartHeight / (_maxY)

            /**
             * setting the common paint property for bars
             */
            paint.style = Paint.Style.FILL
            paint.strokeWidth = _barWidth

            /**
             * var for variable loop inside
             */
            var tempx: Float
            var tempy: Float
            _ploatingData?.forEach {

                paint.color = it.color
                /**
                 * get the scaled and shifted  x and y  value  for canvas draw
                 */
                tempx = _shiftX + (it.x - _minX) * xfactor
                tempy = (it.y) * yfactor
                /**
                 * draw data on canvas
                 */
                canvas.drawLine(tempx, chartHeight - tempy, tempx, chartHeight, paint)

            }

        }
    }



    /**
     * function to get data array index of clicked bar
     * Logn  function with to get value of clicked bar
     */
    private fun getIndexOfX(x:Float): Int {
        var size = _ploatingData?.size?:0
        var t = size/2
        var returnValue :Int =-1
        var logFactor:Int =t

        while(t<size && t > -1 )
        {

            if(_ploatingData!!.get(t).x <= x)
            {
                returnValue =t
                    logFactor /= 2
                    t += if(logFactor!=0)
                        logFactor
                    else 1
            }
            else{
                size=t
                logFactor /= 2
                t -= if (logFactor != 0)
                    logFactor
                else 1
            }
        }
        return returnValue
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if (event != null) {

            val actualX: Float = if (xfactor != 0f) ((event.x) / xfactor) + _minX
            else _minX

            if ((_ploatingData?.size ?: 0) > 0) {
                _toast?.cancel()

                val ploatIndex = getIndexOfX(actualX)
                if ( ! (ploatIndex < 0)) {

                    val tempscaledY = (_ploatingData!![ploatIndex].y) * yfactor


                    /**
                     * show if click point is a bar
                     */
                    if (event.y <= chartHeight && event.y >= (chartHeight - tempscaledY)) {
                        _toast = Toast.makeText(
                            context,
                            (_ploatingData!![ploatIndex].y).toString(),
                            Toast.LENGTH_SHORT
                        )
                        _toast?.setGravity(
                            this.foregroundGravity,
                            event.x.toInt() + this.left,
                            event.y.toInt() + this.top
                        )
                        _toast?.show()
                    }


                }
            }

        }
        return super.onTouchEvent(event)

    }

    /**
     * clean max and min value of data
     */
    private  fun clearMAXMIN()
    {
        this._minX = Float.MAX_VALUE
        this._maxX  = 0.0f
        this._minY = Float.MAX_VALUE
        this._maxY  = 0.0f
    }

    /**
     * set max and min value of data for  x and y Axis
     */
    private fun setRange()
    {
        clearMAXMIN()
        _ploatingData?.forEach {

            if (it.x >_maxX){
                _maxX=it.x
            }
            if (it.x <_minX){
                _minX=it.x
            }
            if (it.y >_maxY){
                _maxY=it.y
            }
            if (it.y <_minY){
                _minY=it.y
            }
        }
        /**
         * request for redraw
         */
        invalidate()
        requestLayout()

    }

}
data class BarCharData(val x: Float = 0.0f, val y: Float = 0.0f,val color: Int = 0)
