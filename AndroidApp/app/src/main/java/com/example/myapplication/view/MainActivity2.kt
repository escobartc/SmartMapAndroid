package com.example.myapplication.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.myapplication.R
import com.example.myapplication.ui.InfoEdificio
import com.example.myapplication.viewModel.MapViewModel
import com.unity3d.player.UnityPlayer

class MainActivity2 : AppCompatActivity(R.layout.activity_main2) {
    val viewModel: MapViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.generarEdificios()

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<UnityFragment>(R.id.fragment_container_view)
                addToBackStack(UnityFragment::class.java.canonicalName)
            }
        }

        findViewById<ComposeView>(R.id.composeViewMasInformacion).setContent {
            val edificio = viewModel.numeroEdificio.collectAsState()
            val nombre = viewModel.nombreEdificio.collectAsState()
            val descripcion = viewModel.descripcionEdificio.collectAsState()
            InfoEdificio(
                numero = edificio.value,
                nombre = nombre.value,
                descripcion = descripcion.value,
                onClick = {
                    viewModel.onClickCerrarMasInformacion()
                }
            )
        }

        viewModel.masInformacion.observe(this) {
            if (it == true) {
                findViewById<ComposeView>(R.id.composeViewMasInformacion).visibility = View.VISIBLE
            } else {
                findViewById<ComposeView>(R.id.composeViewMasInformacion).visibility =
                    View.GONE
            }
        }


        tv = this.findViewById(R.id.textView)
        datosruta = this.findViewById(R.id.calcularRuta)

        tv.doAfterTextChanged {
            viewModel.inicializarEdificio(tv.text.toString())
            viewModel.onClickMasInformacion()
        }

        datosruta.doAfterTextChanged {
            val respuesta = viewModel.procesarRespuesta(datosruta.text.toString())
            UnityPlayer.UnitySendMessage("ControladorUnityAndroid", "RecibirRutaUnity", respuesta)
        }

    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
            //additional code
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var tv: TextView
        lateinit var datosruta: TextView


        @JvmStatic
        fun informacionEdificioAndroid(string: String) {
            print("El número del edificio recibido de Unity es: $string")
            Log.d("HOLA", "El número del edificio recibido de Unity es: $string")
            tv.text = string
        }

        @JvmStatic
        fun calcularRutaAndroid(string: String) {
            Log.d("HOLA", "El recibido de Unity es: $string")
            datosruta.text = string
        }
    }
}




