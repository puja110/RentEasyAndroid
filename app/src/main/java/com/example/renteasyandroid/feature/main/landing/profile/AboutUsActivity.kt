package com.example.renteasyandroid.feature.main.landing.profile

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import com.example.renteasyandroid.R

class AboutUsActivity : AppCompatActivity() {

    // declaring a null variable for VideoView
    private var simpleVideoView: VideoView? = null

    // declaring a null variable for MediaController
    private var mediaControls: MediaController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)

        val btnBack = findViewById<ImageView>(R.id.imageArrowLeft)
        btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        simpleVideoView = findViewById<View>(R.id.videoView) as VideoView


        if (mediaControls == null) {
            // creating an object of media controller class
            mediaControls = MediaController(this)

            // set the anchor view for the video view
            mediaControls!!.setAnchorView(this.simpleVideoView)
        }

        // set the media controller for video view
        simpleVideoView!!.setMediaController(mediaControls)

        // set the absolute path of the video file which is going to be played
        simpleVideoView!!.setVideoURI(
            Uri.parse("android.resource://"
                + packageName + "/" + R.raw.renteasy))

        simpleVideoView!!.requestFocus()

        // starting the video
        simpleVideoView!!.start()


        simpleVideoView!!.setOnCompletionListener {
            Toast.makeText(applicationContext, "Video completed",
                Toast.LENGTH_LONG).show()
            true
        }

        simpleVideoView!!.setOnErrorListener { mp, what, extra ->
            Toast.makeText(applicationContext, "An Error Occurred " +
                    "While Playing Video !!!", Toast.LENGTH_LONG).show()
            false
        }
    }
}