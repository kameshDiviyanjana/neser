package com.example.cataract_and_conjunctivitis_detection

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.cataract_and_conjunctivitis_detection.ml.Model
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder

class cataract_detection : AppCompatActivity() {

    lateinit var imageout : TextView

    var imageSize = 32
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cataract_detection)
        var picture = findViewById<Button>(R.id.button)
        var  picksother = findViewById<Button>(R.id.pickimage)
        imageout = findViewById(R.id.result)
        picksother.setOnClickListener {

            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 3)
        }

        picture.setOnClickListener {

            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, 1)
            } else {
                //Request camera permission if we don't have it.
                requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
            }
        }


    }

    @SuppressLint("SuspiciousIndentation")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        var result = findViewById<TextView>(R.id.result)
        var confidence = findViewById<TextView>(R.id.confidence)
        var  imageView = findViewById<ImageView>(R.id.imageView)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            var image = data!!.extras!!["data"] as Bitmap?
            val dimention = Math.min(image!!.width, image.height)
            image = ThumbnailUtils.extractThumbnail(image, dimention, dimention)
            imageView.setImageBitmap(image)
            image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false)
                  imageclarify(image)
        } else {
            val datax = data!!.data
            var image: Bitmap? = null
            try {
                image = MediaStore.Images.Media.getBitmap(this.contentResolver, datax)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            imageView.setImageBitmap(image)
            image = Bitmap.createScaledBitmap(image!!, imageSize, imageSize, false)
            imageclarify(image)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun imageclarify(image: Bitmap?) {


        val model = Model.newInstance(this)

// Creates inputs for reference.
        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 32, 32, 3), DataType.FLOAT32)
        val byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
        byteBuffer.order(ByteOrder.nativeOrder())
        val intvalues = IntArray(imageSize * imageSize)
        image!!.getPixels(intvalues, 0, image.width, 0, 0, image.width, image.height)
        var pixel = 0
        for (j in 0 until imageSize) {
            for (i in 0 until imageSize) {
                val `val` = intvalues[pixel++]
                byteBuffer.putFloat((`val` shr 16 and 0xFF) * (1f / 1))
                byteBuffer.putFloat((`val` shr 8 and 0xFF) * (1f / 1))
                byteBuffer.putFloat((`val` and 0xFF) * (1f / 1))
            }
        }
        inputFeature0.loadBuffer(byteBuffer)

// Runs model inference and gets result.
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer
        val confidences = outputFeature0.floatArray
        var maxpos = 0
        var maxConfidence = 0f
        for (i in confidences.indices) {
            if (confidences[i] > maxConfidence) {
                maxConfidence = confidences[i]
                maxpos = i
            }

        }
        print(maxpos)
        val classarys = arrayOf("cataract", "normal")

       imageout!!.text = classarys[maxpos]

// Releases model resources if no longer used.
        model.close()
    }


}