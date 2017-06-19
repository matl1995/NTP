/**
  * Author: Miguel Ángel Torres López
  */
object Huffman {

  /**
    * Esta función se va a encargar de decodificar un texto siguiendo el algoritmo de Huffman
    *
    * @param arbol Recibe como parametro un nodo que contiene la raiz del arbol de Huffman
    * @param textoCodificado Recibe como parametro una lista de int con el texto codificado en 0 y 1
    * @return Devuelve una lista de caracteres que contiene el texto decodificado
    */
  def decodificar(arbol: Nodo, textoCodificado: List[Int]): List[Char] = {
    def decodificar0(nodo: Nodo, textoCodificado: List[Int]): List[Char] =  //Creo una función auxiliar que es recursiva
      nodo match {                                                                // Si el nodo coincide:
        case NodoHoja(caracter, _) =>                                             //    1.Con un nodo hoja
          if (textoCodificado.isEmpty) List(caracter)                             // Si el texto esta vacio, paramos y devolvemos la lista de caracteres
          else List(caracter) ++ decodificar0(arbol, textoCodificado)                   // Si no,añado el caracter actual a la lista y hago llamada recursiva
        case NodoInterno(izda, dcha, _, _) =>                                     //    2.Con un nodo interno
          if (textoCodificado.head == 0) decodificar0(izda, textoCodificado.tail) // Si hay un 0, llamo a la función recursiva pasando el nodo izquierdo y quitando el primer elemento
          else decodificar0(dcha, textoCodificado.tail)                           // Si no hay un 0 hay un 1, y llamo a la función recursiva con el nodo derecho y quitando el primer elemento
      }
    decodificar0(arbol, textoCodificado) //Lanzo la función auxiliar pasandole la raiz y el texto codificado entero
  }

  /**
    * Esta función se va a encargar de codificar un texto siguiendo el algoritmo de Huffman
    *
    * @param arbol Recibe como parametro un nodo que contiene la raiz del arbol de Huffman
    * @param texto Recibe como parametro una lista de char con el texto sin codificar
    * @return Devuelve una lista de enteros que contienen el texto codificado en 0 y 1
    */
  def codificar(arbol: Nodo, texto: List[Char]): List[Int] = {
    def codificar0(nodo: Nodo, texto: List[Char]): List[Int] =            //Creo una función auxiliar que es recursiva
      nodo match {                                                                        // Si el nodo coincide:
        case NodoHoja(_, _) =>                                                            //    1.Con un nodo hoja
          if (texto.tail.isEmpty) List()                                                  // Si es el ultimo caracter, devuelvo la lista
          else codificar0(arbol, texto.tail)                                              // Si no es el último caracter llamo a la función recursiva quitando el caracter actual
        case NodoInterno(izda, dcha, _, _) =>                                             //    2.Con un nodo interno
          if (FuncionesUtiles.obtenerCaracteres(izda).contains(texto.head)) List(0) ++ codificar0(izda, texto)  // Si el caracter está en la iquierda añado un 0 a la lista
          else List(1) ++ codificar0(dcha, texto)                                               // Si el caracter está en la derecha añado un 1 a la lista
      }
    codificar0(arbol, texto)  //Lanzo la función auxiliar pasandole la raiz del arbol y el texto sin codificar entero
  }

  //Declaro el tipo TablaCodigo que contiene el codigo asociado a cada caracter en una lista
  type TablaCodigo = List[(Char, List[Int])]

  /**
    * Esta función se va a encargar de codificar un caracter, usando una tabla
    * para no tener que recorrer el arbol
    *
    * @param tabla Recibe como argumento una tabla que contiene los caracteres y su codigo asociado
    * @param caracter Recibo como argumento el caracter que se quiere codificar
    * @return Devuelvo una lista con los enteros(0 y 1) que simbolizan ese caracter codificado
    */
  def codificarConTabla(tabla: TablaCodigo)(caracter: Char): List[Int] =
    //Filtro la tabla para encontrar el caracter, y una vez lo tengo devuelvo el caracter codificado(lista de enteros(0 y 1))
    tabla.filter(entrada => entrada._1 == caracter).head._2

  /**
    * Esta función va a crear una tablaCodigo a partir de el arbol de codificación
    *
    * @param arbol Contiene el nodo raiz del arbol que se va a pasar a tabla
    * @return Devuelve una tablaCodigo que contiene cada caracter y su codificación asociada
    */
  def convertirArbolTabla(arbol: Nodo) : TablaCodigo = {
    def convertirArbolTabla0(nodo: Nodo, lista: List[Int]): TablaCodigo = {     //Creo una función auxiliar recursiva
      nodo match {                                                                       // Si el nodo coincide:
        case NodoHoja(caracter, _) => List((caracter, lista))                            // 1.Con un nodo hoja: devuelvo el caracter con la lista de 0 y 1 hasta llegar a el
        case NodoInterno(izda, dcha, _, _) =>                                            // 2.Con un nodo interno: Llamo a la función recursiva
          convertirArbolTabla0(izda, lista ++ List(0) ) ++ convertirArbolTabla0(dcha, lista ++ List(1)) //juntando la lista izquierda y derecha que obtendre, y añadiendo un 0(izquierda) y un 1(derecha) a la lista
      }
    }
    convertirArbolTabla0(arbol, List()) //Lanzo la función auxiliar con el arbol completo y una lista vacia
  }

  /**
    * Esta función se va a encargar de codificar un texto de forma mas rapida gracias a que dispone de una tabla con las codificaciones
    * de los caracteres y no tiene que recorrer el arbol
    *
    * @param arbol Recibe como parametro el nodo raiz del arbol de codificación
    * @param texto Recibe como parametro el texto(lista de caracteres) que se quiere codificar
    * @return Devuelve una lista de enteros(0 y 1) que contiene el texto codificado
    */
  def codificacionRapida(arbol: Nodo)(texto: List[Char]): List[Int] = {
    //Creo una tabla con la codificación a partir del arbol dado
    val tablaCodigo = convertirArbolTabla(arbol)

    //Creo una variable lista de enteros que va a contener el resultado del texto codificado
    var textoCodificado: List[Int]=List()

    //Para cada caracter del texto obtengo su codificación y la añado a la lista que contiene el texto codificado
    for(caracter <- texto)
      textoCodificado++=codificarConTabla(tablaCodigo)(caracter)

    //Devuelvo el texto codificado
    textoCodificado
  }
}
