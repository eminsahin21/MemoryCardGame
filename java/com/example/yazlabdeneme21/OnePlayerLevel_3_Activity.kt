package com.example.yazlabdeneme21

import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.service.controls.ControlsProviderService
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class OnePlayerLevel_3_Activity : AppCompatActivity() {

    lateinit var firestore: FirebaseFirestore
    private val db = Firebase.firestore

    var namedizi = ArrayList<String>(44)
    var puandizi = ArrayList<String>(44)
    var imgdizi = ArrayList<String>(44)
    var evkatsayi_dizi = ArrayList<Int>(44)
    var ev_dizi = ArrayList<String>(44)
    var bitmap_img_dizi = ArrayList<Bitmap>(44)

    var random_num1 = (0..2).random()
    var random_num2 = (3..5).random()
    var random_num3 = (6..8).random()
    var random_num4 = (9..10).random()

    var random_num5 = (11..13).random()
    var random_num6 = (14..16).random()
    var random_num7 = (17..29).random()
    var random_num8 = (20..21).random()

    var random_num9 = (22..24).random()
    var random_num10 = (25..26).random()
    var random_num11 = (27..28).random()
    var random_num12 = (29..30).random()
    var random_num13 = (31..32).random()

    var random_num14 = (33..35).random()
    var random_num15 = (36..37).random()
    var random_num16 = (38..39).random()
    var random_num17 = (40..41).random()
    var random_num18 = (42..43).random()

    var myRandomArray = arrayOf(random_num1,random_num8,random_num5,random_num7,random_num3,random_num6,random_num4,random_num2,
        random_num1,random_num2,random_num3,random_num4,random_num5,random_num6,random_num7,random_num8,
        random_num9,random_num10,random_num11,random_num12,random_num18,random_num17,random_num16,random_num15,
        random_num12,random_num11,random_num18,random_num17,random_num10,random_num9,random_num13,random_num14,random_num15,
        random_num16,random_num13,random_num14
        )

    var anliksure=0
    var eslesmeKontrol = 0
    var lastScore = 0
    var oyunBittimi = 0

    var countEv = 0
    private val filename = "hard_1PL.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_player_level3)

        //süre hesabı
        val time: TextView = findViewById(R.id.suretext_hard1)
        val sayici = object : CountDownTimer(45000, 1000) {

            override fun onTick(p0: Long) {
                anliksure = (p0 / 1000).toInt()
                time.text = "${p0 / 1000} sn"
            }

            override fun onFinish() {
                time.text = "Bitti!"
                oyunsonu()

            }
        }
        sayici.start()
        //süre kodunun sonu

        readData()

        onclick_card(namedizi, puandizi, bitmap_img_dizi,evkatsayi_dizi,sayici)

    }

    private fun readData(){

        db.collection("Gryffindor")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var namecard = document.data["name"].toString()
                    var puancard = document.data["puan"].toString()
                    var imgcard = document.data["image"].toString()


                    var base64String = imgcard
                    var imageBytes = Base64.decode(base64String, Base64.DEFAULT)
                    var decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                    bitmap_img_dizi.add(decodedImage)

                    namedizi.add(namecard)
                    puandizi.add(puancard)
                    evkatsayi_dizi.add(2)
                    ev_dizi.add("G")

                    countEv++
                }
                println("Gryffindor çekti")
                dosyayaYaz(namedizi,puandizi,myRandomArray,countEv)
            }.addOnFailureListener { exception ->
                Log.d(ControlsProviderService.TAG, "Error getting documents: ", exception)
            }


        db.collection("Slytherin")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var namecard = document.data["name"].toString()
                    var puancard = document.data["puan"].toString()
                    var imgcard = document.data["image"].toString()

                    var base64String = imgcard
                    var imageBytes = Base64.decode(base64String, Base64.DEFAULT)
                    var decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                    bitmap_img_dizi.add(decodedImage)


                    namedizi.add(namecard)
                    puandizi.add(puancard)
                    evkatsayi_dizi.add(2)
                    ev_dizi.add("S")

                    countEv++

                }
                println("Slytherin çekti")
                dosyayaYaz(namedizi,puandizi,myRandomArray,countEv)
            }.addOnFailureListener { exception ->
                Log.d(ControlsProviderService.TAG, "Error getting documents: ", exception)
            }


        db.collection("Ravenclaw")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var namecard = document.data["name"].toString()
                    var puancard = document.data["puan"].toString()
                    var imgcard = document.data["image"].toString()

                    var base64String = imgcard
                    var imageBytes = Base64.decode(base64String, Base64.DEFAULT)
                    var decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                    bitmap_img_dizi.add(decodedImage)


                    namedizi.add(namecard)
                    puandizi.add(puancard)
                    evkatsayi_dizi.add(1)
                    ev_dizi.add("R")

                    countEv++
                }
                println("Ravenclawdan çekti")
                dosyayaYaz(namedizi,puandizi,myRandomArray,countEv)
            }.addOnFailureListener { exception ->
                Log.d(ControlsProviderService.TAG, "Error getting documents: ", exception)
            }


        db.collection("Hufflepuff")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var namecard = document.data["name"].toString()
                    var puancard = document.data["puan"].toString()
                    var imgcard = document.data["image"].toString()

                    var base64String = imgcard
                    var imageBytes = Base64.decode(base64String, Base64.DEFAULT)
                    var decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                    bitmap_img_dizi.add(decodedImage)

                    namedizi.add(namecard)
                    puandizi.add(puancard)
                    evkatsayi_dizi.add(1)
                    ev_dizi.add("H")

                    countEv++
                }
                println("Hufflepuff çekti")
                dosyayaYaz(namedizi,puandizi,myRandomArray,countEv)
            }.addOnFailureListener { exception ->
                Log.d(ControlsProviderService.TAG, "Error getting documents: ", exception)
            }


    }


    private fun onclick_card(
        namedizi: ArrayList<String>,
        puandizi: ArrayList<String>,
        bitmapimg_dizi: ArrayList<Bitmap>,
        ev_ktsayi_dizi: ArrayList<Int>,
        sayici: CountDownTimer
    ) {

        val image1: ImageView = findViewById(R.id.img_1)
        val image2: ImageView = findViewById(R.id.img_2)
        val image3: ImageView = findViewById(R.id.img_3)
        val image4: ImageView = findViewById(R.id.img_4)
        val image5: ImageView = findViewById(R.id.img_5)
        val image6: ImageView = findViewById(R.id.img_6)
        val image7: ImageView = findViewById(R.id.img_7)
        val image8: ImageView = findViewById(R.id.img_8)
        val image9: ImageView = findViewById(R.id.img_9)
        val image10: ImageView = findViewById(R.id.img_10)
        val image11: ImageView = findViewById(R.id.img_11)
        val image12: ImageView = findViewById(R.id.img_12)
        val image13: ImageView = findViewById(R.id.img_13)
        val image14: ImageView = findViewById(R.id.img_14)
        val image15: ImageView = findViewById(R.id.img_15)
        val image16: ImageView = findViewById(R.id.img_16)
        val image17: ImageView = findViewById(R.id.img_17)
        val image18: ImageView = findViewById(R.id.img_18)
        val image19: ImageView = findViewById(R.id.img_19)
        val image20: ImageView = findViewById(R.id.img_20)
        val image21: ImageView = findViewById(R.id.img_21)
        val image22: ImageView = findViewById(R.id.img_22)
        val image23: ImageView = findViewById(R.id.img_23)
        val image24: ImageView = findViewById(R.id.img_24)
        val image25: ImageView = findViewById(R.id.img_25)
        val image26: ImageView = findViewById(R.id.img_26)
        val image27: ImageView = findViewById(R.id.img_27)
        val image28: ImageView = findViewById(R.id.img_28)
        val image29: ImageView = findViewById(R.id.img_29)
        val image30: ImageView = findViewById(R.id.img_30)
        val image31: ImageView = findViewById(R.id.img_31)
        val image32: ImageView = findViewById(R.id.img_32)
        val image33: ImageView = findViewById(R.id.img_33)
        val image34: ImageView = findViewById(R.id.img_34)
        val image35: ImageView = findViewById(R.id.img_35)
        val image36: ImageView = findViewById(R.id.img_36)

        val my_img_views = arrayOf(
            image1, image2, image3, image4, image5, image6, image7, image8, image9,
            image10, image11, image12, image13, image14, image15, image16,image17,image18,
            image19,image20,image21,image22,image23,image24,image25,image26,image27,
            image28,image29,image30,image31,image32,image33,image34,image35,image36
        )
        var my_txt_views = Array<String>(36) { "" }
        var tiklanma_sayisi = 0
        var turnOver = false
        var son_tiklanan = -1

        myRandomArray.shuffle()
//        for (k in 0..35) {
//            println(myRandomArray[k])
//        }


        for (i in 0..35) {
            my_img_views[i].setImageResource(R.drawable.arkafoto)
            my_txt_views[i] = "cardBack"

            my_img_views[i].setOnClickListener {

                kartlari_kitle(my_img_views)

                if (my_txt_views[i] == "cardBack" && !turnOver == true) {
                    my_img_views[i].setImageBitmap(bitmapimg_dizi[myRandomArray[i]])
                    my_txt_views[i] = namedizi[myRandomArray[i]]
                    my_img_views[i].isClickable = false

                    println(namedizi[myRandomArray[i]])
                    println(ev_ktsayi_dizi[myRandomArray[i]])


                    if (tiklanma_sayisi == 0) {
                        son_tiklanan = i
                    }
                    tiklanma_sayisi++
                }
                if (tiklanma_sayisi == 2) {
                    println("2 kart açıldı")
                    turnOver = true
                    if (my_txt_views[i] == my_txt_views[son_tiklanan]) {
                        my_img_views[i].isClickable = false
                        my_img_views[son_tiklanan].isClickable = false
                        turnOver = false
                        tiklanma_sayisi = 0


                        val mediaplayer: MediaPlayer = MediaPlayer.create(this, R.raw.eslesmekisa)
                        mediaplayer.start()
                        mediaplayer.setVolume(20f,20f)

                        var char1_puan = puandizi[myRandomArray[i]]
                        var char2_puan = puandizi[myRandomArray[son_tiklanan]]
                        var char1_evpuan = ev_ktsayi_dizi[myRandomArray[i]]
                        var char2_evpuan = ev_ktsayi_dizi[myRandomArray[son_tiklanan]]
                        var evadi1 = ev_dizi[myRandomArray[i]]
                        var evadi2 = ev_dizi[myRandomArray[son_tiklanan]]
                        eslesmeKontrol = 1
                        puanHesapla(eslesmeKontrol, char1_puan, char2_puan, char1_evpuan, char2_evpuan, anliksure, evadi1, evadi2)


                        oyunBittimi++
                        if (oyunBittimi==18 && anliksure.toInt()>0){

                            mediaplayer.stop()
                            mediaplayer.release()

                            val win_music: MediaPlayer = MediaPlayer.create(this, R.raw.basarili_oyun_sonu_muzik)
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
                        var char1_puan = puandizi[myRandomArray[i]]
                        var char2_puan = puandizi[myRandomArray[son_tiklanan]]
                        var char1_evpuan = ev_ktsayi_dizi[myRandomArray[i]]
                        var char2_evpuan = ev_ktsayi_dizi[myRandomArray[son_tiklanan]]
                        var evadi1 = ev_dizi[myRandomArray[i]]
                        var evadi2 = ev_dizi[myRandomArray[son_tiklanan]]
                        eslesmeKontrol = 0
                        puanHesapla(eslesmeKontrol, char1_puan, char2_puan, char1_evpuan, char2_evpuan, anliksure, evadi1, evadi2)

                        my_txt_views[i] = "cardBack"
                        my_txt_views[son_tiklanan] = "cardBack"
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

    private fun puanHesapla(
        eslesmeKontrol: Int,
        char1_puan: String,
        char2_puan: String,
        char1_evpuan: Int,
        char2_evpuan: Int,
        anliksure: Int,
        evadi1: String,
        evadi2: String
    ) {
        //evlerin  katsayıları
        //karakterlerin puanı
        //kalan süre

        var scoretext: TextView = findViewById(R.id.scoretext_hard1_pl1)
        var karakter_puan1 = char1_puan.toInt()
        var karakter_puan2 = char2_puan.toInt()

        var kalansure = this.anliksure.toInt()
        var score = 0

        println("Eşleşme Puani: " + this.eslesmeKontrol)
        if (this.eslesmeKontrol ==1){
            score = (2 * karakter_puan1 * char1_evpuan) * ( this.anliksure / 10).toInt()
        }
        else{
            if (evadi1==evadi2){
                score = -((2 * karakter_puan1 / char1_evpuan) * ((45-kalansure) / 10).toInt())
            }
            else{
                var ortpuan = ((karakter_puan1 + karakter_puan2)/2).toInt()
                score = -((ortpuan * char1_evpuan * char2_evpuan) * ((45-kalansure) / 10))
            }

        }

        lastScore = lastScore + score
        scoretext.text = "Score:${lastScore}"

        println("Karakter Puan1:${karakter_puan1}")
        println("Karakter Puan2:${karakter_puan2}")
        println("Ev Puan1:${char1_evpuan}")
        println("Ev Puan2:${char2_evpuan}")
        println("Kalan Süre:${kalansure}")
        println("Anlık Skor:${score}")
        println("Toplam Skor:${lastScore}")
        println("-------------------------------------------------")
        this.eslesmeKontrol = 0

    }

    private fun oyunsonu(){

        val oyunsonu_music: MediaPlayer = MediaPlayer.create(this, R.raw.basarisiz_oyun_sonu)
        oyunsonu_music.start()

    }

    private fun kartlari_kitle(my_img_views: Array<ImageView>) {
        if (anliksure==0){
            println("ife girdi")
            for (i in 0..35){
                my_img_views[i].isClickable = false
            }
        }
    }

    private fun dosyayaYaz(
        name_dizi: ArrayList<String>,
        puan_dizi: ArrayList<String>,
        my_array: Array<Int>,
        countEv: Int
    ){

        if (countEv==44){
            try {
                val fileOutputStream: FileOutputStream

                fileOutputStream = openFileOutput(filename, MODE_PRIVATE)

                for (i in 0..35){
                    println("${i+1}-${name_dizi[my_array[i]]}/${puan_dizi[my_array[i]]}\n")
                    fileOutputStream.write("${i+1}-${name_dizi[my_array[i]]}/${puan_dizi[my_array[i]]}\n".toByteArray())
                }
                println(filesDir)

            }catch (e: FileNotFoundException){
                e.printStackTrace();
            }catch (e: IOException){
                e.printStackTrace()
            }
        }


    }




}