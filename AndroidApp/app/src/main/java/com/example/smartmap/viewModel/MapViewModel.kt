package com.example.smartmap.viewModel


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.smartmap.model.*
import com.example.smartmap.network.RestEngine
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import java.text.Normalizer

class MapViewModel : ViewModel() {
    private val _masInformacion = MutableLiveData<Boolean>()
    val masInformacion = _masInformacion

    private val _verAforo = MutableLiveData<Boolean>()
    val verAforo = _verAforo

    private val _mostrarBarraBusqueda = MutableLiveData<Boolean>()
    val mostrarBarraBusqueda = _mostrarBarraBusqueda

    private val _buscarEdificio = MutableLiveData<Boolean>()
    val buscarEdificio = _buscarEdificio

    private val _numeroEdificio = MutableStateFlow("")
    val numeroEdificio = _numeroEdificio

    private val _nombreEdificio = MutableStateFlow("")
    val nombreEdificio = _nombreEdificio

    private val _edificioABuscar = MutableStateFlow("")
    val edificioABuscar = _edificioABuscar

    private val _urlImagenEdificio = MutableStateFlow("")
    val urlImagenEdificio = _urlImagenEdificio

    private val _edificios = mutableListOf<EdificioDataCollectionItem>()
    private val _edificiosDB = mutableListOf<Edificio>()

    private val _descripcionEdificio = MutableStateFlow("")
    val descripcionEdificio = _descripcionEdificio

    private val _datosCalcularRuta = MutableStateFlow("")

    private val _rutaMejorada = MutableStateFlow(Ruta(String()))

    private val _dataAforo = MutableStateFlow(mutableListOf<Pair<Int, Double>>())
    val dataAforo = _dataAforo

    val esInvitado = MutableStateFlow(FirebaseAuth.getInstance().currentUser == null)
    val nombresEdificios = mutableListOf<EdBusqueda>()

    fun inicializarEdificio(numero: String) {
        _numeroEdificio.value = numero
        CoroutineScope(Dispatchers.IO).launch {
            _nombreEdificio.value =
                EdificioApp().room.edificioDAO().getNombreByNumero(numero.toInt())
            _descripcionEdificio.value =
                EdificioApp().room.edificioDAO().getDescripcionByNumero(numero.toInt())
            _urlImagenEdificio.value =
                EdificioApp().room.edificioDAO().getImagenByNumero(numero.toInt())
        }
    }

    fun traerEdificios() {
        if (esInvitado.value) {
            val uid = "-1"
            cargarEdificios(uid)
        } else {
            val uid = FirebaseAuth.getInstance().currentUser!!.uid
            cargarEdificios(uid)
        }
    }

    fun cargarEdificios(uid: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = RestEngine.apiService.getEdificio(uid)
            if (call.isSuccessful) {
                _edificios.addAll(call.body() ?: emptyList())
                guardarEdificios()
                guardarNombresDeEdificios()
            }
        }
    }

    fun calcularRutaMejorada(nodo: String, edificio: String) {
        runBlocking {
            val job: Job = launch(context = Dispatchers.IO) {
                val call = RestEngine.apiService.getRutaMejorada(nodo, edificio)
                if (call.isSuccessful) {
                    _rutaMejorada.value = call.body() ?: Ruta(String())
                    Log.d("HOLA", _rutaMejorada.value.ruta)
                } else {
                    _rutaMejorada.value = Ruta(String())
                }
            }
            job.join()
        }
    }

    private fun guardarEdificios() {
        CoroutineScope(Dispatchers.IO).launch {
            _edificios.map {
                _edificiosDB.add(
                    Edificio(
                        aforoActual = it.aforoActual ?: 0,
                        descripcion = it.descripcion,
                        id_edificio = it.id_edificio,
                        imagen = it.imagen ?: "",
                        listaDeEntradas = it.listaDeEntradas ?: "",
                        nombre = it.nombre,
                        palabras_clave = it.palabras_clave ?: "",
                    )
                )
            }
            EdificioApp().room.edificioDAO().insert(_edificiosDB)
        }
    }

    fun onClickAbrirBarraBusqueda() {
        if (_mostrarBarraBusqueda.value == true) {
            _mostrarBarraBusqueda.postValue(false)
        } else {
            _mostrarBarraBusqueda.postValue(true)
        }
    }

    fun onClickMasInformacion() {
        _masInformacion.postValue(true)
        CoroutineScope(Dispatchers.IO).launch {
            val call = RestEngine.apiService.getAforo(_numeroEdificio.value)
            if (call.isSuccessful) {
                _dataAforo.value.clear()
                val aforos = mutableListOf<Int>()
                aforos.addAll(call.body() ?: emptyList())
                val horas = mutableListOf(7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19)
                _dataAforo.value.addAll(
                    listOf(
                        Pair(horas[0], aforos[0].toDouble()),
                        Pair(horas[1], aforos[1].toDouble()),
                        Pair(horas[2], aforos[2].toDouble()),
                        Pair(horas[3], aforos[3].toDouble()),
                        Pair(horas[4], aforos[4].toDouble()),
                        Pair(horas[5], aforos[5].toDouble()),
                        Pair(horas[6], aforos[6].toDouble()),
                        Pair(horas[7], aforos[7].toDouble()),
                        Pair(horas[8], aforos[8].toDouble()),
                        Pair(horas[9], aforos[9].toDouble()),
                        Pair(horas[10], aforos[10].toDouble()),
                        Pair(horas[11], aforos[11].toDouble()),
                        Pair(horas[12], aforos[12].toDouble())
                    )
                )
                Log.d("HOLA", "Aforo: ${call.body()}")
            }
        }
    }

    fun onClickBuscar() {
        _buscarEdificio.postValue(true)
        if (!esInvitado.value) {
            if (_edificioABuscar.value.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    val uid = FirebaseAuth.getInstance().currentUser!!.uid
                    val call =
                        RestEngine.apiService.guardarBusquedaDelUsuario(uid, _edificioABuscar.value)
                    if (call.isSuccessful) {
                        Log.d("HOLA", "SE ACTUALIZÓ EL HISTORIAL")
                    }
                }
            }
        }
    }

    fun finalizarBusqueda() {
        _buscarEdificio.postValue(false)
    }

    fun onClickCerrarMasInformacion() {
        _masInformacion.postValue(false)
    }

    fun onEdTextChange(texto: String) {
        _edificioABuscar.value = texto
    }

    fun onClickCleanText() {
        _edificioABuscar.value = String()
    }

    fun procesarRespuesta(string: String): String {
        val nodo = string.substringBefore("-")
        val edificio = string.substringAfter("-")
        return if (esInvitado.value) {
            _datosCalcularRuta.value = "Basico-$nodo-ED-$edificio"
            "Basico-$nodo-ED-$edificio"
        } else {
            calcularRutaMejorada(nodo = nodo, edificio = edificio)
            if (_rutaMejorada.value.ruta.isNotEmpty()) {
                "Completo-${_rutaMejorada.value.ruta}"
            } else {
                "Basico-$nodo-ED-$edificio"
            }
        }
    }

    fun guardarNombresDeEdificios() {
        CoroutineScope(Dispatchers.IO).launch {
            _edificios.forEach {
                nombresEdificios.add(
                    EdBusqueda(
                        numero = it.id_edificio.toString(),
                        nombre = it.nombre.lowercase().unaccent(),
                        alias = if (!it.palabras_clave.isNullOrBlank()) {
                            it.palabras_clave.lowercase().unaccent()
                        } else {
                            ""
                        }
                    )
                )
            }
        }
    }

    fun buscarEdificioPorNombre(texto: String): String {
        val nombreEd = texto.lowercase().unaccent()

        nombresEdificios.forEach { edificio ->
            if (edificio.nombre == nombreEd) {
                return edificio.numero
            }
            if (
                edificio.nombre.contains(nombreEd)
            ) {
                return edificio.numero
            }
            if (edificio.numero == texto) {
                return edificio.numero
            }
            if (edificio.alias.contains(nombreEd)) {
                return edificio.numero
            }
        }
        return String()
    }

    fun getEspacios(edificio: String): List<Espacio> {
        _edificios.forEach { numero ->
            if (edificio == numero.id_edificio.toString()) {
                return numero.espacios ?: emptyList()
            }
        }
        return emptyList()
    }

    fun actualizarPosicion(nodos: String) {
        val nodoActual = nodos.substringAfter(",").substringAfter("(").substringBefore(")")
        val nodoAnterior = nodos.substringBefore(",").substringAfter("(").substringBefore(")")
        if (!esInvitado.value) {
            CoroutineScope(Dispatchers.IO).launch {
                val uid = FirebaseAuth.getInstance().currentUser!!.uid
                if (nodoAnterior == "persona") {
                    val call = RestEngine.apiService.actualizarUbicacion(uid, "-1", nodoActual)
                    if (call.isSuccessful) {
                        Log.d("HOLA", "SE ACTUALIZÓ LA UBICACIÓN")
                    }
                } else {
                    val call =
                        RestEngine.apiService.actualizarUbicacion(uid, nodoAnterior, nodoActual)
                    if (call.isSuccessful) {
                        Log.d("HOLA", "SE ACTUALIZÓ LA UBICACIÓN")
                    }
                }

            }
        }
    }


    private fun CharSequence.unaccent(): String {
        val regex = "\\p{InCombiningDiacriticalMarks}+".toRegex()
        val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
        return regex.replace(temp, "")
    }

    fun onClickAforo() {
        _verAforo.postValue(true)
    }

    fun onClickCerrarAforo() {
        _verAforo.postValue(false)
    }

    data class EdBusqueda(
        val numero: String,
        val nombre: String,
        val alias: String
    )
}