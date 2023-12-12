open class Libro(val titulo: String, val autor: String, val anoPublicacion: Int, var copiasDisponibles: Int) {
    override fun toString(): String {
        return "Título: $titulo\nAutor: $autor\nAño de Publicación: $anoPublicacion\nCopias Disponibles: $copiasDisponibles\n"
    }
}

open class Usuario(val nombre: String, val dni: String) {
    val librosPrestados = mutableListOf<Libro>()

    override fun toString(): String {
        return "Nombre: $nombre\nDNI: $dni\n"
    }
}

class Biblioteca {
    private val libros = mutableListOf<Libro>()
    val usuarios = mutableListOf<Usuario>()

    fun agregarLibro(libro: Libro) {
        libros.add(libro)
    }

    fun prestarLibro(usuario: Usuario, tituloLibro: String): Boolean {
        val libro = libros.find { it.titulo == tituloLibro && it.copiasDisponibles > 0 }
        if (libro != null) {
            libro.copiasDisponibles--
            usuario.librosPrestados.add(libro)
            return true
        }
        return false
    }

    fun devolverLibro(usuario: Usuario, tituloLibro: String): Boolean {
        val libroPrestado = usuario.librosPrestados.find { it.titulo == tituloLibro }
        if (libroPrestado != null) {
            libroPrestado.copiasDisponibles++
            usuario.librosPrestados.remove(libroPrestado)
            return true
        }
        return false
    }

    fun buscarLibroPorTitulo(tituloLibro: String): Libro? {
        return libros.find { it.titulo == tituloLibro }
    }

    fun darDeAltaUsuario(usuario: Usuario) {
        usuarios.add(usuario)
    }

    fun mostrarUsuariosConLibrosPrestados() {
        for (usuario in usuarios) {
            println("Usuario: ${usuario.nombre}")
            for (libroPrestado in usuario.librosPrestados) {
                println("Libro Prestado: ${libroPrestado.titulo}")
            }
        }
    }

    fun mostrarLibrosEnBiblioteca() {
        println("Libros en la Biblioteca:")
        for (libro in libros) {
            println(libro)
        }
    }
}

fun main() {
    val biblioteca = Biblioteca()

    while (true) {
        println("Biblioteca de Libros")
        println("1. Agregar Libro")
        println("2. Prestar Libro")
        println("3. Devolver Libro")
        println("4. Mostrar Libros en la Biblioteca")
        println("5. Buscar Libro por Título")
        println("6. Dar de Alta a un Usuario")
        println("7. Mostrar Usuarios con Libros Prestados")
        println("8. Salir")
        print("Selecciona una opción: ")

        when (readLine()?.toIntOrNull()) {
            1 -> {
                println("Agregar Libro")
                print("Título del Libro: ")
                val titulo = readLine() ?: ""
                print("Autor del Libro: ")
                val autor = readLine() ?: ""
                print("Año de Publicación: ")
                val anoPublicacion = readLine()?.toIntOrNull() ?: 0
                print("Número de Copias Disponibles: ")
                val copiasDisponibles = readLine()?.toIntOrNull() ?: 0

                val libro = Libro(titulo, autor, anoPublicacion, copiasDisponibles)
                biblioteca.agregarLibro(libro)

                println("Libro agregado con éxito.")
            }
            2 -> {
                println("Prestar Libro")
                print("DNI del Usuario: ")
                val dniUsuario = readLine() ?: ""
                val usuario = biblioteca.usuarios.find { it.dni == dniUsuario }

                if (usuario != null) {
                    print("Título del Libro a prestar: ")
                    val tituloLibro = readLine() ?: ""

                    if (biblioteca.prestarLibro(usuario, tituloLibro)) {
                        println("Libro prestado con éxito.")
                    } else {
                        println("El libro no está disponible para prestar o el título no coincide.")
                    }
                } else {
                    println("Usuario no encontrado.")
                }
            }
            3 -> {
                println("Devolver Libro")
                print("DNI del Usuario: ")
                val dniUsuario = readLine() ?: ""
                val usuario = biblioteca.usuarios.find { it.dni == dniUsuario }

                if (usuario != null) {
                    print("Título del Libro a devolver: ")
                    val tituloLibro = readLine() ?: ""

                    if (biblioteca.devolverLibro(usuario, tituloLibro)) {
                        println("Libro devuelto con éxito.")
                    } else {
                        println("El usuario no tiene el libro especificado o el título no coincide.")
                    }
                } else {
                    println("Usuario no encontrado.")
                }
            }
            4 -> {
                println("Mostrar Libros en la Biblioteca")
                biblioteca.mostrarLibrosEnBiblioteca()
            }
            5 -> {
                println("Buscar Libro por Título")
                print("Título del Libro a buscar: ")
                val tituloLibro = readLine() ?: ""
                val libro = biblioteca.buscarLibroPorTitulo(tituloLibro)

                if (libro != null) {
                    println("Información del Libro:")
                    println(libro)
                } else {
                    println("Libro no encontrado.")
                }
            }
            6 -> {
                println("Dar de Alta a un Usuario")
                print("Nombre del Usuario: ")
                val nombre = readLine() ?: ""
                print("DNI del Usuario: ")
                val dni = readLine() ?: ""

                val usuario = Usuario(nombre, dni)
                biblioteca.darDeAltaUsuario(usuario)

                println("Usuario agregado con éxito.")
            }
            7 -> {
                println("Mostrar Usuarios con Libros Prestados")
                biblioteca.mostrarUsuariosConLibrosPrestados()
            }
            8 -> {
                println("¡Adiós!")
                return
            }
            else -> {
                println("Opción no válida. Por favor, selecciona una opción válida.")
            }
        }
    }
}