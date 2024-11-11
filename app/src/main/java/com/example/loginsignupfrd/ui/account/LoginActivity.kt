package com.example.loginsignupfrd.ui.account

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.loginsignupfrd.MainActivity
import com.example.loginsignupfrd.databinding.ActivityLoginBinding
import com.example.loginsignupfrd.model.UserData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("users")

        binding.loginButton.setOnClickListener{
            val loginUsername = binding.loginUsername.text.toString()
            val loginPassword = binding.loginPassword.text.toString()

            if (loginUsername.isNotEmpty()&& loginPassword.isNotEmpty()){
                if (loginUsername.length < 8 || loginPassword.length < 8 ){
                    Toast.makeText(this@LoginActivity, "Username and Password must be at least 8 characters", Toast.LENGTH_SHORT).show()
                }else{
                    loginUser(loginUsername,loginPassword)
                }
            }
            else{
                Toast.makeText(this@LoginActivity, "All field are mandatory", Toast.LENGTH_SHORT).show()

            }


        }
            binding.signupRedirect.setOnClickListener{
                startActivity(Intent(this@LoginActivity, SignupActivity::class.java ))
                finish()

            }


        }

    private fun loginUser(username:String, password:String){
        databaseReference.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot:DataSnapshot ) {
                if (dataSnapshot.exists()) {
                    for (userSnapshot in dataSnapshot.children) {
                        val userData = userSnapshot.getValue(UserData::class.java)

                        if (userData != null && userData.password == password) {
                            val hiddenPassword = password.replaceRange(0, password.length, "*".repeat(password.length))
                            Toast.makeText(this@LoginActivity, "Login succesful ", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java ))
                            finish()
                            return


                        }
                    }
                }
                Toast.makeText(this@LoginActivity, "Login failed ", Toast.LENGTH_SHORT).show()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@LoginActivity, "Database error :${databaseError.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    }

