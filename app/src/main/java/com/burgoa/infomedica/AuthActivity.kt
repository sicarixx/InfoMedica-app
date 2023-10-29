package com.burgoa.infomedica

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_InfoMedica)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        setup()
    }
    private fun setup() {
        val buttonAcceder = findViewById<Button>(R.id.buttonAcceder);
        val emailEditText = findViewById<EditText>(R.id.emailEditText);
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText);
        val textViewRegister = findViewById<TextView>(R.id.textView3);

        textViewRegister.setOnClickListener{
            val registerIntent = Intent(this, RegisterActivity::class.java)
            startActivity(registerIntent)
        }

        buttonAcceder.setOnClickListener {
            if(emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(emailEditText.text.toString(), passwordEditText.text.toString())
                    .addOnCompleteListener {
                        if (!it.isSuccessful) {
                            val messageErrorLogin = it.exception?.message
                            //val messageErrorLogin = (it.exception as FirebaseAuthException).errorCode
                            Toast.makeText(this, messageErrorLogin, Toast.LENGTH_LONG).show()

                            //errorLogin()
                        } else {
                            showHome()
                            Toast.makeText(this, getString(R.string.welcome), Toast.LENGTH_LONG).show()
                        }
                    }
            } else {
                val text = getString(R.string.toastRequired)
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(this, text, duration)
                toast.show()
            }
        }
    }

    private fun errorLogin() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(getString(R.string.errorLogin))
        builder.setPositiveButton(getString(R.string.btnAceptar), null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome() {
        val homeIntent = Intent(this, MainActivity::class.java)
        startActivity(homeIntent)
        finish()
    }
}