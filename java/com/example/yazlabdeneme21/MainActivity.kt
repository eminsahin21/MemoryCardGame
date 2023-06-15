package com.example.yazlabdeneme21

import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.database.collection.LLRBNode

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button_tekoyuncu: Button = findViewById(R.id.button_oneplayer)
        val button_ikioyuncu: Button = findViewById(R.id.button_twoplayer)

        val easybutton: Button =findViewById(R.id.button_easy)
        val mediumbutton: Button =findViewById(R.id.button_medium)
        val hardbutton: Button = findViewById(R.id.button_hard)

        val start_button: Button = findViewById(R.id.button_start)

        var person_count=0
        var level=0


        // ses açma kapatma kodları
        val mediaplayer: MediaPlayer = MediaPlayer.create(this,R.raw.mainmusic)
        val musicbutton: ImageButton = findViewById(R.id.musicplayButton)
        mediaplayer.start()

        musicbutton.setOnClickListener {
            if (!mediaplayer.isPlaying){
                mediaplayer.start()
                musicbutton.setImageResource(R.drawable.ic_baseline_volume_up_24)
            }
            else{
                mediaplayer.pause()
                musicbutton.setImageResource(R.drawable.ic_baseline_volume_off_24)
            }
        }
        //ses açma kapatma kodları bitti




        button_tekoyuncu.setOnClickListener {
            button_tekoyuncu.setBackgroundColor(Color.GREEN)
            button_ikioyuncu.setBackgroundColor(Color.RED)
            person_count=1

        }

        button_ikioyuncu.setOnClickListener {
            button_tekoyuncu.setBackgroundColor(Color.RED)
            button_ikioyuncu.setBackgroundColor(Color.GREEN)
            person_count=2
        }

        easybutton.setOnClickListener {
            easybutton.setBackgroundColor(Color.GREEN)
            mediumbutton.setBackgroundColor(Color.RED)
            hardbutton.setBackgroundColor(Color.RED)
            level=1

        }
        mediumbutton.setOnClickListener {
            easybutton.setBackgroundColor(Color.RED)
            mediumbutton.setBackgroundColor(Color.GREEN)
            hardbutton.setBackgroundColor(Color.RED)
            level=2

        }
        hardbutton.setOnClickListener {
            easybutton.setBackgroundColor(Color.RED)
            mediumbutton.setBackgroundColor(Color.RED)
            hardbutton.setBackgroundColor(Color.GREEN)
            level=3
        }

        start_button.setOnClickListener {

            if (person_count==1)
            {
                if (level==1)
                {
                    val intent = Intent(this,OnePlayerLevel_1_Activity::class.java)
                    startActivity(intent)
                }
                else if (level==2)
                {
                    val intent = Intent(this,OnePlayerLevel_2_Activity::class.java)
                    startActivity(intent)
                }
                else if (level==3)
                {
                    val intent = Intent(this,OnePlayerLevel_3_Activity::class.java)
                    startActivity(intent)
                }
            }
            else if (person_count==2)
            {
                if (level==1)
                {
                    val intent = Intent(this,TwoPlayerLevel_1_Activity::class.java)
                    startActivity(intent)
                }
                else if (level==2)
                {
                    val intent = Intent(this,TwoPlayerLevel_2_Activity::class.java)
                    startActivity(intent)
                }
                else if (level==3)
                {
                    val intent = Intent(this,TwoPlayerLevel_3_Activity::class.java)
                    startActivity(intent)
                }
            }
        }

    }

}