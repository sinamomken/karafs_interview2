package ir.sinamomken.karafs_interview2

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.os.Build
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import androidx.core.content.res.ResourcesCompat
import java.lang.Exception

class DrawView : View {
    constructor(myContext: Context) : super(myContext)
    constructor (myContext: Context, attributeSet: AttributeSet) : super(myContext, attributeSet)
    constructor (myContext: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(myContext, attributeSet, defStyleAttr)

    var scrWidthIn:Float = 1F
    var scrHeightIn:Float = 1F
    var scrWidthPx: Int = 1
    var scrHeightPx: Int = 1
    var xdpi:Float = 1F
    var ydpi:Float = 1F

    val RULER_OFFSET_X = 20F
    val RULER_OFFSET_Y = 20F
    val INDICATOR_WIDTH = 20F

    var unit:Unit = Unit.INCH

    init {
        calculateRealScreenSize()
    }

    override fun onDraw(canvas: Canvas?) {
        drawRulerLines(canvas)
        drawRulerIndicators(canvas)
    }

    fun changeUnit(unit: Unit){
        this.unit = unit
        invalidate()
    }

    fun drawRulerLines(canvas: Canvas?){
        val paint = Paint()
        paint.setColor(Color.BLACK)
        paint.strokeWidth = 5F

        // draw vertical line
        canvas?.drawLine(RULER_OFFSET_X, RULER_OFFSET_Y, RULER_OFFSET_X, scrHeightPx.toFloat(), paint)

        // draw horizontal line
        canvas?.drawLine(RULER_OFFSET_X, RULER_OFFSET_Y, scrWidthPx.toFloat(), RULER_OFFSET_Y , paint)
    }

    fun drawRulerIndicators(canvas: Canvas?){
        val paint = Paint()
        paint.setColor(Color.GRAY)
        paint.strokeWidth = 3F
        paint.typeface = ResourcesCompat.getFont(context, R.font.bnazanin)
        paint.textSize = 36F

        //draw vertical indicators
        var nextYPx : Float = RULER_OFFSET_Y
        var counterY : Int = 0
        val yStepPx : Float = ydpi/unit.perInch
        while(nextYPx < scrHeightPx) {
            nextYPx += yStepPx
            counterY++
            canvas?.drawLine(RULER_OFFSET_X, nextYPx, RULER_OFFSET_X + INDICATOR_WIDTH, nextYPx, paint)
            canvas?.drawText(counterY.toString(), RULER_OFFSET_X + 2*INDICATOR_WIDTH, nextYPx, paint )
        }

        //draw horizontal indicators
        var nextXPx : Float = RULER_OFFSET_X
        var counterX : Int = 0
        val xStepPx : Float = xdpi/unit.perInch
        while(nextXPx < scrWidthPx) {
            nextXPx += xStepPx
            counterX++
            canvas?.drawLine(nextXPx, RULER_OFFSET_Y, nextXPx, RULER_OFFSET_Y + INDICATOR_WIDTH, paint)
            canvas?.drawText(counterX.toString(), nextXPx, RULER_OFFSET_Y + INDICATOR_WIDTH + paint.textSize, paint )
        }
    }

    fun calculateRealScreenSize(){
        if (Build.VERSION.SDK_INT >= 17) {
            var size: Point = Point()
            var displayMetrics = DisplayMetrics()
            try {
                displayMetrics = context.resources.displayMetrics
                scrWidthPx = displayMetrics.widthPixels
                scrHeightPx = displayMetrics.heightPixels
                Log.i("screen size px:", scrWidthPx.toString() + "x" + scrHeightPx.toString())
                xdpi = displayMetrics.xdpi
                ydpi = displayMetrics.ydpi
                scrWidthIn = scrWidthPx.toFloat() / xdpi
                scrHeightIn = scrHeightPx.toFloat() / ydpi
                Log.i("screen density xdpi:", xdpi.toString())
                Log.i("screen density ydpi:", ydpi.toString())
                Log.i("screen size in:", scrWidthIn.toString() + "x" + scrHeightIn.toString())
            } catch (e: Exception) {
                Log.e("error", "it can't work");
            }
        }
    }

    enum class Unit(val perInch: Float){
        INCH(1F), CM(2.54F)
    }
}