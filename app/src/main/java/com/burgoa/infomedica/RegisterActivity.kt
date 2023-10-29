package com.burgoa.infomedica

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.FirebaseError.ERROR_EMAIL_ALREADY_IN_USE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setup()
    }

    private fun setup() {
        val emailEditTextRegister = findViewById<EditText>(R.id.emailEditTextRegister)
        val passwordEditTextRegister = findViewById<EditText>(R.id.passwordEditTextRegister)
        val passwordEditTextRegisterRepeat = findViewById<EditText>(R.id.passwordEditTextRegisterRepeat)
        val buttonRegister = findViewById<Button>(R.id.buttonRegister)

        buttonRegister.setOnClickListener {
            if (emailEditTextRegister.text.isNotEmpty() && passwordEditTextRegister.text.isNotEmpty() && passwordEditTextRegisterRepeat.text.isNotEmpty()) {
                if (passwordEditTextRegister.text.toString().length >= 6) {
                    if (passwordEditTextRegister.text.toString() == passwordEditTextRegisterRepeat.text.toString()) {
                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailEditTextRegister.text.toString(), passwordEditTextRegister.text.toString())
                            .addOnCompleteListener{
                                if (!it.isSuccessful){
                                    val messageErrorSignup = (it.exception as FirebaseAuthException).errorCode
                                    if (messageErrorSignup === "ERROR_EMAIL_ALREADY_IN_USE") {
                                        errorRegister()
                                    } else {
                                        Toast.makeText(this, getString(R.string.errorRegister), Toast.LENGTH_LONG).show()
                                    }
                                } else {
                                    Toast.makeText(this, getString(R.string.postRegister), Toast.LENGTH_LONG).show()
                                    loginShow()
                                }
                            }
                    } else {
                        Toast.makeText(this, getString(R.string.errorPassword), Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this, getString(R.string.lengthPassword), Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, getString(R.string.toastRequired), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun loginShow() {
        val loginIntent = Intent(this, AuthActivity::class.java)
        startActivity(loginIntent)
    }

    private fun errorRegister() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(getString(R.string.emailAlreadyInUse))
        builder.setPositiveButton(getString(R.string.btnAceptar), null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}