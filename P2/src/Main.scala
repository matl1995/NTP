
/**
  * Objeto singleton para probar la funcionalidad del triangulo
  * de Pascal
  */
object Main {

  /**
    * Ejercicio 1: funcion para generar el triangulo de Pascal
    *
    * @param columna Recibe un entero con el número de columna para el que se quiere calcular el triangulo de Pascal
    * @param fila Recibe un entero con el número de fila para el que se quiere calcular el triangulo de Pascal
    * @return Devuelve el entero que tendrá el triangulo de pascal en esa fila y columna
    */
  def calcularValorTrianguloPascal(columna: Int, fila: Int): Int = {
    if(columna==0 || columna==fila) 1
    else calcularValorTrianguloPascal(columna - 1, fila - 1) + calcularValorTrianguloPascal(columna, fila - 1)
  }

  /**
    * Ejercicio 2: funcion para chequear el balance de parentesis
    *
    * @param cadena Recibe una lista de char, que forman la cadena que se quiere analizar
    * @return Devuelve un booleano que indica true si la cadena esta bien balanceada y false en caso contrario
    */
  def chequearBalance(cadena: List[Char]): Boolean = {
    //La siguiente función es auxiliar para comprobar si la cadena esta balanceada, recibe la cadena y en el caso de que
    //este vacia devuelve un 0 directamente, si no lo esta comprobamos la cadena, si encontramos un "(" en la última posición,
    //este no se va a poder cerrar por lo que no está balanceada y devolvemos directamente -1, al encontrarnos un "(" sumamos
    //1 mas una llamada a la funcion recursiva y si encontramos un ")" restamos a 1 una llamada a la función recursiva,
    //así, si por cada parentesis que abro sumo 1 y por cada parentesis que cierro resto 1, se queda en 0, por lo que
    //si la función devuelve un 0 es que está balanceada, y en otro caso no. En el caso de que el elemento de la cadena
    //no sea un parentesis simplemente se vuelve a llamar a la funcion para que compruebe el siguiente elemento.
    def comprobarParentesis(cadena: List[Char]): Int = {
      if(!cadena.isEmpty) {
        if(cadena.last=='(') //En el caso en que en la ultima posicion tengamos un parentesis abriendo, que por lo tanto no se va a cerrar, directamente devolvemos -1 ya que no está balanceada
          -1
        else if(cadena.head=='(') //En el caso de que la cadena contenga '(' tenemos un parentesis que se tiene que cerrar por lo que sumo uno y sigo comprobando
          comprobarParentesis(cadena.tail)+1
        else if(cadena.head == ')') //En caso de que la cadena contenga ')' cierro un parentesis por lo que resto 1 y sigo comprobando
          comprobarParentesis(cadena.tail)-1
        else //En caso de que no se cumplan los otros casos, es que no afecta al balanceo por lo que llamo a la funcion para seguir comprobando
          comprobarParentesis(cadena.tail)
      }
      else //En el caso de que la cadena esté vacia esta bien balanceada asi que devolvemos un 0
        0
    }

    if(comprobarParentesis(cadena)==0) //Si al llamar a la funcion devuelve un 0 es que está balanceada por lo que devuelvo true
      true
    else //Si no ha devuelto un cero es que no está balanceada por lo que devuelvo false
      false
  }


  /**
    * Ejercicio 3: funcion para determinar las posibles formas de devolver el
    * cambio de una determinada cantidad con un conjunto de monedas
    *
    * @param cantidad Recibe un entero con la cantidad para la que se quiere calcular los conjuntos de monedas con los que se puede conseguir
    * @param monedas Recibe una lista de enteros con el valor de las monedas con las que se tienen que buscar esos conjuntos que sumen la cantidad recibida
    * @return devuelve un entero que contiene el número de formas en las que se puede pagar la cantidad dada con las monedas dadas
    */
  def contarCambiosPosibles(cantidad: Int, monedas: List[Int]): Int = {
    if (cantidad==0) //Cuando me dan que la cantidad es 0, es porque ya he llegado a obtener el conjunto de monedas por lo que
                     //devuelvo un 1, ya que es una forma de conseguir la cantidad con un conjunto de monedas dado
      1
    else if (cantidad<0 || monedas.isEmpty) //Cuando me dan que la cantidad es menor que 0, devuelvo un 0 ya que no hemos conseguido obtener la cantidad con ese conjunto de monedas
                                            //Cuando me dan que no quedan mas monedas, devuelvo un 0, ya que aun me queda cantidad que cubrir pero ya no me quedan ponedas para cubrirla
      0
    else //En otro caso vuelvo a llamar a la función pasandole la cantidad menos la moneda usada, y todas las monedas que tenia
         //(así consigo simular que uso la moneda que resto a la cantidad una vez, y que se siga probando despues de esta resta),
         //y le sumo, una llamada a la función pasandole la misma cantidad, pero quitando la primera moneda de la lista
         //(así consigo que en lugar de probar con todas las moneda también pruebe usando una moneda menos cada vez)
         //lo que consigo con esto es crear el arbol de todas las posibilidades de monedas y cantidades para conseguir la cantidad deseada que existen
      contarCambiosPosibles(cantidad-monedas.head,monedas)+contarCambiosPosibles(cantidad,monedas.tail)
  }

  /**
    * Ejercicio 4: funcion para realizar la busqueda binaria generica sobre una lista de elementos de cualquier tipo
    * @param lista Es un elemento de tipo List que contiene una lista con los elementos sobre los que vamos a buscar el deseado
    * @param elem Es el elemento que se desea encontrar dentro de la lista
    * @param comparar Es la funcion que se va a utilizar para comparar los elementos deseados, y se recibe como argumento
    * @tparam A Indica que el tipo de los elementos aun no está definido por lo que puede redibir cualquier tipo de elemtentos en los lugares que ocupa A
    */
  def busquedaBinariaGenerica[A](lista:List[A], elem: A, comparar:(A,A)=>Boolean):Boolean= {
    @annotation.tailrec
    def iterar(indice:Int):Boolean = {
      // Caso base 1: no pasarse de longitud
      if(indice == lista.length-1) comparar(lista(indice), elem) //Si estoy en la ultima posicion de la lista llamo a comparar
      // Caso base 2: en cuanto encuentro un par de elementos no ordenados,
      // se devuelve false
      else if(comparar(lista(indice), elem)) true
      else iterar(indice+1)
    }

    // Desencadenamos la ejecucion
    iterar(0)
  }
}
