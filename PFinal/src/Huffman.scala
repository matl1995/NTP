/**
  * Author: Miguel Ángel Torres López
  */
object Huffman {

  type TablaCodigo = List[(Char, List[Int])]

  /**
    * Recibe como argumento un nodo y devuelve el peso asociado calculando los pesos de los nodos inferiores, desde las
    * hojas hasta sus hijos
    *
    * @param n1
    * @return
    */
  def calcularPeso(n1: Nodo): Int = n1 match {
    case NodoHoja(_, peso) => peso
    case NodoInterno(nodoIzquierda, nodoDerecha, _, _) => calcularPeso(nodoIzquierda) + calcularPeso(nodoDerecha)
  }

  /**
    * Recibe como argumento un árbol de codificación (un nodo, su raíz) y devuelve la lista de caracteres que
    * representa considerando todos los nodos inferiores
    *
    * @param n1
    * @return
    */
  def obtenerCaracteres(n1: Nodo): List[Char] = n1 match {
    case NodoHoja(caracter, _) => List(caracter)
    case NodoInterno(nodoIzquierda, nodoDerecha, _, _) => List.concat(obtenerCaracteres(nodoIzquierda),obtenerCaracteres(nodoDerecha))
  }

  /**
    * Recibe como argumento los subárboles a izquierda y derecha y genera un nuevo árbol a partir de ellos
    *
    * @param nodoIzquierda
    * @param nodoDerecha
    * @return
    */
  def generarArbol(nodoIzquierda: Nodo, nodoDerecha: Nodo): Nodo =
    NodoInterno(nodoIzquierda, nodoDerecha, obtenerCaracteres(nodoIzquierda) ++ obtenerCaracteres(nodoDerecha), calcularPeso(nodoIzquierda) + calcularPeso(nodoDerecha))

  /**
    * Decodifica un texto siguiendo un código Huffman
    *
    * @param arbol
    * @param textoCodificado
    * @return
    */
  def decodificar(arbol: Nodo, textoCodificado: List[Int]): List[Char] = {
    def decodificar0(nodo: Nodo, textoCodificado: List[Int]): List[Char] =
      nodo match {                                                                // nodo actual
        case NodoHoja(caracter, _) =>                                             // es nodo hoja
          if (textoCodificado.isEmpty) List(caracter)                             // último bit => criterio de parada
          else caracter :: decodificar0(arbol, textoCodificado)                   // no último bit => guarda el caracter y continúa
        case NodoInterno(izda, dcha, _, _) =>                                  // es nodo intermedio
          if (textoCodificado.head == 0) decodificar0(izda, textoCodificado.tail) // bit == 0 => recursividad por la izda con el resto de bits
          else decodificar0(dcha, textoCodificado.tail)                           // bit == 1 => recursividad por la dcha con el resto de bits
      }
    decodificar0(arbol, textoCodificado) // comienza desde la raiz con el texto codificado completo
  }

  /**
    * Codifica un texto siguiendo un código Huffman
    *
    * @param arbol
    * @param texto
    * @return
    */
  def codificar(arbol: Nodo, texto: List[Char]): List[Int] = {
    def codificar0(nodo: Nodo, texto: List[Char]): List[Int] =
      nodo match {                                                                        // nodo actual
        case NodoHoja(_, _) =>                                                            // es nodo hoja
          if (texto.tail.isEmpty) List()                                                  // último caracter => criterio de parada
          else codificar0(arbol, texto.tail)                                              // no último caracter => continúa con el siguiente carácter
        case NodoInterno(izda, dcha, _, _) =>                                          // es nodo intermedio
          if (obtenerCaracteres(izda).contains(texto.head)) 0 :: codificar0(izda, texto)  // el carácter está a la izquierda => añade un 0
          else 1 :: codificar0(dcha, texto)                                               // el carácter está a la derecha => añade un 1
      }
    codificar0(arbol, texto)  // comienza desde la raiz con el texto completo
  }

  /**
    * Codifica un texto siguiendo un código Huffman usando una tabla para no tener que recorrer el árbol
    *
    * @param tabla
    * @param caracter
    * @return
    */
  def codificarConTabla(tabla: TablaCodigo)(caracter: Char): List[Int] =
    tabla
      .filter(entrada => entrada._1 == caracter)  // filtra para buscar la entrada con el carácter
      .head._2                                    // devuelve el carácter encriptado

  /**
    * Crear tabla visitando el arbol de codificación
    *
    * @param arbol
    * @return
    */
  def convertirArbolTabla(arbol: Nodo) : TablaCodigo = {
    def convertirArbolTabla0(nodo: Nodo, lista: List[Int]): TablaCodigo = {
      nodo match {                                                                          // nodo actual
        case NodoHoja(caracter, _) => List((caracter, lista))                               // es nodo hoja => devuelve el caracter con la lista hasta llegar a él
        case NodoInterno(izda, dcha, _, _) =>                                            // es nodo intermedio => recursividad a izda y dcha agregando un 0 y 1
          convertirArbolTabla0(izda, lista :+ 0) ::: convertirArbolTabla0(dcha, lista :+ 1) //                       a la lista respectivamente
      }
    }
    convertirArbolTabla0(arbol, List()) // comienza con el arbol completo y una lista vacía
  }

  /**
    * Codifica un texto siguiendo un código Huffman usando una tabla para no tener que recorrer el árbol
    *
    * @param arbol
    * @param texto
    * @return
    */
  def codificacionRapida(arbol: Nodo)(texto: List[Char]): List[Int] = {
    val tablaCodigo = convertirArbolTabla(arbol)                            // crea la tabla a partir del árbol
    (for(caracter <- texto) yield codificarConTabla(tablaCodigo)(caracter)) // obtiene lista para cada caracter del texto
      .flatten                                                              // convierte el List[List[Int]] a List[Int]
  }
}
