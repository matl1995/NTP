
/**
  * Objeto singleton para probar la funcionalidad del triangulo
  * de Pascal
  */
object Main {

  /**
    * Metodo main: en realidad no es necesario porque el desarrollo
    * deberia guiarse por los tests de prueba
    *
    * @param args
    */
  def main(args: Array[String]) {
    println("................... Triangulo de Pascal ...................")

    // Se muestran 10 filas del trinagulo de Pascal
    for (row <- 0 to 10) {
      // Se muestran 10 10 columnas
      for (col <- 0 to row)
        print(calcularValorTrianguloPascal(col, row) + " ")

      // Salto de linea final para mejorar la presentacion
      println()
    }

    // Se muestra el valor que debe ocupar la columna 5 en la fila 10
    println(calcularValorTrianguloPascal(5, 10))
    println(calcularValorTrianguloPascal(0, 0))
  }

  /**
    * Ejercicio 1: funcion para generar el triangulo de Pascal
    *
    * @param columna Recibe un entero con el número de columna para el que se quiere calcular el triangulo de Pascal
    * @param fila Recibe un entero con el número de fila para el que se quiere calcular el triangulo de Pascal
    * @return Devuelve el entero que tendrá el triangulo de pascal en esa fila y columna
    */
  def calcularValorTrianguloPascal(columna: Int, fila: Int): Int = {
    if(columna==0||columna==fila) 1
    else calcularValorTrianguloPascal(columna-1,fila-1)+calcularValorTrianguloPascal(columna,fila-1)
  }

  /**
    * Ejercicio 2: funcion para chequear el balance de parentesis
    *
    * @param cadena Recibe una lista de char, que forman la cadena que se quiere analizar
    * @return Devuelve un booleano que indica true si la cadena esta bien balanceada y false en caso contrario
    */
  def chequearBalance(cadena: List[Char]): Boolean = {
    val resultadoParentesis=comprobarParentesis(cadena)

    if(cadena.isEmpty || resultadoParentesis == 0)
    {
      return true;
    }
    else
    {
      return false;
    }
  }

  def comprobarParentesis(cadena: List[Char]): Int = {
    if(!cadena.isEmpty) {
      if (cadena.last=='(') //En caso de que la cadena acabe con '(', ese parentesis no se puede cerrar por lo que va a estar mal balanceada, devuelvo directamente menos 1 y no compruebo nada mas
      {
        return -1;
      }
      else if (cadena.head=='(') //En el caso de que la cadena contenga '(' tenemos un parentesis que se tiene que cerrar por lo que sumo uno y sigo comprobando
      {
        return 1+comprobarParentesis(cadena.tail);
      }
      else if (cadena.head != ')' && cadena.head != '(') //En el caso de que no haya ni '(' ni ')', el elemento no importa para el balance por lo que sigo comprobando
      {
        return comprobarParentesis(cadena.tail);
      }
      else if (cadena.head == ')') //En caso de que la cadena contenga ')' cierro un parentesis por lo que resto 1 y sigo comprobando
      {
        return comprobarParentesis(cadena.tail)-1;
      }
      else //En caso de que no se cumplan los otros casos, es que
      {
        return 0;
      }
    }
    else //En el caso de que la cadena esté vacia esta bien balanceada asi que devolvemos un 0
    {
      return 0;
    }
  }
/*
  /**
    * Ejercicio 3: funcion para determinar las posibles formas de devolver el
    * cambio de una determinada cantidad con un conjunto de monedas
    *
    * @param cantidad
    * @param monedas
    * @return contador de numero de vueltas posibles
    */
  def contarCambiosPosibles(cantidad: Int, monedas: List[Int]): Int = {
     // A rellenar
  }*/
}
