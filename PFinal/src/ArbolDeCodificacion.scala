/**
  * Author: Miguel Ángel Torres López
  */
object ArbolDeCodificacion {

  /**
    * Pasar de cadena de texto normal a lista de caracteres
    *
    * @param cadena
    * @return
    */
  def stringAListaCaracteres(cadena: String): List[Char] = cadena.toList

  /**
    * Calcula la frecuencia de aparición de cada carácter en el texto a analizar
    *
    * @param texto
    * @return
    */
  def obtenerFrecuencias(texto: List[Char]): List[(Char, Int)] =
    texto.map(char => (char, texto.count(otherChar => otherChar == char))).distinct //Mapeo las ocurrencias de cada caracter y elimino los repetidos


  /**
    * Genera una lista con toidos los nodos hoja del árbol de codificación. Esta lista de nodos terminales está
    * ordenada por pesos de forma ascendente
    *
    * @param ocurrencias
    * @return
    */
  def ordenarParejas(ocurrencias: List[(Char, Int)]): List[NodoHoja] =
    ocurrencias
      .sortWith((ocurr1, ocurr2) => ocurr1._2 < ocurr2._2)  // ordena de forma ascendente por peso
      .map(ocurr => NodoHoja(ocurr._1, ocurr._2))           // mapea el resultado en una lista de nodos hoja


  /**
    * Comprueba si una lista de nodos contiene un único elemento
    *
    * @param nodos
    * @return
    */
  def singleton(nodos: List[Nodo]): Boolean = nodos.size == 1  // comprueba que solo hay un nodo

  /**
    * Combina todos los nodos terminales
    * Pasos:
    *  - elimina de la lista de nodos los dos con menos peso (n-2)
    *  - los combina para formar un nodo intermedio con ellos
    *  - inserta ese nodo en la lista de nodos a combinar insertando de forma que se preserve el orden (n-1)
    *  - función hasta
    *
    * @param nodos
    * @return
    */
  def combinar(nodos: List[Nodo]): List[Nodo] = {
    (Huffman.generarArbol(nodos.head, nodos.tail.head) :: nodos.tail.tail)            // inserta nodo intermedio al principio
      .sortWith((nodo1, nodo2) => Huffman.calcularPeso(nodo1) < Huffman.calcularPeso(nodo2))  // ordena la lista
  }

  /**
    * Hace llamadas a las funciones definidas en pasos anteriores hasta que la lista de nodos contenga un único elemento
    *
    * @param pred
    * @param func
    * @param nodos
    * @return
    */
  def hasta(pred: List[Nodo] => Boolean, func: List[Nodo] => List[Nodo])(nodos: List[Nodo]): List[Nodo] =
    if (pred(nodos)) nodos              // solo queda un elemento
    else hasta(pred, func)(func(nodos)) // llamada recursiva sobre la combinación en los nodos actuales


  /**
    * Dado un texto, calcula y construye un árbol de codificación analizando sus caracteres y contadores de ocurrencia
    *
    * @param texto
    * @return
    */
  def generarArbolCodificacion(texto: List[Char]): Nodo =
    hasta(singleton, combinar)(ordenarParejas(obtenerFrecuencias(texto))).head  //Llamo a "hasta" sobre el texto y obtiengo el primer elemento (que es el único)

}
