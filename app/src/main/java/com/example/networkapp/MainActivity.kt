package com.example.networkapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream

// TODO (1: Fix any bugs)
// TODO (2: Add function saveComic(...) to save and load comic info automatically when app starts)

class MainActivity : AppCompatActivity() {

    private lateinit var requestQueue: RequestQueue
    lateinit var titleTextView: TextView
    lateinit var descriptionTextView: TextView
    lateinit var numberEditText: EditText
    lateinit var showButton: Button
    lateinit var comicImageView: ImageView
    private lateinit var file: File
    private val internalFilename = "my_file"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        file = File(filesDir, internalFilename)

        requestQueue = Volley.newRequestQueue(this)

        titleTextView = findViewById<TextView>(R.id.comicTitleTextView)
        descriptionTextView = findViewById<TextView>(R.id.comicDescriptionTextView)
        numberEditText = findViewById<EditText>(R.id.comicNumberEditText)
        showButton = findViewById<Button>(R.id.showComicButton)
        comicImageView = findViewById<ImageView>(R.id.comicImageView)

        showButton.setOnClickListener {
            val input = numberEditText.text.toString()
            if (input.toIntOrNull() != null) {
                downloadComic(input)
                saveComic()
            } else {
                val toastText = "Enter valid Comic (int)"
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(this, toastText, duration)
                toast.show()
            }
        }

    }

    private fun downloadComic(comicId: String) {
        val url = "https://xkcd.com/$comicId/info.0.json"
        val comicName = "$comicId"
        var comicFile = File(filesDir, comicName)
        if (comicFile.exists()) {

        }

        else {

            requestQueue.add(
                JsonObjectRequest(url, { showComic(it) }, {
                }))
            }
        }

    private fun showComic(comicObject: JSONObject) {
        titleTextView.text = comicObject.getString("title")
        descriptionTextView.text = comicObject.getString("alt")
        Picasso.get().load(comicObject.getString("img")).into(comicImageView)
    }

    private fun saveComic() {
        val comicId = comicObject.getString("num")
        val comicName = "$comicId"
        var comicFile = File(filesDir, comicName)
        val outputStream = FileOutputStream(comicFile)
        outputStream.write(comicObject.toString().toByteArray())
        outputStream.close()
    }
}