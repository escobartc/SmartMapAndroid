package com.example.myapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.myapplication.R

class MainActivity2 : AppCompatActivity(R.layout.activity_main2) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<UnityFragment>(R.id.fragment_container_view)
                addToBackStack(UnityFragment::class.java.canonicalName)
            }
        }
    }
    companion object {
        @JvmStatic
        fun informacionEdificioAndroid(string: String) {
            print("El número del edificio recibido de Unity es: $string")
        }
    }
}