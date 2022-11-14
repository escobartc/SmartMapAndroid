package com.example.smartmap.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.smartmap.R
import com.example.smartmap.ui.*
import com.example.smartmap.ui.DetailTopAppBar
import com.example.smartmap.viewModel.MapViewModel
import com.google.firebase.auth.FirebaseAuth
import com.unity3d.player.UnityPlayer


class MapActivity : AppCompatActivity(R.layout.activity_map) {
    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 2
    val viewModel: MapViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)// Show status bar
        viewModel.traerEdificios()
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<UnityFragment>(R.id.fragment_container_view)
                addToBackStack(UnityFragment::class.java.canonicalName)
            }
        }
        getLocation()
        findViewById<ComposeView>(R.id.composeViewMasInformacion).setContent {
            val edificio = viewModel.numeroEdificio.collectAsState()
            val nombre = viewModel.nombreEdificio.collectAsState()
            val descripcion = viewModel.descripcionEdificio.collectAsState()
            val urlImagen = viewModel.urlImagenEdificio.collectAsState()
            Log.d("HOLA", "Usuario: ${FirebaseAuth.getInstance().currentUser?.zzb()?.zzf()}")

            val esInvitado = viewModel.esInvitado.collectAsState()
            val espacios = viewModel.getEspacios(edificio.value)
            InfoEdificio(
                numero = edificio.value,
                nombre = nombre.value,
                descripcion = descripcion.value,
                onClick = {
                    viewModel.onClickCerrarMasInformacion()
                },
                imagen = urlImagen.value,
                esInvitado = esInvitado.value,
                espacios = espacios,
                onClickAforo = { viewModel.onClickAforo() }
            )
        }

        findViewById<ComposeView>(R.id.composeViewTopBar).setContent {

            val ed = viewModel.edificioABuscar.collectAsState()
            DetailTopAppBar(
                value = ed.value,
                onClickBuscar = { viewModel.onClickBuscar() },
                onTextChange = { viewModel.onEdTextChange(it) },
                onClickClean = { viewModel.onClickCleanText() })
            viewModel.buscarEdificio.observe(this) {
                if (it == true) {
                    val busqueda = viewModel.buscarEdificioPorNombre(ed.value)
                    if (busqueda.isNotEmpty()) {
                        UnityPlayer.UnitySendMessage(
                            "ControladorUnityAndroid", "RecibirEdificioAMostrar",
                            busqueda
                        )
                    } else {
                        Toast.makeText(
                            this,
                            "Ningún edificio cumple con el criterio de búsqueda",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    viewModel.finalizarBusqueda()
                }
            }
        }

        findViewById<ComposeView>(R.id.composeViewNavBar).setContent {
            BottomBar(
                onClickCs = { cerrarSesion() },
                onClickBuscar = { viewModel.onClickAbrirBarraBusqueda() }) { centrarCamara() }
        }

        viewModel.mostrarBarraBusqueda.observe(this) {
            if (it == true) {
                findViewById<ComposeView>(R.id.composeViewTopBar).visibility = View.VISIBLE

            } else {
                findViewById<ComposeView>(R.id.composeViewTopBar).visibility = View.GONE
            }
        }

        viewModel.masInformacion.observe(this) {
            if (it == true) {
                findViewById<ComposeView>(R.id.composeViewMasInformacion).visibility = View.VISIBLE
                findViewById<ComposeView>(R.id.composeViewTopBar).visibility = View.GONE
                findViewById<ComposeView>(R.id.composeViewNavBar).visibility = View.GONE


            } else {
                findViewById<ComposeView>(R.id.composeViewMasInformacion).visibility =
                    View.GONE
                findViewById<ComposeView>(R.id.composeViewNavBar).visibility = View.VISIBLE
            }
        }

        viewModel.verAforo.observe(this) {
            if (it == true) {
                findViewById<ComposeView>(R.id.composeViewAforo).setContent {
                    val data = viewModel.dataAforo.collectAsState()
                    val edificio = viewModel.nombreEdificio.collectAsState()
                    val numero = viewModel.numeroEdificio.collectAsState()
                    ChartScreen(data = data.value, onClick = { viewModel.onClickCerrarAforo() },numero = numero.value, edificio = edificio.value)
                }
                findViewById<ComposeView>(R.id.composeViewAforo).visibility = View.VISIBLE

            } else {
                findViewById<ComposeView>(R.id.composeViewAforo).setContent {
                    val data = viewModel.dataAforo.collectAsState()
                    val edificio = viewModel.nombreEdificio.collectAsState()
                    val numero = viewModel.numeroEdificio.collectAsState()
                    ChartScreen(data = data.value, onClick = { viewModel.onClickCerrarAforo() },numero = numero.value, edificio = edificio.value)
                }
                findViewById<ComposeView>(R.id.composeViewAforo).visibility = View.GONE
            }
        }

        localizacion = this.findViewById(R.id.localizacion)
        tv = this.findViewById(R.id.textView)
        datosruta = this.findViewById(R.id.calcularRuta)
        datosRutaMejorada = this.findViewById(R.id.calcularRutaMejorada)

        tv.doAfterTextChanged {
            viewModel.inicializarEdificio(tv.text.toString())
            viewModel.onClickMasInformacion()
        }

        datosruta.doAfterTextChanged {
            val respuesta = viewModel.procesarRespuesta(datosruta.text.toString())
            UnityPlayer.UnitySendMessage("ControladorUnityAndroid", "RecibirRutaUnity", respuesta)
        }

        localizacion.doAfterTextChanged {
            getLocation()
        }

        datosRutaMejorada.doAfterTextChanged {
            viewModel.actualizarPosicion(datosRutaMejorada.text.toString())
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
        //exitProcess(-1)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                locationPermissionCode
            )
        } else {
            val lon = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)?.longitude
            val lat = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)?.latitude
            Log.d("HOLA", "Latitud Android es: $lat y la longitud es: $lon")
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    this,
                    "Permisos denegados, debe otorgarlos para poder hacer uso de la aplicación.",
                    Toast.LENGTH_SHORT
                ).show()
                //getLocation()
            }
        }
    }

    fun centrarCamara() {
        UnityPlayer.UnitySendMessage("ControladorUnityAndroid", "centrarCamara", "")
    }


    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var tv: TextView

        @SuppressLint("StaticFieldLeak")
        lateinit var datosruta: TextView

        @SuppressLint("StaticFieldLeak")
        lateinit var localizacion: TextView

        @SuppressLint("StaticFieldLeak")
        lateinit var datosRutaMejorada: TextView

        @JvmStatic
        fun recibirPosicionActual(string: String) {
            print("La localización recibida de Unity es: $string")
            Log.d("HOLA", "La localización recibida de Unity es: $string")
            localizacion.text = string
        }

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

        @JvmStatic
        fun actualizarPosicionAndroid(string: String) {
            Log.d("HOLA", "Lo que debo mandar al backend es: $string")
            datosRutaMejorada.text = string
        }
    }
}




