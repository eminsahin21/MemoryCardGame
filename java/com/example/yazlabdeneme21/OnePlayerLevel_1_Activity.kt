package com.example.yazlabdeneme21


import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.service.controls.ControlsProviderService.TAG
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.*
import android.media.MediaPlayer.create as create



class OnePlayerLevel_1_Activity : AppCompatActivity() {

    lateinit var firestore: FirebaseFirestore
    private val db = Firebase.firestore
    private val filename = "easy_1PL.txt"

    var name_dizi = Array<String>(11) { "" }
    var puan_dizi = Array<String>(11) { "" }
    var img_dizi = Array<String>(11) { "" }
    var bitmapArray = ArrayList<Bitmap>(11)

    var lastScore = 0
    var anliksure = 0
    var eslesmeKontrol = 0
    var oyunBittimi = 0

    var katsayi_ev = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_player_level1)

        val time: TextView = findViewById(R.id.textview_time)
        val sayici = object : CountDownTimer(45000, 1000) {

            override fun onTick(p0: Long) {
                anliksure = (p0 / 1000).toInt()
                if (anliksure <= 45){
                    time.text = "${p0 / 1000} sn"
                }

            }

            override fun onFinish() {
                time.text = "Bitti!"
                oyunsonu()

            }
        }
        sayici.start()

        var home_array = arrayOf("Gryffindor","Slytherin","Ravenclaw","Hufflepuff")
        var ev_random = (0..3).random()

        var i = 0
        db.collection("${home_array[ev_random]}")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var namecard = document.data["name"].toString()
                    var puancard = document.data["puan"].toString()
                    var imgcard = document.data["image"].toString()


                    name_dizi.set(i, namecard)
                    puan_dizi.set(i, puancard)
                    img_dizi.set(i, imgcard)
                    i += 1

                    if (ev_random==0){
                        katsayi_ev = 2
                    }else if (ev_random==1){
                        katsayi_ev = 2
                    }else if (ev_random==2){
                        katsayi_ev = 1
                    }else if (ev_random==3){
                        katsayi_ev = 1
                    }

                }
                onclickCard(name_dizi, puan_dizi, img_dizi,sayici)
            }.addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }


    }

    private fun onclickCard(
        name_dizi: Array<String>,
        puan_dizi: Array<String>,
        img_dizi: Array<String>,
        sayici: CountDownTimer
    ) {
        val imgview1: ImageView = findViewById(R.id.imageView_1)
        val imgview2: ImageView = findViewById(R.id.imageView_2)
        val imgview3: ImageView = findViewById(R.id.imageView_3)
        val imgview4: ImageView = findViewById(R.id.imageView_4)

        val txtview1: TextView = findViewById(R.id.cardtext_1)
        val txtview2: TextView = findViewById(R.id.cardtext_2)
        val txtview3: TextView = findViewById(R.id.cardtext_3)
        val txtview4: TextView = findViewById(R.id.cardtext_4)


        var random_num1 = (0..5).random()
        var random_num2 = (6..10).random()

        var my_array = arrayOf(random_num1, random_num2, random_num1, random_num2)
        my_array.shuffle()

//        println("Random Sayı1:${random_num1}")
//        println("Random Sayı2:${random_num2}")


        for (i in 0..10) {
            var base64String = img_dizi[i]
            var imageBytes = Base64.decode(base64String, Base64.DEFAULT)
            var decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            bitmapArray.add(decodedImage)
        }

        val my_img_views = arrayOf(imgview1, imgview2, imgview3, imgview4)
        var my_txt_views = arrayOf(txtview1, txtview2, txtview3, txtview4)
        val cardBack = ""
        var tiklanma_sayisi = 0
        var turnOver = false
        var son_tiklanan = -1


        dosyayaYaz(name_dizi,puan_dizi,my_array)

        for (i in 0..3) {
            my_img_views[i].setImageResource(R.drawable.arkafoto)
            my_txt_views[i].text = "cardBack"
            my_txt_views[i].textSize = 0.0F

            my_img_views[i].setOnClickListener {

                kartlari_kitle(my_img_views)

                if (my_txt_views[i].text == "cardBack" && !turnOver == true) {
                    my_img_views[i].setImageBitmap(bitmapArray[my_array[i]])
                    my_txt_views[i].setText(name_dizi[my_array[i]])
                    my_img_views[i].isClickable = false
                    if (tiklanma_sayisi == 0) {
                        son_tiklanan = i
                    }
                    tiklanma_sayisi++
                }

                if (tiklanma_sayisi == 2) {
                    println("2 kart açıldı")
                    turnOver = true
                    if (my_txt_views[i].text == my_txt_views[son_tiklanan].text) {
                        my_img_views[i].isClickable = false
                        my_img_views[son_tiklanan].isClickable = false
                        turnOver = false
                        tiklanma_sayisi = 0

                        val mediaplayer: MediaPlayer = create(this,R.raw.eslesmekisa)
                        mediaplayer.start()
                        mediaplayer.setVolume(20f,20f)

                        var char_puan = puan_dizi[my_array[i]]
                        eslesmeKontrol = 1
                        puanHesapla(eslesmeKontrol,char_puan,anliksure)

                        oyunBittimi++
                        if (oyunBittimi==2 && anliksure.toInt()>0){

                            mediaplayer.stop()
                            mediaplayer.release()

                            val win_music: MediaPlayer = create(this,R.raw.basarili_oyun_sonu_muzik)
                            win_music.start()

                            sayici.cancel()

                            val dialogBinding = layoutInflater.inflate(R.layout.win_dialog,null)

                            val myDialog = Dialog(this)
                            myDialog.setContentView(dialogBinding)

                            myDialog.setCancelable(true)
                            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                            myDialog.show()


                        }
                    }
                    else{
                        var char_puan = puan_dizi[my_array[i]]
                        eslesmeKontrol = 0
                        puanHesapla(eslesmeKontrol,char_puan,anliksure)

                        my_txt_views[i].setText("cardBack")
                        my_txt_views[son_tiklanan].setText("cardBack")
                        turnOver = false
                        tiklanma_sayisi = 0
                        Handler().postDelayed({
                            my_img_views[i].setImageResource(R.drawable.arkafoto)
                            my_img_views[son_tiklanan].setImageResource(R.drawable.arkafoto)
                            my_img_views[i].isClickable = true
                            my_img_views[son_tiklanan].isClickable = true
                        },600)
                    }
                } else if (tiklanma_sayisi == 0) {
                    turnOver = false
                }

            }

        }

    }

    private fun kartlari_kitle(my_img_views: Array<ImageView>) {
        if (anliksure==0){
            println("ife girdi")
            for (i in 0..3){
                my_img_views[i].isClickable = false
            }
        }
    }

    private fun puanHesapla(eslesmeKontrol: Int, anlik_char_puan: String, anliksure: Int) {
        //ev katsayısı
        //karakter puanı
        //kalan süre

        var scoretext: TextView = findViewById(R.id.scoretext)
        var karakter_puan = anlik_char_puan.toInt()
        var kalansure = anliksure.toInt()
        var score = 0

        println(eslesmeKontrol)
        if (eslesmeKontrol==1){
            score = (2 * karakter_puan * katsayi_ev) * ( anliksure / 10).toInt()
        }
        else{
            score = -(2 * karakter_puan / katsayi_ev) * ((45-kalansure) / 10).toInt()
        }

        lastScore = lastScore + score
        scoretext.text = "Score:${lastScore}"

        println("Karakter Puan:${karakter_puan}")
        println("Kalan Süre:${kalansure}")
        println("Anlık Skor:${score}")
        println("Toplam Skor:${lastScore}")
        println("-------------------------------------------------")
        this.eslesmeKontrol = 0

    }

    private fun dosyayaYaz(name_dizi: Array<String>, puan_dizi: Array<String>, my_array: Array<Int>){

        try {
            val fileOutputStream:FileOutputStream

            fileOutputStream = openFileOutput(filename, MODE_PRIVATE)

            for (i in 0..3){
                fileOutputStream.write("${i+1}-${name_dizi[my_array[i]]}/${puan_dizi[my_array[i]]}\n".toByteArray())
            }

            Toast.makeText(this,"Save file"+filesDir+"/"+filename,Toast.LENGTH_SHORT).show()
            fileOutputStream.close()
            println(filesDir)

        }catch (e:FileNotFoundException){
            e.printStackTrace();
        }catch (e:IOException){
            e.printStackTrace()
        }

    }

    private fun oyunsonu(){

        val oyunsonu_music: MediaPlayer = create(this,R.raw.basarisiz_oyun_sonu)
        oyunsonu_music.start()

    }



}