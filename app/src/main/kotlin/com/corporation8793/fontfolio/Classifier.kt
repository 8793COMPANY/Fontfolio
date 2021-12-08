package com.corporation8793.fontfolio

import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel
import java.util.*

class Classifier(private val assetManager: AssetManager, private val modelName: String) {
    lateinit var interpreter: Interpreter
    private var modelInputChannel = 0
    private var modelInputWidth = 0
    private var modelInputHeight = 0
    private var modelOutputClasses = 0

    fun init() {
        val model = loadModelFile()
        model.order(ByteOrder.nativeOrder())
        Log.e("init", "통과")
        interpreter = Interpreter(model)
        initModelShape()
        Log.e("init", "end")
    }

    companion object {
        const val DIGIT_CLASSIFIER = "converted_model.tflite"
    }


    @Throws(IOException::class)
    private fun loadModelFile(): ByteBuffer {
        Log.e("in!!", "loadModelFile")
        val assetFileDescriptor = assetManager.openFd("converted_model.tflite")
        val fileInputStream = FileInputStream(assetFileDescriptor.fileDescriptor)
        val fileChannel: FileChannel = fileInputStream.getChannel()
        val startOffset = assetFileDescriptor.startOffset
        val len = assetFileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, len)
    }

    private fun resizeBitmap(bitmap: Bitmap) =
        Bitmap.createScaledBitmap(bitmap, modelInputWidth, modelInputHeight, true)


    private fun initModelShape(){
        val inputTensor = interpreter.getInputTensor(0)
        val inputShape = inputTensor.shape()
        modelInputChannel = inputShape[0]
        modelInputWidth = inputShape[1]
        modelInputHeight = inputShape[2]

        Log.e("modelInputChannel", modelInputChannel.toString())
        Log.e("modelInputWidth", modelInputWidth.toString())
        Log.e("modelInputHeight", modelInputHeight.toString())

        val outputTensor = interpreter.getOutputTensor(0)
        val outputShape = outputTensor.shape()
        modelOutputClasses = outputShape[1]
    }

    private fun convertBitmapGrayByteBuffer(bitmap: Bitmap): ByteBuffer {
        val input = ByteBuffer.allocateDirect(bitmap.byteCount * 3).order(ByteOrder.nativeOrder())
        for (y in 0 until 128) {
            for (x in 0 until 128) {
                val px = bitmap.getPixel(x, y)

                // Get channel values from the pixel value.
                val r = Color.red(px)
                val g = Color.green(px)
                val b = Color.blue(px)

                // Normalize channel values to [-1.0, 1.0]. This requirement depends on the model.
                // For example, some models might require values to be normalized to the range
                // [0.0, 1.0] instead.
                val rf = (r - 127) / 255f
                val gf = (g - 127) / 255f
                val bf = (b - 127) / 255f

                input.putFloat(rf)
                input.putFloat(gf)
                input.putFloat(bf)
            }
        }

//        val bufferSize = 11 * java.lang.Float.SIZE / java.lang.Byte.SIZE
//        val modelOutput = ByteBuffer.allocateDirect(bufferSize).order(ByteOrder.nativeOrder())
        return input
    }

    fun classify(image: Bitmap): FloatArray  {
        Log.e("classify", "in!!!")
        val buffer = convertBitmapGrayByteBuffer(image)
        val result = Array(1) { FloatArray(modelOutputClasses) { 0f } }
        interpreter.run(buffer, result)
        return result[0]
    }

    private fun argmax(array: FloatArray): Pair<Int, Float> {
        var maxIndex = 0
        var maxValue = 0f
        array.forEachIndexed { index, value ->
            Log.e("index", index.toString())
            if (value > maxValue) {
                maxIndex = index
                maxValue = value
            }
        }
        return maxIndex to maxValue
    }

}