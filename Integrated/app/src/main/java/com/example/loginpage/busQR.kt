package com.example.loginpage

import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.common.base.Utf8
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Path
import java.nio.charset.StandardCharsets

class busQR : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus_qr)
        scanCode()
    }

    private fun scanCode() {
        val options = ScanOptions()
        options.setPrompt("volume up to flash on")
        options.setBeepEnabled(true)
        options.setOrientationLocked(true)
        options.captureActivity = CapturAct::class.java
        barLauncher.launch(options)
    }

    var barLauncher = registerForActivityResult(ScanContract()) { result: ScanIntentResult ->
        //To on successful scan satisfy condition
        val username = intent.getStringExtra("username").toString()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://webprosindia.com//bvritnt//")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        val header = HashMap<String, String>()
        header["Authorization"] =
            "Basic cQM89mnmdFyD5jHls6HLXBAjOsjnMqSacQgJvi8S9DntdGQT7zEDT6XEx8zH+ImxseNazWUdxKzMKQz5FEVuyIkyQ2+dgeqGoD6GdagoLp3oyjeJ0qbC8B6RThYFNtRAFqO55YohjweO0B+Ng5tsGPJsQwlhOvE8"
        val decodedBytes = Base64.decode(result.contents, Base64.DEFAULT)
        val decodedString = String(decodedBytes)
        val call=apiService.makePostRequest(username,decodedString,header)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                    if (response.isSuccessful&&result.contents!=null) {
                        // Handle the successful response here
                        val intent = Intent(this@busQR, MainActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(this@busQR, "Attendance Marked", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        // Handle the error response here
                        val intent = Intent(this@busQR, MainActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(this@busQR, "Try Again", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    // Handle the failure here
                    Toast.makeText(this@busQR, "API Call Failed", Toast.LENGTH_SHORT).show()

                }
            })

    }
}
interface ApiService{
    @GET("api/savestudentvehicle/{rollno}/{vehicleno}")
    fun makePostRequest(@Path("rollno") username: String,@Path("vehicleno") vehicleno: String,@HeaderMap header: Map<String, String>): Call<ResponseBody>

}