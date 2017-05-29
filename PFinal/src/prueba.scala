import scala.io.Source

/**
  * Author: Miguel Ángel Torres López
  */
object prueba extends App {
  import Huffman._
  import ArbolDeCodificacion._

  /**
    * PRUEBA 1
    */
  //Creo los el arbol de codificación a mano
  val arbolOriginal = NodoInterno(
    NodoHoja('a', 8),
    NodoInterno(
      NodoInterno(
        NodoInterno(
          NodoHoja('g', 1),
          NodoHoja('h', 1),
          List('g','h'), 2),
        NodoInterno(
          NodoHoja('e', 1),
          NodoHoja('f', 1),
          List('e','f'), 2),
        List('g','h','e','f'), 4),
      NodoInterno(
        NodoInterno(
          NodoHoja('c', 1),
          NodoHoja('d', 1),
          List('c', 'd'), 2),
        NodoHoja('b', 3),
        List('c','d','b'), 5),
      List('g','h','e','f','c','d','b'), 9),
    List('a','g','h','e','f','c','d','b'), 17)

  //Ahora paso el string con el que se va a construir el arbol a una lista de caracteres
  val listaCaracteres = stringAListaCaracteres("aaaaaaaabbbcdefgh")

  //Ahora genero el arbol mediante el método generarArbolCodificación
  val arbolGenerado = generarArbolCodificacion(listaCaracteres)

  //Ahora compruebo si el arbol original(el que he creado a mano) es igual que el que obtiene el metodo generar... de la cadena dada
  println("¿Es el arbol original igual al generado por el metodo?: " + (arbolOriginal == arbolGenerado))


  /**
    * PRUEBA 2
    */

  // Codigo Huffman para el lenguaje frances, obtenido a partir de la pagina
  // web http://fr.wikipedia.org/wiki/Fr%C3%A9quence_d%27apparition_des_lettres_en_fran%C3%A7ais
  val codigoHuffmanFrances: Nodo = NodoInterno(
    NodoInterno(
      NodoInterno(
        NodoHoja('s', 121895),
        NodoInterno(
          NodoHoja('d', 56269),
          NodoInterno(
            NodoInterno(
              NodoInterno(
                NodoHoja('x', 5928),
                NodoHoja('j', 8351),
                List('x', 'j'), 14279),
              NodoHoja('f', 16351),
              List('x', 'j', 'f'), 30630),
            NodoInterno(
              NodoInterno(
                NodoInterno(
                  NodoInterno(
                    NodoHoja('z', 2093),
                    NodoInterno(
                      NodoHoja('k', 745),
                      NodoHoja('w', 1747),
                      List('k', 'w'), 2492),
                    List('z', 'k', 'w'), 4585),
                  NodoHoja('y', 4725),
                  List('z', 'k', 'w', 'y'), 9310),
                NodoHoja('h', 11298),
                List('z', 'k', 'w', 'y', 'h'), 20608),
              NodoHoja('q', 20889),
              List('z', 'k', 'w', 'y', 'h', 'q'), 41497),
            List('x', 'j', 'f', 'z', 'k', 'w', 'y', 'h', 'q'), 72127),
          List('d', 'x', 'j', 'f', 'z', 'k', 'w', 'y', 'h', 'q'), 128396),
        List('s', 'd', 'x', 'j', 'f', 'z', 'k', 'w', 'y', 'h', 'q'), 250291),
      NodoInterno(
        NodoInterno(
          NodoHoja('o', 82762),
          NodoHoja('l', 83668),
          List('o', 'l'), 166430),
        NodoInterno(
          NodoInterno(
            NodoHoja('m', 45521),
            NodoHoja('p', 46335),
            List('m', 'p'), 91856),
          NodoHoja('u', 96785),
          List('m', 'p', 'u'),
          188641),
        List('o', 'l', 'm', 'p', 'u'), 355071),
      List('s', 'd', 'x', 'j', 'f', 'z', 'k', 'w', 'y', 'h', 'q', 'o', 'l', 'm', 'p', 'u'), 605362),
    NodoInterno(
      NodoInterno(
        NodoInterno(
          NodoHoja('r', 100500),
          NodoInterno(
            NodoHoja('c', 50003),
            NodoInterno(
              NodoHoja('v', 24975),
              NodoInterno(
                NodoHoja('g', 13288),
                NodoHoja('b', 13822),
                List('g', 'b'), 27110),
              List('v', 'g', 'b'), 52085),
            List('c', 'v', 'g', 'b'), 102088),
          List('r', 'c', 'v', 'g', 'b'), 202588),
        NodoInterno(
          NodoHoja('n', 108812),
          NodoHoja('t', 111103),
          List('n', 't'), 219915),
        List('r', 'c', 'v', 'g', 'b', 'n', 't'), 422503),
      NodoInterno(
        NodoHoja('e', 225947),
        NodoInterno(
          NodoHoja('i', 115465),
          NodoHoja('a', 117110),
          List('i', 'a'), 232575),
        List('e', 'i', 'a'), 458522),
      List('r', 'c', 'v', 'g', 'b', 'n', 't', 'e', 'i', 'a'), 881025),
    List('s', 'd', 'x', 'j', 'f', 'z', 'k', 'w', 'y', 'h', 'q', 'o', 'l', 'm', 'p', 'u', 'r', 'c', 'v', 'g', 'b', 'n', 't', 'e', 'i', 'a'), 1486387)


  // Mensaje secreto a decodificar
  val mensajeSecretoFrances: List[Int] = List(0, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 0, 1, 1, 0, 1, 0,
    0, 1, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 1, 1,
    0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1)

  //Ahora decodifico el mensaje pasando al metodo decodificar el Arbol de codificación para el prances y el mensaje codificado en frances
  val mensajeDecodificadoFrances: List[Char] = decodificar(codigoHuffmanFrances, mensajeSecretoFrances)

  //Ahora imprimo el mensaje decodificado en frances
  println("Mensaje en frances decodificado: " + mensajeDecodificadoFrances.mkString)

  //Ahora codifico el mensaje decodificado usando el metodo rapido(utiliza la tabla)
  val codificadoRapido = codificacionRapida(codigoHuffmanFrances)(mensajeDecodificadoFrances)

  //Ahora compruebo si el original coficicado es igual al codificado rapido(debe serlo)
  println("¿Es el mensaje secreto frances igual al mensaje codificado de forma rapida?: " + (mensajeSecretoFrances == codificadoRapido))


  /**
    * PRUEBA 3
    */
  //Creo la función que me permite generar un String desde un archivo
  def leerArchivo(nombreArchivo : String) : String = {
    Source.fromFile(nombreArchivo).getLines().mkString
  }

  //Almaceno el string con el contenido de regenta.txt
  val regenta:String=leerArchivo("./data/regenta.txt")

  //Ahora paso el string con el que se va a construir el arbol a una lista de caracteres
  val listaCaracteresRegenta = stringAListaCaracteres(regenta)

  //Ahora genero el arbol mediante el método generarArbolCodificación
  val arbolRegenta = generarArbolCodificacion(listaCaracteresRegenta)


  // Se decodifica este mensaje secreto
  val mensajeSecretoEsp = List(0, 1, 1, 0, 1, 1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1,
    0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 0,
    1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0,
    0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1,
    1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0,
    0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1,
    1, 1, 1, 0, 0, 1, 0, 1, 0, 0, 1, 1, 1)

  //Ahora decodifico el mensaje con el arbol obtenido de el archivo regenta.txt
  val mensajeDecodificadoEsp: List[Char] = decodificar(arbolRegenta, mensajeSecretoEsp)

  //Ahora imprimo el mensaje decodificado en frances
  println("Mensaje en español decodificado: " + mensajeDecodificadoEsp.mkString)

  //Ahora codifico el mensaje decodificado usando el metodo normal
  val codificadoEsp = codificar(arbolRegenta,mensajeDecodificadoEsp)

  //Ahora compruebo si el original coficicado es igual al codificado(debe serlo)
  println("¿Es el mensaje secreto español igual al mensaje codificado?: " + (mensajeSecretoEsp == codificadoEsp))

}
