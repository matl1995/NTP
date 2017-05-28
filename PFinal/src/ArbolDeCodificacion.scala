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
    * Calcula las frecuencias de aparición de cada caracter
    *
    * @param texto Recibe una lista de caracteres sobre la que se van a calcular las frecuencias
    * @return Devuelve una lista que contiene pares caracter junto con un entero que contiene las veces que aparece ese caracter
    */
  def obtenerFrecuencias(texto: List[Char]): List[(Char, Int)] =
    //En cada caracter compruebo cuantos hay igual a este usando mapeo, guardo ese par en una lista y borro las demas apariciones de ese caracter
    texto.map(char => (char, texto.count(otherChar => otherChar == char))).distinct


  /**
    * Crea una lista de nodos hoja que estan ordenados por el peso de cada nodo, estando los caracteres que menos veces
    * aparecen al principio, y los que mas al final
    *
    * @param caracteres_pesos Recibe una lista de pares caracter, peso(frecuencia)
    * @return Devuelve una lista con los nodos hoja
    */
  def ordenarParejas(caracteres_pesos: List[(Char, Int)]): List[NodoHoja] =
    //Ordeno los pares nodo, peso de forma que el que tenga un peso menor vaya al principio de la lista
    //Una vez ordenados, los mapeo a una lista con los nodos hoja
    caracteres_pesos.sortWith((nodo1, nodo2) => nodo1._2 < nodo2._2).map(nodo => NodoHoja(nodo._1, nodo._2))


  /**
    * Comprueba si una lista de nodos contiene a un único elemento
    *
    * @param nodos Recibe una lista de nodos
    * @return Devuelve true en caso de que la lista de nodos tenga solo 1 elemento, y false en caso contrario
    */
  def singleton(nodos: List[Nodo]): Boolean = nodos.size == 1

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
