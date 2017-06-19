/**
  * Author: Miguel Ángel Torres López
  */
object FuncionesUtiles {
  /**
    * Esta función se encarga de calcular el peso asociado un nodo, que en caso de ser un nodo hoja será el numero
    * de apariciones de ese caracter y en caso de ser un nodo intermedio será la suma de los pesos de sus nodos hijos
    *
    * @param nodo Recibe un nodo para el que la función va a calcular su peso
    * @return Devuelve un entero que contiene el peso asociado a este nodo
    */
  def calcularPeso(nodo: Nodo): Int = nodo match {
    case NodoHoja(_, peso) => peso //En caso de que sea un nodo hoja devuelve el dato miembro peso de NodoHoja
    case NodoInterno(nodoIzquierda, nodoDerecha, _, _) => calcularPeso(nodoIzquierda) + calcularPeso(nodoDerecha) //En caso de ser nodo intermedio el peso es la suma de de los pesos de sus nodos hijos
  }

  /**
    * Esta función va a recibir un nodo(arbol de codificación) y va a elaborar una lista con los caracteres
    * que contiene dicho arbol
    *
    * @param nodo Recibe un nodo que es el nodo raiz del arbol de codificación
    * @return Devuelve una lista con los caracteres del arbol de codificación
    */
  def obtenerCaracteres(nodo: Nodo): List[Char] = nodo match {
    case NodoHoja(caracter, _) => List(caracter) //En caso de ser nodo hoja meto el caracter en una lista
    case NodoInterno(nodoIzquierda, nodoDerecha, _, _) => List.concat(obtenerCaracteres(nodoIzquierda),obtenerCaracteres(nodoDerecha)) //En caso de ser nodo intermedio concateno la lista de caracteres de sus dos nodos hijo
  }

  /**
    * Esta función recibe dos nodos(sub-arboles) y crea un arbol juntandolos
    *
    * @param nodoIzquierda Recibe un nodo que será el nodo hijo izquierdo del nuevo arbol
    * @param nodoDerecha Recibe un nodo que será el nodo hijo derecho del nuevo arbol
    * @return Devuelve un nodo, que será el nodo raiz del arbol creado de la unión de los dos subarboles
    */
  def generarArbol(nodoIzquierda: Nodo, nodoDerecha: Nodo): Nodo =
  //Creamos un nuevo nodo de tipo interno, que tiene como hijos los nodos recibidos, de caracteres la unión de los de los hijos, y de pesos la suma de los de los hijos
    NodoInterno(nodoIzquierda, nodoDerecha, List.concat(obtenerCaracteres(nodoIzquierda),obtenerCaracteres(nodoDerecha)), calcularPeso(nodoIzquierda) + calcularPeso(nodoDerecha))

}
