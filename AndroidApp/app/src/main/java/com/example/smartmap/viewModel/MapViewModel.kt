package com.example.smartmap.viewModel

import androidx.compose.ui.text.substring
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartmap.model.Lugar
import com.example.smartmap.model.LugarApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MapViewModel : ViewModel() {
    private val _masInformacion = MutableLiveData<Boolean>()
    val masInformacion = _masInformacion

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

    private val _edificios = mutableListOf<Lugar>()

    private val _descripcionEdificio = MutableStateFlow("")
    val descripcionEdificio = _descripcionEdificio

    private val _datosCalcularRuta = MutableStateFlow("")

    fun inicializarEdificio(numero: String) {
        _numeroEdificio.value = numero
        viewModelScope.launch {
            _nombreEdificio.value = LugarApp().room.lugarDao().getNombreByNumero(numero)
            _descripcionEdificio.value = LugarApp().room.lugarDao().getDescripcionByNumero(numero)
            _urlImagenEdificio.value = LugarApp().room.lugarDao().getImagenByNumero(numero)
        }
    }

    fun generarEdificios() {
        _edificios.addAll(
            listOf<Lugar>(
                Lugar(
                    id = 1,
                    numero = "1",
                    nombre = "Casa Navarro",
                    descripcion = "La Casa Instituto Pensar, ubicada entre el Edificio Gabriel Giraldo y la Avenida Séptima era el lugar donde habitaba el señor Navarro, un distinguido caballero quien al fallecer, donó su morada al Distrito; el cual, tiempo después, la cedió por convenio a la Universidad Javeriana en 1977.",
                    imagen = "https://s3.amazonaws.com/s3.timetoast.com/public/uploads/photo/19119490/image/medium-dc42b8b8f4f5aa1e89292936620c72f6.jpg"
                ),
                Lugar(
                    id = 2,
                    numero = "2",
                    nombre = "Ed. Fernando Barón, S.J.",
                    descripcion = "",
                    imagen = "https://www.javeriana.edu.co/documents/10179/70065/dir_financiera.png/c23b3dc8-eb7a-4aa1-b611-8e2e1ad4ad57?t=1392064934242"
                ),
                Lugar(
                    id = 3,
                    numero = "3",
                    nombre = "Ed Gabriel Giraldo, S.J.",
                    descripcion = "Está conformado por nueve pisos y sótano, con un auditorio para 250 personas; 5 salones múltiples especiales para 270 asistentes.\n" +
                            "\n" +
                            "\nTambién tiene una capilla para 40 personas; 25 aulas para 1.390 estudiantes. La capacidad de los salones es de hasta 100 asistentes..\n" +
                            "\nPara el servicio de alimentación tiene dos cafeterías: una para 250 personas en el segundo piso, con vista al Parque Nacional; y otra en el último piso, con vista panorámica.\n" +
                            "\n" +
                            "\nPor último, tiene una capacidad de 127 cupos en el sótano, 73 exteriores y 54 cubiertos.",
                    imagen = "https://www.javeriana.edu.co/image/journal/article?img_id=8368088&t=1486680911826"
                ),
                Lugar(
                    id = 4,
                    numero = "4",
                    nombre = "Ed. Gerardo Arango Puerta",
                    descripcion = "El edificio corresponde a la facultad de artes, donde posee espacios interiores que cuentan con múltiples aulas de música para práctica individual y grupal, ambientes de gran formato para prácticas de baile, talleres y laboratorios de fotografía, escultura y pintura.\n" +
                            "La acústica de las aulas, talleres y zonas de estudio fue apropiada para la labor educativa\n" +
                            "que se desarrolló en cada una de ellas.\n" +
                            "El diseño arquitectónico del edificio de la Universidad integra conceptos de tecnología,\n" +
                            "sostenibilidad e integración con el medio ambiente.",
                    imagen = "https://cniingenieros.com/wp-content/uploads/2022/05/edificio-artes-upj-llanofotografia-2700.jpg"
                ), Lugar(
                    id = 5,
                    numero = "5",
                    nombre = "Talleres de Diseño Industrial",
                    descripcion = "Se encuentra ubicado en el primer piso del Edificio 15 y tiene como objetivo principal brindar a los estudiantes y profesores de la Facultad de Arquitectura y Diseño, recursos adicionales que faciliten llevar a buen término la actividad académica de los estudiantes y profesores que hacen parte de los diferentes programas de pregrado y posgrado.\n" +
                            "\n" +
                            "Está compuesto por una Sala de Bienestar dotada con mobiliario para el trabajo en grupo y/o individual; una Sala de Cómputo con 18 equipos de escritorio y 7 portátiles para consulta y trabajo académico individual, todos con software especializado; un Punto de Control administrado por dos auxiliares encargados del préstamo de los equipos y casilleros, y de vigilar el uso adecuado y seguro de los espacios físicos y el mobiliario de dotación.",
                    imagen = "https://www.javeriana.edu.co/recursosdb/1372208/0/Porque+estudiar+javeriana+-+AYD+%281%29.jpg/ee87bf94-eb00-e206-fa14-f74466cbb3cc?version=1.0&t=1625238883483&download=true"
                ), Lugar(
                    id = 7,
                    numero = "7",
                    nombre = "Ed. Lorenzo Uribe, S.J.",
                    descripcion = "Edificio que corresponde a la facultad de educación y se encuentra sobre la carrera séptima con calle 42",
                    imagen = "data:image/jpeg;base64,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"
                ), Lugar(
                    id = 8,
                    numero = "8",
                    nombre = "Ed. Ático",
                    descripcion = "ÁTICO es el acrónimo que permite designar una de las iniciativas más destacadas de la Pontificia Universidad Javeriana. Desde septiembre de 2010, las artes, las tecnologías, la información y la Comunicación convergieron en un recinto que se denomina hoy Centro Ático.\n" +
                            "\n" +
                            "El Centro Ático aloja en un edificio de siete niveles, de aproximadamente 8.000 metros cuadrados de construcción, laboratorios de cine, televisión, video, radio, videojuegos, animación experimental, arquitectura, música, sonidos, diseño, creación digital, un auditorio y un salón creativo.\n",
                    imagen = "https://www.javeriana.edu.co/recursosdb/2807168/2923149/img_historia.png/ab2de223-b8d6-049c-b933-6a24f75426e5?t=1627527253578"
                ), Lugar(
                    id = 9,
                    numero = "9",
                    nombre = " Ed. Julio Carrizosa",
                    descripcion = "El edificio se encuentra debajo de la actual plazoleta de ingeniería, se le conocía antiguamente por ser la facultad de ciencias políticas y relaciones internacionales, las cuales ahora se encuentran en el edifico Giraldo.",
                    imagen = "https://fastly.4sqi.net/img/general/600x600/3254716_JISIyf2zU49i4rIsA5gAO5VNvUuRudVcQQgE36pvYnM.jpg"
                ), Lugar(
                    id = 11,
                    numero = "11",
                    nombre = "Ed. José Gabriel Maldonado S.J.",
                    descripcion = "Edificio donde se encuentra la facultad de ingeniería para las carreras de electrónica, sistemas, industrial, entre otras.",
                    imagen = "https://www.javeriana.edu.co/recursosdb/20125/877848/EdIngenieriaLab3.JPG/3a6521b4-8061-d00f-1099-37402c03d790"
                ), Lugar(
                    id = 12,
                    numero = "12",
                    nombre = "Ed. José Gabriel Maldonado S.J.",
                    descripcion = "Moderno edificio de laboratorios de 14.000 metros cuadrados destinados a la docencia, investigación y servicio que impulsan el crecimiento y desarrollo tecnológico del país.\n" +
                            "\n" +
                            "Cuenta con más de 96 espacios donde cada uno de los espacios del edificio fue diseñado para ser un espacio vivo de aprendizaje bajo la premisa de compartir el conocimiento.\n" +
                            "\n" +
                            "Como una muestra más del compromiso de la Pontificia Universidad Javeriana con el cuidado de nuestra casa común, el edificio fue reconocido con la Certificación Edge del Banco Mundial, donde se resaltan sus características de uso eficiente de agua y energía, uso de materiales amigables con el medio ambiente y su diseño bioclimático.",
                    imagen = "https://ingenieria.javeriana.edu.co/documents/1569590/1834781/im_ed.jpg/0129e13d-38fa-eabf-ce68-d81d5b8da4f7?t=1643388307373"
                ), Lugar(
                    id = 15,
                    numero = "15",
                    nombre = "Ed. Leopoldo Rother",
                    descripcion = "",
                    imagen = ""
                ), Lugar(
                    id = 16,
                    numero = "16",
                    nombre = "Ed. Carlos Arbeláez Camacho",
                    descripcion = "",
                    imagen = ""
                ),
                Lugar(
                    id = 17,
                    numero = "17",
                    nombre = "Capilla Nuestra Señora del Camino",
                    descripcion = "Capilla Nuestra Señora del Camino",
                    imagen = "https://www.javeriana.edu.co/image/journal/article?img_id=3103647&t=1412185047303"
                ),
                Lugar(
                    id = 18,
                    numero = "18",
                    nombre = "Talleres de Arquitectura",
                    descripcion = "Talleres destinados a los estudiantes de Arquitectura",
                    imagen = "https://arquidiseno.javeriana.edu.co/documents/1575957/2304342/42496997_1989372561159544_8406275193353273344_o%20(2)_opt.jpg/7b42caa3-6425-0bf9-a1ab-a2107c1b5fc1"
                ),
                Lugar(
                    id = 20,
                    numero = "20",
                    nombre = "Ed. Jorge Hoyos Vásquez, S.J.",
                    descripcion = "\"En su interior está compuesto por 9 pisos el cual es completamente permeable desde la ciudad hacia la universidad y no es una barrera ni obstáculo. El edificio es facilitador en términos de movilidad, siendo conscientes de la fragilidad de este tema el edificio en su primer piso, nivel carrera 7, genera un car lobby, un anillo dentro de este que permite el constante flujo vehicular.\"",
                    imagen = "https://www.javeriana.edu.co/recursosdb/20125/877892/EdJorgeHoyos1Cra7.JPG/6814edd2-0398-7bee-58b7-466f004daeb1"
                ), Lugar(
                    id = 21,
                    numero = "21",
                    nombre = " Ed. Emilio Arango, S.J.",
                    descripcion = "En su interior estan las instalaciones del Centro Pastoral San Francisco Javier y el Centro de Asesoria Psicologica y Salud (CAPS)",
                    imagen = "https://www.javeriana.edu.co/recursosdb/704339/878173/thumbnail.jpg/edf1a9c0-3fa8-7a4e-7b1e-3c1a24fdc594"
                ), Lugar(
                    id = 23,
                    numero = "23",
                    nombre = "Cafetería Central",
                    descripcion = "Restaurante que cuenta con: Comedor privado para profesores y personal administrativo, \"Barra saludable\", Pizzas, Comida rapida, Desayunos y \"Listo para llevar\"",
                    imagen = "https://www.javeriana.edu.co/image/journal/article?img_id=9756062&t=1530728606108"
                ), Lugar(
                    id = 24,
                    numero = "24",
                    nombre = "Hospital Universitario San Ignacio",
                    descripcion = "Abarca todas aquellas actividades y programas que en orden a la conservación, recuperación y rehabilitación de la salud de los Miembros de la comunidad Universitaria se llevan a cabo en la Universidad.\\\" Cuenta con los servicios: \\\"Consulta externa \\\", \\\"Consulta de emergencia \\\", \\\"Consulta de especialista\\\" y \\\"Hospitalizacion.",
                    imagen = "https://www.javeriana.edu.co/colombia-cientifica/wp-content/uploads/2022/07/HUSI-800x400.jpeg"
                ), Lugar(
                    id = 25,
                    numero = "25",
                    nombre = "Facultad de Educación",
                    descripcion = "\"la Facultad atiende la formación con siete licenciaturas, tres programas de posgrado, varios proyectos de consultoría, investigación y Educación Continua.\"",
                    imagen = "https://res.cloudinary.com/civico/image/upload/c_fill,f_auto,fl_lossy,h_628,q_auto:low,w_1200/v1576600108/entity/image/file/1d4/000/5df9022c2f41f3744a0001d4.jpg"
                ), Lugar(
                    id = 26,
                    numero = "26",
                    nombre = " Facultad de Odontología",
                    descripcion = "Cuenta con 136 unidades odontologicas para la practica clinica, Quirofano para procedimientos odontologicos bajo, Sala de simulacion de alta tecnologia. Consulta externa de Odontología especializada: teléfono 6013208320 extensión 2866 o 2872",
                    imagen = "https://www.javeriana.edu.co/recursosdb/1372208/0/Porque+estudiar+javeriana-+Odontolog%C3%ADa+.jpg/286003ef-8df6-c452-3ce2-5b629832a2ec?version=1.0&t=1625778007629&download=true"
                ), Lugar(
                    id = 27,
                    numero = "27",
                    nombre = "Ed. José del Carmen Acosta",
                    descripcion = "Cuenta con salones amplios y ascensor",
                    imagen = "https://www.javeriana.edu.co/image/journal/article?img_id=9339957&t=1517933696991"
                ), Lugar(
                    id = 28,
                    numero = "28",
                    nombre = " Ed. Jesús María Fernández, S.J.",
                    descripcion = "Cuenta con un amplio catalogo de libros, prestamo de portatiles y servicio de prestamo de material bibliografico. Tiene salas de estudio individual y grupal y una zona de descanso que cuenta con televisores, peliculas y consolas de videoJuegos. ",
                    imagen = "https://www.javeriana.edu.co/image/journal/article?img_id=6865979&t=1463170416911"
                ), Lugar(
                    id = 29,
                    numero = "29",
                    nombre = "Urgencias HUSI",
                    descripcion = "Sala de urgencias del Hospital Universitario San Ignacio",
                    imagen = "https://www.javeriana.edu.co/colombia-cientifica/wp-content/uploads/2022/07/HUSI-800x400.jpeg"
                ), Lugar(
                    id = 30,
                    numero = "30",
                    nombre = "Ed. Néstor Santacoloma",
                    descripcion = "", imagen = ""
                ), Lugar(
                    id = 31,
                    numero = "31",
                    nombre = "Ed. Rafael Barrientos Conto",
                    descripcion = "El Departamento de Morfología de la Facultad de Medicina de la Pontificia Universidad Javeriana participa en la formación académica de estudiantes de distintas facultades de la Universidad ofreciéndoles una visión de la morfología humana con énfasis en la aplicación clínica de los conocimientos.",
                    imagen = "https://medicina.javeriana.edu.co/documents/1610269/1706371/IMG_8605.JPG/ddd3c227-8cd0-632d-fb52-f13a04af9091?t=1628777724154"
                ), Lugar(
                    id = 32,
                    numero = "32",
                    nombre = "Intituto de Genética Humana",
                    descripcion = "El Instituto de Genética Humana (IGH) ha sido categorizado sistemáticamente por Colciencias en la más alta posición jerárquica (A1), y recibió en 2018 el grado de Centro de Investigación en el sistema nacional de ciencia y tecnología de Colombia. ",
                    imagen = ""
                ), Lugar(
                    id = 34,
                    numero = "34",
                    nombre = "Auditorio Alejandro Novoa, S.J.",
                    descripcion = "Auditorio ubicado al frente de la biblioteca, sobre la septima, en donde se realizan diversidad de eventos y conferencias de diferentes temas",
                    imagen = "https://www.javeriana.edu.co/recursosdb/20125/724948/Centro-de-vacunaci%C3%B3n-covid-Alejandro-Novoa.jpg/f390274b-7d30-27bf-0d88-b3d4802c2946?version=1.0&t=1635176481391"
                ), Lugar(
                    id = 35,
                    numero = "35",
                    nombre = "Central de Vigilancia",
                    descripcion = "", imagen = ""
                ), Lugar(
                    id = 41,
                    numero = "41",
                    nombre = "Ed. Pablo VI",
                    descripcion = "",
                    imagen = "https://res.cloudinary.com/civico/image/upload/c_fit,f_auto,fl_lossy,h_1200,q_auto:low,w_1200/v1421956435/entity/image/file/0b2/001/52bce75231e93c08a00010b2.jpg"
                ), Lugar(
                    id = 42,
                    numero = "42",
                    nombre = " Facultad de Artes Ala Oriental",
                    descripcion = "", imagen = ""
                ), Lugar(
                    id = 43,
                    numero = "43",
                    nombre = "Ed. Catalán",
                    descripcion = "Edificio ubicado en el area de ciencias basicas de la universidad y donde se encuetra el centro pastoral San Francisco Javier",
                    imagen = "data:image/jpeg;base64,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"
                ), Lugar(
                    id = 44,
                    numero = "44",
                    nombre = "Salones Carrera de Estudios Musicales",
                    descripcion = "", imagen = ""
                ), Lugar(
                    id = 45,
                    numero = "45",
                    nombre = "Capilla San Francisco Javier",
                    descripcion = "",
                    imagen = "https://www.javeriana.edu.co/image/journal/article?img_id=3103647&t=1412185047303"
                ), Lugar(
                    id = 46,
                    numero = "46",
                    nombre = "Ed. Cataluña",
                    descripcion = "",
                    imagen = "https://elcomercio.pe/resizer/JB2qj4czbu4-5L6M-CTpZxiex-U=/1200x800/smart/filters:format(jpeg):quality(75)/arc-anglerfish-arc2-prod-elcomercio.s3.amazonaws.com/public/QCO5Y4PJLZFRLGXEOEL5WF6GZA.jpg"
                ), Lugar(
                    id = 47,
                    numero = "47",
                    nombre = "Ed. Quindío",
                    descripcion = "", imagen = ""
                ), Lugar(
                    id = 48,
                    numero = "48",
                    nombre = "Ed. Juniorado, Bienestar",
                    descripcion = "", imagen = ""
                ), Lugar(
                    id = 49,
                    numero = "49",
                    nombre = "Auditorio Félix Restrepo",
                    descripcion = "Auditorio ubicado a la entrada por la Cra. 7ma del sector de ciencias basicas de la Javeriana",
                    imagen = "https://docplayer.es/docs-images/77/76643530/images/3-3.jpg"
                ), Lugar(
                    id = 50,
                    numero = "50",
                    nombre = "Ed. Félix Restrepo, S.J.",
                    descripcion = "La Facultad de Ciencias desarrolla investigación básica y aplicada en diferentes áreas de conocimiento, como lo son Biología, Química, Microbiología, Matemáticas, Bacteriología, Nutrición, Bioquímica y Física, pensando sus programas de investigación a diferentes niveles y perspectivas",
                    imagen = "https://ciencias.javeriana.edu.co/documents/1578117/1641331/Bienvenida_1.jpeg/66d63936-4b06-6fdd-37fc-d77d58028779?t=1652731443840"
                ), Lugar(
                    id = 51,
                    numero = "51",
                    nombre = "Ed. Angel Valtierra, S.J.",
                    descripcion = "La Facultad de Ciencias desarrolla investigación básica y aplicada en diferentes áreas de conocimiento, como lo son Biología, Química, Microbiología, Matemáticas, Bacteriología, Nutrición, Bioquímica y Física, pensando sus programas de investigación a diferentes niveles y perspectivas",
                    imagen = "https://ciencias.javeriana.edu.co/documents/1578117/1641331/Bienvenida_1.jpeg/66d63936-4b06-6fdd-37fc-d77d58028779?t=1652731443840"
                ), Lugar(
                    id = 52,
                    numero = "52",
                    nombre = "Ed. Carlos Ortiz, S.J.",
                    descripcion = "La Facultad de Ciencias desarrolla investigación básica y aplicada en diferentes áreas de conocimiento, como lo son Biología, Química, Microbiología, Matemáticas, Bacteriología, Nutrición, Bioquímica y Física, pensando sus programas de investigación a diferentes niveles y perspectivas",
                    imagen = "https://fastly.4sqi.net/img/general/600x600/1095341_GsL9472hqYcTWdxwcr8rC9jHPINu40m6nh6BSbeeU1I.jpg"
                ), Lugar(
                    id = 53,
                    numero = "53",
                    nombre = "Ed. Jesús Emilio Ramírez, S.J.",
                    descripcion = "La Facultad de Ciencias desarrolla investigación básica y aplicada en diferentes áreas de conocimiento, como lo son Biología, Química, Microbiología, Matemáticas, Bacteriología, Nutrición, Bioquímica y Física, pensando sus programas de investigación a diferentes niveles y perspectivas",
                    imagen = "https://fastly.4sqi.net/img/general/600x600/59757957_lZtYlz7iEzeGOv5Pjwyuw6ekoFVhK_4EBBSFr9WhtaU.jpg"
                ), Lugar(
                    id = 54,
                    numero = "54",
                    nombre = "Ed. Jesús Emilio Ramírez, S.J.",
                    descripcion = "La Facultad de Ciencias desarrolla investigación básica y aplicada en diferentes áreas de conocimiento, como lo son Biología, Química, Microbiología, Matemáticas, Bacteriología, Nutrición, Bioquímica y Física, pensando sus programas de investigación a diferentes niveles y perspectivas",
                    imagen = "https://fastly.4sqi.net/img/general/600x600/59757957_lZtYlz7iEzeGOv5Pjwyuw6ekoFVhK_4EBBSFr9WhtaU.jpg"
                ), Lugar(
                    id = 55,
                    numero = "55",
                    nombre = "Ed. Bioterio y Colecciones Biológicas",
                    descripcion = " Las colecciones biológicas tienen como base promover la preservación y curaduría, así como desarrollar investigación en taxonomía, sistemática, biogeografía y ecología en los grupos biológicos depositados en estas. Las colecciones biológicas se nutren con ejemplares de investigaciones realizadas por diferentes unidades de la Facultad de Ciencias,",
                    imagen = ""
                ), Lugar(
                    id = 67,
                    numero = "67",
                    nombre = "Ed. José Rafael Arboleda, S.J.",
                    descripcion = "\"La facultad de comunicación y lenguaje se encuentra integrada por la carrera de Ciencia de la Información-Bibliotecología, la carrera de Comunicación Social, \n" +
                            "la carrera de Licenciatura en Lenguas Modernas. Tambien cuenta con el centro de escritura, un centro acompañamiento para estudiantes y maestros en sus trabajos escritos.\"",
                    imagen = "https://comunicacionylenguaje.javeriana.edu.co/documents/1578195/1666772/Asi-se-ve-1.png/af9ad23d-6d07-b120-92c6-85117e035c6a"
                ), Lugar(
                    id = 90,
                    numero = "90",
                    nombre = "Cancha de Fútbol",
                    descripcion = "El campo de fútbol de la Universidad incluye en su espacio dos canchas múltiples para fútbol sala, baloncesto y voleibol",
                    imagen = "https://pbs.twimg.com/media/Dk1UQOGWwAAHfaw?format=jpg&name=medium"
                ), Lugar(
                    id = 91,
                    numero = "91",
                    nombre = "Centro Javeriano de Formación Deportiva",
                    descripcion = "El centro Javeriano de formación deportiva es una instalación la cual cuenta con multiples zonas para realizar actividad fisica. Salas para cardio y fuerza, canchas de squash, salas de baile, una cancha para basketball, voleyball y futbol.",
                    imagen = "https://mapio.net/images-p/9640337.jpg"
                ), Lugar(
                    id = 94,
                    numero = "94",
                    nombre = "Ed. Pedro Arrupe, S.J.",
                    descripcion = "Facultad de Teología y archivo historico. Cuenta con oficinas y aulas de clase. El Archivo Historico Javeriano se encuentran un sin numero de documentos, fotografias y material que cuenta la Historia de la Universidad y del Pais.",
                    imagen = "https://mapio.net/images-p/9640629.jpg"
                ), Lugar(
                    id = 95,
                    numero = "95",
                    nombre = "Ed. Manuel Briceño Jáuregui, S.J.",
                    descripcion = "Facultad de ciencias sociales, psicologia y filosofia, cuenta con oficinas y salones de clase.",
                    imagen = "https://cienciassociales.javeriana.edu.co/documents/1578181/2216668/_DSC6291--.jpg/0a2e8115-d014-1dce-f7e2-5eeadeb08904"
                ), Lugar(
                    id = 115,
                    numero = "115",
                    nombre = "Ed. Don Guillermo Castro",
                    descripcion = "El edificio de parqueaderos, cuenta con 7 pisos para parqueaderos, un servicio para solicitar taxi y una cafeteria.",
                    imagen = "https://www.javeriana.edu.co/image/journal/article?img_id=7690742&t=1474558826866"
                ), Lugar(
                    id = 116,
                    numero = "116",
                    nombre = "Ed. Hernando Arellano Ángel",
                    descripcion = "Vicerrectoria de extensión continua y relaciones interinstitucionales",
                    imagen = "https://www.javeriana.edu.co/image/journal/article?img_id=11725529&t=1592609626987"
                )
            )
        )
        guardarEdificios()
    }

    private fun guardarEdificios() {
        viewModelScope.launch {
            LugarApp().room.lugarDao().insert(_edificios)
        }
    }

    fun onClickMasInformacion() {
        _masInformacion.postValue(true)
    }

    fun onClickBuscar() {
        _buscarEdificio.postValue(true)
    }

    fun finalizarBusqueda() {
        _buscarEdificio.postValue(false)
        _edificioABuscar.value = String()
    }

    fun onClickCerrarMasInformacion() {
        _masInformacion.postValue(false)
    }

    fun onEdTextChange(texto: String) {
        _edificioABuscar.value = texto
    }

    fun procesarRespuesta(string: String): String {
        val nodo = string.substringBefore("-")
        val edificio = string.substringAfter("-")
        _datosCalcularRuta.value = "Basico-$nodo-ED-$edificio"
        return "Basico-$nodo-ED-$edificio"
    }

    fun buscarEdificioPorNombre(texto: String) {
        _edificios.forEach {
            if (it.nombre.contains(texto)) {
                viewModelScope.launch {
                    _numeroEdificio.value = LugarApp().room.lugarDao().getNumeroByNombre(it.nombre)
                    _buscarEdificio.postValue(true)
                }
            }
        }
    }
}