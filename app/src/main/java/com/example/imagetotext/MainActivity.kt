package com.example.imagetotext

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class MainActivity : AppCompatActivity() {
    lateinit var result: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        result = findViewById(R.id.edtResult)
        val camera = findViewById<ImageView>(R.id.btnCamera)
        val erase = findViewById<ImageView>(R.id.btnErase)
        val copy = findViewById<ImageView>(R.id.btnCopy)


        camera.setOnClickListener {
            val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            if (i.resolveActivity(packageManager)!= null){
                startActivityForResult(i,123)
            }else{
                    Toast.makeText(this, "Something went wrong...",Toast.LENGTH_LONG).show()
            }
            erase.setOnClickListener {
                result.setText("")
            }

            copy.setOnClickListener{
                val clipBoard = getSystemService(Context.CLIPBOARD_SERVICE) as  ClipboardManager
                val clip = ClipData.newPlainText("label", result.text.toString())
                clipBoard.setPrimaryClip(clip)
                Toast.makeText(this, "Copied to ClipBoard",Toast.LENGTH_LONG).show()

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 123 && resultCode == RESULT_OK) {
            val extras = data?.extras
            val bitmap = extras?.get("data") as Bitmap
            detectTextUsingML(bitmap)
        }
    }


    private fun detectTextUsingML(bitmap: Bitmap) {
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        val image = InputImage.fromBitmap(bitmap, 0)

         recognizer.process(image)
            .addOnSuccessListener {  visionText ->
            result.setText(visionText.text.toString())
        }
            .addOnFailureListener { e->
                Toast.makeText(this, "Something went wrong...",Toast.LENGTH_LONG).show()

            }
    }
}