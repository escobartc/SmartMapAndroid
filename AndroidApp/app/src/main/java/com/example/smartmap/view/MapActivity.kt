package com.example.smartmap.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.smartmap.R
import com.example.smartmap.ui.DetailTopAppBar
import com.example.smartmap.ui.InfoEdificio
import com.example.smartmap.viewModel.MapViewModel
import com.google.firebase.auth.FirebaseAuth
import com.unity3d.player.UnityPlayer

class MapActivity : AppCompatActivity(R.layout.activity_map) {
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
            val urlImagen = viewModel.urlImagenEdificio.collectAsState()
            InfoEdificio(
                numero = edificio.value,
                nombre = nombre.value,
                descripcion = descripcion.value,
                onClick = {
                    viewModel.onClickCerrarMasInformacion()
                },
                imagen = urlImagen.value
            )
        }
        findViewById<ComposeView>(R.id.composeViewTopBar).setContent {
            val ed = viewModel.edificioABuscar.collectAsState()
            DetailTopAppBar(
                value = ed.value,
                onClickBuscar = { viewModel.onClickBuscar() },
                onTextChange = { viewModel.onEdTextChange(it) },
                onClickCs = { cerrarSesion() })
            viewModel.buscarEdificio.observe(this) {
                if (it == true) {
                    UnityPlayer.UnitySendMessage(
                        "ControladorUnityAndroid", "RecibirEdificioAMostrar",
                        ed.value
                    )
                    viewModel.finalizarBusqueda()
                }
            }
        }

        viewModel.masInformacion.observe(this) {
            if (it == true) {
                findViewById<ComposeView>(R.id.composeViewMasInformacion).visibility = View.VISIBLE
                findViewById<ComposeView>(R.id.composeViewTopBar).visibility = View.GONE

            } else {
                findViewById<ComposeView>(R.id.composeViewMasInformacion).visibility =
                    View.GONE
                findViewById<ComposeView>(R.id.composeViewTopBar).visibility = View.VISIBLE
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

    private fun cerrarSesion() {
        FirebaseAuth.getInstance().signOut()
        onBackPressed()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
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




