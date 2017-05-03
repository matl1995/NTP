//Miguel Ángel Torres López
/**
* Clase para representar conjuntos definidos mediante una funcion
* caracteristica (un predicado). De esta forma, se declara el tipo
* conjunto como un predicado que recibe un entero (elemento) como
* argumento y dvuelve un valor booleano que indica si pertenece o no
* al conjunto
*
* @param funcionCaracteristica
*/
class Conjunto(val funcionCaracteristica: Int => Boolean) {

  /**
  * Crea una cadena con el contenido completo del conjunto
  *
  * @return devuelve un string que contiene el contenido del conjunto
  */
  override def toString(): String = {
    val elementos = for (i <- -Conjunto.LIMITE to Conjunto.LIMITE if funcionCaracteristica(i)) yield i
      elementos.mkString("{", ",", "}")
    }

  /**
  * Metodo para determinar la pertenencia de un elemento al
  * conjunto
  * @param elemento
  * @return valor booleano indicando si elemento cumple
  * la funcion caracteristica o no
  */
  def apply(elemento: Int): Boolean = {
    funcionCaracteristica(elemento)
    }
}

/**
  * Objecto companion que ofrece metodos para trabajar con
  * conjuntos
  */
object Conjunto {
  /**
  * Limite para la iteracion necesaria algunas operaciones,
  * entre -1000 y 1000
  */
  private final val LIMITE = 1000

  /**
  * Metodo que permite crear objetos de la clase Conjunto
  * de forma sencilla
  * @param f
  * @return
  */
  def apply(f: Int => Boolean): Conjunto = {
    new Conjunto(f)
  }

  /**
    * Esta función sirve para crear un conjunto que solo contenga un elemento
    * @param elemento es un entero que sirve para especificar cual es el único elemento que va a poder contener el conjunto creado con esta función
    * @return devuelve un conjunto que contiene ese único elemento que puede contener
    */
  def conjuntoUnElemento(elemento : Int) : Conjunto={
    Conjunto((x:Int) => x==elemento)
  }

  /**
    * Esta funcion sirve para crear un conjunto que contiene la union de los dos que se le pasan como argumentos
    * @param c1 es un conjunto de elementos que formará parte del nuevo conjunto
    * @param c2 es un conjunto de elementos que formará parte del nuevo conjunto
    * @return devuelve un conjunto que contiene todos los elementos de los dos conjuntos recibidos como parametro
    */
  def union(c1 : Conjunto, c2 : Conjunto) : Conjunto={
    Conjunto((x:Int) => c1(x) || c2(x))
  }

  /**
    * Esta funcion sirve para crear un conjunto que contiene la interseccion de los dos que se pasan como argumentos
    * @param c1 es un conjunto de elementos que formará parte del nuevo conjunto
    * @param c2 es un conjunto de elementos que formará parte del nuevo conjunto
    * @return devuelve un conjunto que solo contiene los elementos que aparecen en los dos conjuntos recibidos como parametro
    */
  def interseccion(c1 : Conjunto, c2 : Conjunto) : Conjunto={
    Conjunto((x:Int) => c1(x) && c2(x))
  }

  /**
    * Esta funcion sirve para crear un conjunto que contiene la diferencia de los dos que se pasan como argumentos
    * @param c1 es un conjunto de elementos que formará parte del nuevo conjunto
    * @param c2 es un conjunto de elementos que formará parte del nuevo conjunto
    * @return devuelve un conjunto que contiene los elementos que aparecen en el primer conjunto pero no en el segundo
    */
  def diferencia(c1 : Conjunto, c2 : Conjunto) : Conjunto={
    Conjunto((x:Int) => c1(x) && !c2(x))
  }

  /**
    * Esta funcion sirve para crear un conjunto que contiene los elementos del conjunto recibido como parametro
    * pero filtrados por el predicado, por lo que solo estan los elementos del conjunto que cumplen ese predicado
    * @param c es un conjunto de elementos que formará parte del nuevo conjunto
    * @param predicado es una condicion que establecerá que elementos del conjunto dado forman parte del nuevo conjunto
    * @return devuelve un conjunto que contiene los elementos del conjunto dado que cumplen el predicado dado
    */
  def filtrar(c : Conjunto, predicado : Int => Boolean) : Conjunto={
    Conjunto((x:Int) => c(x) && predicado(x))
  }

  /**
    * Función que comprueba si todos los elementos del conjunto dado cumplen el predicado dado
    * @param conjunto es un conjunto de elementos sobre el que se comprobará el predicado
    * @param predicado es una condicion que se comprobará sobre cada elemento del conjunto dado
    * @return devuelve true en caso de que todos los elementos del conjunto dado cumplan el predicado dado y false en caso contrario
    */
  def paraTodo(conjunto : Conjunto, predicado : Int => Boolean) : Boolean = {
    def iterar(elemento : Int) : Boolean = {
      if (elemento>LIMITE) true
      else if (!conjunto(elemento)) iterar(elemento+1)
      else predicado(elemento) && iterar(elemento+1)
    }
    iterar(-LIMITE)
  }

  /**
    * Funcion que comprueba si al menos un elemento del conjunto cumple el predicado dado
    * @param c es un conjunto de elementos sobre el que se comprobará el predicado
    * @param predicado es una condicion que se comprobará sobre cada elemento del conjunto dado
    * @return devuelve true en caso de que algún elemento del conjunto cumpla el predicado dado y false en caso contrario
    */
  def existe(c : Conjunto, predicado : Int => Boolean) : Boolean={
    //le paso el predicado negado a la función paraTodo, para que si devuelve false, implique que hay algun elemento que cumple
    //el predicado orignal, ya que alguno no pertenece al predicado inverso, por lo que si pertenece al original, y por el
    //contrario, si devuelve true, es porque todos los elementos estan fuera del predicado original y por tanto no existe ninguno
    //que cumpla el predicado, y lo que devuelvo es la funcion paraTodo negada, ya que como he puesto, si la funcion paraTodo devuelve false
    //es porque  hay algun elemento que cumple el predicado y por tanto lo que debe devolver la funcion existe es lo contrario(true) y viceversa
    !paraTodo(c, (x:Int) => !predicado(x))
  }

  /**
    * Función que transforma el conjunto dado en otro conjunto utilizando la transformación que define la funcion dada
    * @param c es un conjunto de elementos sobre el que se aplicará la transformación que define la funcion dada
    * @param funcion es una funcion que transformará los datos conjunto dado a unos datos nuevos
    * @return devuelve un nuevo conjunto que contiene los datos del conjunto dado transformado por la función dada
    */
  def map(c : Conjunto, funcion : Int => Int) : Conjunto={
    Conjunto((x:Int) => existe(c, (y:Int) => funcion(y) == x ))
  }
}