package ir.sinamomken.karafs_interview2

import android.graphics.Color
import android.graphics.Point
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //createDrawView()
        inch_rb.setOnClickListener {
            canvas_dv.changeUnit(DrawView.Unit.INCH)
        }
        cm_rb.setOnClickListener {
            canvas_dv.changeUnit(DrawView.Unit.CM)
        }
    }

}