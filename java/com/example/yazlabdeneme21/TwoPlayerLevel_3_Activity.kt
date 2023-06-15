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
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class TwoPlayerLevel_3_Activity : AppCompatActivity() {

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

    var anliksure = 0
    var eslesmeKontrol = 0
    var lastScore_oyuncu1 = 0
    var lastScore_oyuncu2 = 0
    var oyunBittimi = 0

    var sira_kimde = 1

    var countEv = 0
    private val filename = "hard_2PL.txt"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two_player_level3)

        //süre hesabı
        val time: TextView = findViewById(R.id.suretext_hard2)
        val sayici = object : CountDownTimer(60000, 1000) {

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

    private fun onclick_card(
        namedizi: ArrayList<String>,
        puandizi: ArrayList<String>,
        bitmapimg_dizi: ArrayList<Bitmap>,
        ev_ktsayi_dizi: ArrayList<Int>,
        sayici: CountDownTimer
    ) {

        val image1: ImageView = findViewById(R.id.img1)
        val image2: ImageView = findViewById(R.id.img2)
        val image3: ImageView = findViewById(R.id.img3)
        val image4: ImageView = findViewById(R.id.img4)
        val image5: ImageView = findViewById(R.id.img5)
        val image6: ImageView = findViewById(R.id.img6)
        val image7: ImageView = findViewById(R.id.img7)
        val image8: ImageView = findViewById(R.id.img8)
        val image9: ImageView = findViewById(R.id.img9)
        val image10: ImageView = findViewById(R.id.img10)
        val image11: ImageView = findViewById(R.id.img11)
        val image12: ImageView = findViewById(R.id.img12)
        val image13: ImageView = findViewById(R.id.img13)
        val image14: ImageView = findViewById(R.id.img14)
        val image15: ImageView = findViewById(R.id.img15)
        val image16: ImageView = findViewById(R.id.img16)
        val image17: ImageView = findViewById(R.id.img17)
        val image18: ImageView = findViewById(R.id.img18)
        val image19: ImageView = findViewById(R.id.img19)
        val image20: ImageView = findViewById(R.id.img20)
        val image21: ImageView = findViewById(R.id.img21)
        val image22: ImageView = findViewById(R.id.img22)
        val image23: ImageView = findViewById(R.id.img23)
        val image24: ImageView = findViewById(R.id.img24)
        val image25: ImageView = findViewById(R.id.img25)
        val image26: ImageView = findViewById(R.id.img26)
        val image27: ImageView = findViewById(R.id.img27)
        val image28: ImageView = findViewById(R.id.img28)
        val image29: ImageView = findViewById(R.id.img29)
        val image30: ImageView = findViewById(R.id.img30)
        val image31: ImageView = findViewById(R.id.img31)
        val image32: ImageView = findViewById(R.id.img32)
        val image33: ImageView = findViewById(R.id.img33)
        val image34: ImageView = findViewById(R.id.img34)
        val image35: ImageView = findViewById(R.id.img35)
        val image36: ImageView = findViewById(R.id.img36)

        val my_img_views = arrayOf(
            image1, image2, image3, image4, image5, image6, image7, image8, image9,
            image10, image11, image12, image13, image14, image15, image16,image17,image18,
            image19,image20,image21,image22,image23,image24,image25,image26,image27,
            image28,image29,image30,image31,image32,image33,image34,image35,image36
        )
        var my_txt_views = Array<String>(36) { "" }
        val cardBack = ""
        var tiklanma_sayisi = 0
        var turnOver = false
        var son_tiklanan = -1

        myRandomArray.shuffle()
        for (k in 0..35) {
            println(myRandomArray[k])
        }


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
                } else if (my_txt_views[i] != "cardBack") {

                    if (tiklanma_sayisi==2){
                        var char1_puan = puandizi[myRandomArray[i]]
                        var char2_puan = puandizi[myRandomArray[son_tiklanan]]
                        var char1_evpuan = ev_ktsayi_dizi[myRandomArray[i]]
                        var char2_evpuan = ev_ktsayi_dizi[myRandomArray[son_tiklanan]]
                        var evadi1 = ev_dizi[myRandomArray[i]]
                        var evadi2 = ev_dizi[myRandomArray[son_tiklanan]]
                        eslesmeKontrol = 0
                        puanHesapla(eslesmeKontrol, char1_puan, char2_puan, char1_evpuan, char2_evpuan, anliksure, evadi1, evadi2, sira_kimde)
                    }

                    println("Yanlış Eşleşme")
                    //Thread.sleep(500)
                    my_img_views[i].setImageResource(R.drawable.arkafoto)
                    my_img_views[son_tiklanan].setImageResource(R.drawable.arkafoto)
                    my_txt_views[i] = "cardBack"
                    my_txt_views[son_tiklanan] = "cardBack"
                    tiklanma_sayisi = 0

                    if (sira_kimde==1){
                        println("Sıra 2.oyuncuya geçti!")
                        Toast.makeText(this,"2.oyuncunun sırası", Toast.LENGTH_SHORT).show()
                        sira_kimde=2
                    }
                    else{
                        println("Sıra 1.oyuncuya geçti!")
                        Toast.makeText(this,"1.oyuncunun sırası",Toast.LENGTH_SHORT).show()
                        sira_kimde=1
                    }
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
                        puanHesapla(eslesmeKontrol,char1_puan,char2_puan,char1_evpuan,char2_evpuan,anliksure,evadi1,evadi2,sira_kimde)

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

                            if (lastScore_oyuncu1>lastScore_oyuncu2){
                                Toast.makeText(this,"1.OYUNCU KAZANDI",Toast.LENGTH_SHORT).show()
                            }else if(lastScore_oyuncu1<lastScore_oyuncu2){
                                Toast.makeText(this,"2.OYUNCU KAZANDI",Toast.LENGTH_SHORT).show()
                            }
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
                        puanHesapla(eslesmeKontrol, char1_puan, char2_puan, char1_evpuan, char2_evpuan, anliksure, evadi1, evadi2, sira_kimde)

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

                        if (sira_kimde==1){
                            println("Sıra 2.oyuncuya geçti!")
                            Toast.makeText(this,"2.oyuncunun sırası", Toast.LENGTH_SHORT).show()
                            sira_kimde=2
                        }
                        else{
                            println("Sıra 1.oyuncuya geçti!")
                            Toast.makeText(this,"1.oyuncunun sırası",Toast.LENGTH_SHORT).show()
                            sira_kimde=1
                        }

                    }
                } else if (tiklanma_sayisi == 0) {
                    turnOver = false
                }

            }
        }

    }

    private fun puanHesapla(eslesmeKontrol: Int, char1Puan: String, char2Puan: String, char1Evpuan: Int, char2Evpuan: Int, anliksure: Int, evadi1: String, evadi2: String, sira_kimde: Int) {
        //evlerin  katsayıları
        //karakterlerin puanı

        var scoretext1: TextView = findViewById(R.id.scoretext_hard2_pl1)
        var scoretext2: TextView = findViewById(R.id.scoretext_hard2_pl2)
        var karakter_puan1 = char1Puan.toInt()
        var karakter_puan2 = char2Puan.toInt()

        var score = 0

        println("Eşleşme Puani: " + this.eslesmeKontrol)
        if (this.eslesmeKontrol ==1){
            score = 2 * karakter_puan1 * char1Evpuan
        }
        else{
            if (evadi1==evadi2){
                score = -(2 * karakter_puan1 / char1Evpuan)
            }
            else{
                var ortpuan = ((karakter_puan1 + karakter_puan2)/2).toInt()
                score = -(ortpuan * char1Evpuan * char2Evpuan)
            }

        }

        if (sira_kimde==1){
            lastScore_oyuncu1 = lastScore_oyuncu1 + score
            scoretext1.text = "Score:${lastScore_oyuncu1}"
        }
        if (sira_kimde==2){
            lastScore_oyuncu2 = lastScore_oyuncu2 + score
            scoretext2.text = "Score:${lastScore_oyuncu2}"
        }


        println("Karakter Puan1:${karakter_puan1}")
        println("Karakter Puan1:${karakter_puan2}")
        println("Anlık Skor:${score}")
        println("-------------------------------------------------")
        this.eslesmeKontrol = 0
    }

    private fun readData() {
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