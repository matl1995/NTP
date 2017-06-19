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
    //En cada caracter compruebo cuantos hay igual a este usando mapeo, guardo ese par(caracter-peso) en una lista y borro las demas apariciones de ese caracter
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
    * Esta función saca los dos nodos con menos peso de la lista recibida, los combina en uno, y lo introduce
    * en la lista en la posición que le corresponda siguiendo el orden de pesos ascendente
    *
    * @param nodos Recibe una lista de nodos de la que va a combinar los dos elementos con menos peso
    * @return Devuelve una lista de nodos modificada
    */
  def combinar(nodos: List[Nodo]): List[Nodo] = {
    //Lamamos a generarArbol para que nos genere el nodo intermedio con los dos hoja, y despues lo introducimos al principio de la lista nuevamente
    //("::" añade un elemento al principio de la lista) y eliminamos los dos nodos que fueron unidos
    //y ahora ordenamos la lista de manera que quede ordenada por pesos ascendentes de nuevo
    (FuncionesUtiles.generarArbol(nodos.head, nodos.tail.head) :: nodos.tail.tail).sortWith((nodo1, nodo2) => FuncionesUtiles.calcularPeso(nodo1) < FuncionesUtiles.calcularPeso(nodo2))
  }

  /**
    * Hace una llamada recursiva a las funciones definidas en pasos anteriores hasta que la lista de elementos contenga solo un elemento
    *
    * @param pred Es un predicado que contiene una condición, que nos devolverá true si se cumple o false si no se cumple
    * @param func Es una función que se aplicará a la lista de nodos que se recibe como argumento
    * @param nodos Es la lista de nodos inicial sobre la que va a trabajar la función
    * @return Devuelve una lista de nodos
    */
  def repetir(pred: List[Nodo] => Boolean, func: List[Nodo] => List[Nodo])(nodos: List[Nodo]): List[Nodo] =
    if (pred(nodos)) nodos              //Si se cumple el predicado, devuelvo la lista de nodos tal cual está
    else repetir(pred, func)(func(nodos)) //Si no se cumple el predicado hago una llamada recursiva a esta función pasandole el mismo predicado, la misma función, y la lista de nodos aplicandole la función

  /**
    * Esta función construye un arbol de codificación a partir de un texto, teniendo en cuenta los caracteres y su frecuencia de aparición
    *
    * @param texto Es una lista de caracteres que contiene los caracteres de un texto
    * @return Devuelve un unico nodo que contiene todos los caracteres del texto (todos los nodos hoja en uno)
    */
  def generarArbolCodificacion(texto: List[Char]): Nodo =
    //Llamo a la función recursiva repetir que devolverá un unico nodo, que contiene todos los caracteres juntos (todos los nodos hoja en uno)
    repetir(singleton, combinar)(ordenarParejas(obtenerFrecuencias(texto))).head

}
