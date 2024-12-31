package com.example.loginpage


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle

import android.widget.EditText
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity

import com.example.loginpage.SavedSharedPref.setUserName
import com.example.loginpage.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


class act : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var username: EditText
    lateinit var password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        getSupportActionBar()?.hide();
        setContentView(binding.root)





        binding.loginButton.setOnClickListener {
            val username = binding.username.text.toString()
            val Username = username.uppercase()
            val password = binding.password.text.toString()
            // Create object of SharedPreferences.
            // Create object of SharedPreferences.
            val sharedPref = getSharedPreferences("mypref", 0)
            //now get Editor
            //now get Editor
            val editor = sharedPref.edit()
            //put your value
            //put your value
            editor.putString("svdUser", Username)
            //commits your edits
            //commits your edits
            editor.apply()
            setUserName(this, Username)


            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    this,
                    "Username and password fields cannot be empty",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // Make API call to authenticate the user with the entered credentials
            authenticateUser(username, password)

        }

    }


    private fun authenticateUser(username: String, password: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://webprosindia.com//bvritnt//")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(API::class.java)
        val headers = HashMap<String, String>()
        headers["Authorization"] =
            "Basic cQM89mnmdFyD5jHls6HLXBAjOsjnMqSacQgJvi8S9DntdGQT7zEDT6XEx8zH+ImxseNazWUdxKzMKQz5FEVuyIkyQ2+dgeqGoD6GdagoLp3oyjeJ0qbC8B6RThYFNtRAFqO55YohjweO0B+Ng5tsGPJsQwlhOvE8"
        val call = api.getStudent(username, password, headers)



        call.enqueue(object : Callback<ResponseBody> {
            @SuppressLint("SuspiciousIndentation")
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                val responseString = response.body()?.string().toString()



                    if (response.isSuccessful&&responseString.contains("mobile") && password.contentEquals("webcap")) {
                        val i= Intent(this@act, MainActivity::class.java)
                        Toast.makeText(
                            this@act,
                            "Authentication Successful",
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(i)
                        binding.username.text.clear()
                        binding.password.text.clear()

                    } else {
                        if (responseString.contains("roll no")||password != "webcap") {


                            Toast.makeText(
                                this@act,
                                "Invalid Credentials",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        else {


                            Toast.makeText(
                                this@act,
                                "Authentication UnSuccessful",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }

            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@act, "API Call Failed", Toast.LENGTH_SHORT).show()
            }

        })
    }
}




interface API {
    @GET("api/checkstudent/{rollno}/{password}")
    fun getStudent(@Path("rollno") username: String, @Path("password") password: String, @HeaderMap headers: Map<String, String>): Call<ResponseBody>
}
