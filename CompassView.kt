package com.murat.gps_assignment03

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

var degree:Float = 0f

class CompassView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint:Paint = Paint()
    var rotationValue:Float=90f

    override fun onDraw(canvas: Canvas?) {
        canvas?:return
        super.onDraw(canvas)

        val _width:Float = width.toFloat()
        val _height:Float = height.toFloat()
        val _radius = _width * 0.7f / 2


        paint.setColor(Color.BLACK)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10f
        canvas.drawCircle(_width/2f,_height/2f,_radius,paint)

        canvas.drawLine(_width/2f,_height/2f+100f,_width/2f,_height/2f-_radius,paint)
        canvas.drawLine(_width/2f-100f,_height/2f,_width/2f+100f,_height/2f,paint)
        canvas.save()
        paint.setColor(Color.RED)
        paint.textSize = 70f

        canvas.rotate(degree,_width/2f,_height/2f)

        canvas.drawText("N",_width/2f - 25f ,_height/2f - _radius - 50f,paint)

        paint.setColor(Color.BLACK)

        canvas.drawText("E",_width/2f + _radius +30f, _height/2f + 25f,paint)
        canvas.drawText("S",_width/2f - 25f, _height / 2 + _radius + 90f,paint)
        canvas.drawText("W",_width/2f - _radius - 90f,_height/2f + 25f,paint)

        canvas.restore()
        canvas.save()

    }
    fun rotationValue(Rotation:Float){
        this.rotationValue=Rotation
        degree = Rotation
        invalidate()
    }
}