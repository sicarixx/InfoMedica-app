package com.burgoa.infomedica

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_InfoMedica)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setup()
    }

    private fun setup() {
        val buttonSearch = findViewById<Button>(R.id.buttonSearch)
        val editTextSearch = findViewById<EditText>(R.id.editTextSearch)

        buttonSearch.setOnClickListener {
            if (editTextSearch.text.isNotEmpty()) {
                Toast.makeText(this, getString(R.string.identifierToast), Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, getString(R.string.idendifierEmpty), Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.context_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.itemAbout -> aboutApp()
            R.id.itemExit -> signOut()
        }
        return true
    }
    private fun aboutApp() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.about))
        builder.setMessage("Version: 1.0\nDeveloped by: Esteban Burgoa\nTranslate by: Esteban Burgoa")
        builder.setPositiveButton(getString(R.string.btnAceptar), null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        onBackPressed()
    }
}