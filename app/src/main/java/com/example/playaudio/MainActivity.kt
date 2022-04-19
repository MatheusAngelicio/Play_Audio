package com.example.playaudio

import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.playaudio.databinding.ActivityMainBinding
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var mediaPlayer: MediaPlayer? = null

    private var audioStatus: AudioStatus = AudioStatus.STOP

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

    }

    private fun initViews() {
        binding.apply {
            btnPlay.setOnClickListener {
                if (audioStatus == AudioStatus.STOP) playAudio() else return@setOnClickListener
            }

            btnPause.setOnClickListener {
                pauseAudio()
            }
        }
    }

    private fun playAudio() {
        val audioUrl = "https://www.bensound.com/bensound-music/bensound-ukulele.mp3"

        mediaPlayer = MediaPlayer()
        mediaPlayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)

        try {
            mediaPlayer?.let { audio ->
                audio.apply {
                    setDataSource(audioUrl)
                    prepare()
                    start()
                }
            }
            audioStatus = AudioStatus.PLAY
            binding.btnPlay.isEnabled = false

        } catch (e: IOException) {
            e.printStackTrace()
        }

        Toast.makeText(this, "Audio started playing", Toast.LENGTH_SHORT).show()
    }

    private fun pauseAudio() {
        mediaPlayer?.let { audio ->
            if (audioStatus == AudioStatus.PLAY) {
                audio.apply {
                    stop()
                    reset()
                    release()
                }
                audioStatus = AudioStatus.STOP
                binding.btnPlay.isEnabled = true

            } else {
                Toast.makeText(this, "Audio has not played", Toast.LENGTH_SHORT).show()
            }
        }

    }
}

enum class AudioStatus {
    PLAY, STOP
}