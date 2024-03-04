package com.example.lab2_2

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.View
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class MyGraphView(context: Context?) : View(context) {
    private lateinit var path: Path
    private var mPaint: Paint? = null
    private var mBitmapPaint: Paint? = null
    private var mBitmap : Bitmap? = null
    private var mCanvas: Canvas? = null
    init {

        mBitmapPaint = android.graphics.Paint(Paint.DITHER_FLAG)
        mPaint = Paint()
        mPaint!!.setAntiAlias(true)
        mPaint?.setColor(Color.GREEN)
        mPaint?.setStyle(Paint.Style.STROKE)
//        mPaint?.set

        mPaint?.setStrokeJoin(Paint.Join.ROUND)
        mPaint?.setStrokeCap(Paint.Cap.ROUND)
        mPaint?.setStrokeWidth(12F)
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)


        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mBitmap!!)
//        Toast.makeText(this.context, "onSizeChanged ", Toast.LENGTH_SHORT).show()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawBitmap(mBitmap!!, 0f, 0f, mBitmapPaint!!)
    }

    fun setColor(color: Int){
        mPaint?.setColor(color)
    }
    fun drawCircle() {
        println("mCanvas = $mCanvas")
        mCanvas!!.drawCircle(100f, 100f, 50f, mPaint!!)
        invalidate()
    }
    fun drawSquare() {
        println("mCanvas = $mCanvas")
        mCanvas!!.drawRect(200f, 200f, 300f, 300f, mPaint!!)
        invalidate()
    }
    fun drawFace() {
        val mBitmapFromSdcard = BitmapFactory.decodeFile("/sdcard/Download/tray_large.png")
        mCanvas!!.drawBitmap(mBitmapFromSdcard, 100f, 100f, mPaint)
        invalidate()
    }

    fun uploadImage(){
//        mPaint!!.setColor(Color.WHITE)
//        mCanvas!!.drawRect(0f, 0f, width.toFloat(), height.toFloat(), mPaint!!)
        val mBitmapFromSdcard = BitmapFactory.decodeFile(context.getExternalFilesDir(null)!!.absolutePath + "/my.PNG")
        mCanvas!!.drawBitmap(mBitmapFromSdcard, 0f, 0f, mPaint)
        invalidate()
    }

    fun drawName(){
        mPaint!!.textSize = 200f
        mPaint!!.setStyle(Paint.Style.FILL_AND_STROKE)
        mCanvas!!.drawText("Nazmiev ❄️", 0f, 300f, mPaint!!)
        mPaint!!.setStyle(Paint.Style.STROKE)
        invalidate()
    }
    fun onSaveClick() {
        val destPath: String = context.getExternalFilesDir(null)!!.absolutePath
        var outStream: OutputStream? = null
        val file = File(destPath, "my.PNG")
        println("path = $destPath")
        outStream = FileOutputStream(file)


        mBitmap!!.compress(Bitmap.CompressFormat.PNG, 100, outStream)
        outStream.flush()
        outStream.close()
    }

    val funcArray = arrayOf(::drawSquare, ::drawCircle, ::drawFace, ::drawName)

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path = Path()
                path.moveTo(event.x, event.y)
            }


            MotionEvent.ACTION_MOVE, MotionEvent.ACTION_UP -> path.lineTo(event.x,event.y)
        }
        if (path != null) {
            println("mCanvas = $mCanvas")
            mCanvas!!.drawPath(path, mPaint!!)
            invalidate()
        }
        return true
    }
}