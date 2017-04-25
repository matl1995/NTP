
import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class BusquedaBinariaGenericaSuite extends FunSuite {

  // Se importa la clase donde estan definidas las funciones a probar
  import Main.busquedaBinariaGenerica

  // Prueba 1: Se busca un elemento entero en una lista de enteros
  test("Prueba busqueda binaria con enteros") {
    def comparar(elem1:Int,elem2:Int):Boolean={
      if(elem1==elem2) true
      else false
    }
    val res=busquedaBinariaGenerica(List(1,2,3,4,5),4,comparar)
    println(s"En la lista 1,2,3,4,5 busco el entero 4, y devuelve: $res, y deberia devolver true")
    assert(busquedaBinariaGenerica(List(1,2,3,4,5),4,comparar)==true)
  }

  // Prueba 1.1: Se busca un elemento entero en una lista de enteros
  test("Prueba busqueda binaria con enteros que falla") {
    def comparar(elem1:Int,elem2:Int):Boolean={
      if(elem1==elem2) true
      else false
    }
    val res=busquedaBinariaGenerica(List(1,2,3,4,5),7,comparar)
    println(s"En la lista 1,2,3,4,5 busco el entero 7, y devuelve: $res, y deberia devolver false")
    assert(busquedaBinariaGenerica(List(1,2,3,4,5),7,comparar)==false)
  }

  // Prueba 2: Se busca un elemento double en una lista de doubles
  test("Prueba busqueda binaria con doubles") {
    def comparar(elem1:Double,elem2:Double):Boolean={
      if(elem1==elem2) true
      else false
    }
    val res=busquedaBinariaGenerica(List(1.3,2.1,1.7,0.9,0.3),1.3,comparar)
    println(s"En la lista 1.3,2.1,1.7,0.9,0.3 busco el double 1.3, y devuelve: $res, y deberia devolver true")
    assert(busquedaBinariaGenerica(List(1.3,2.1,1.7,0.9,0.3),1.3,comparar)==true)
  }

  // Prueba 2.1: Se busca un elemento double en una lista de doubles
  test("Prueba busqueda binaria con doubles que falla") {
    def comparar(elem1:Double,elem2:Double):Boolean={
      if(elem1==elem2) true
      else false
    }
    val res=busquedaBinariaGenerica(List(1.3,2.1,1.7,0.9,0.3),0.5,comparar)
    println(s"En la lista 1.3,2.1,1.7,0.9,0.3 busco el double 0.5, y devuelve: $res, y deberia devolver false")
    assert(busquedaBinariaGenerica(List(1.3,2.1,1.7,0.9,0.3),0.5,comparar)==false)
  }

  // Prueba 3: Se busca un elemento caracter en una lista de caracteres
  test("Prueba busqueda binaria con caracteres") {
    def comparar(elem1:Char,elem2:Char):Boolean={
      if(elem1.compareTo(elem2)==0) true
      else false
    }
    val res=busquedaBinariaGenerica(List('a','b','c','d','e'),'e',comparar)
    println(s"En la lista 'a','b','c','d','e' busco el char 'e', y devuelve: $res, y deberia devolver true")
    assert(busquedaBinariaGenerica(List('a','b','c','d','e'),'e',comparar)==true)
  }

  // Prueba 3.1: Se busca un elemento caracter en una lista de caracteres
  test("Prueba busqueda binaria con caracteres que falla") {
    def comparar(elem1:Char,elem2:Char):Boolean={
      if(elem1.compareTo(elem2)==0) true
      else false
    }
    val res=busquedaBinariaGenerica(List('a','b','c','d','e'),'J',comparar)
    println(s"En la lista 'a','b','c','d','e' busco el char 'J', y devuelve: $res, y deberia devolver false")
    assert(busquedaBinariaGenerica(List('a','b','c','d','e'),'J',comparar)==false)
  }

  // Prueba 4: Se busca un elemento string en una lista de string
  test("Prueba busqueda binaria con string") {
    def comparar(elem1:String,elem2:String):Boolean={
      if(elem1.equals(elem2)) true
      else false
    }
    val res=busquedaBinariaGenerica(List("pepe","maria","juan","adrian","jose"),"jose",comparar)
    println(s"En la lista 'pepe','maria','juan','adrian','jose' busco el string 'jose', y devuelve: $res, y deberia devolver true")
    assert(busquedaBinariaGenerica(List("pepe","maria","juan","adrian","jose"),"jose",comparar)==true)
  }

  // Prueba 4.1: Se busca un elemento string en una lista de string
  test("Prueba busqueda binaria con string que falla") {
    def comparar(elem1:String,elem2:String):Boolean={
      if(elem1.equals(elem2)) true
      else false
    }
    val res=busquedaBinariaGenerica(List("pepe","maria","juan","adrian","jose"),"pedro",comparar)
    println(s"En la lista 'pepe','maria','juan','adrian','jose' busco el string 'pedro', y devuelve: $res, y deberia devolver false")
    assert(busquedaBinariaGenerica(List("pepe","maria","juan","adrian","jose"),"pedro",comparar)==false)
  }

  // Prueba 5: Se busca un elemento lista de enteros dentro de una lista de listas de enteros
  test("Prueba busqueda binaria con listas de enteros") {
    def comparar(elem1:List[Int],elem2:List[Int]):Boolean={
      @annotation.tailrec
      def iterar(indice:Int):Boolean = {
        //Caso base 1: esta en el ultimo elemento de la lista y coincide, entonces devolvemos true
        if(indice==elem1.length-1 && elem1(indice)==elem2(indice)) true
        // Caso base 2: coincide el elemento y seguimos iterando
        else if(elem1(indice) == elem2(indice)) iterar(indice+1)
        // Caso base 3: no coincide un elemento por lo que las listas no son iguales, entonces devolvemos false
        else false
      }

      // Desencadenamos la ejecucion
      iterar(0)
    }
    val res=busquedaBinariaGenerica(List(List(1,2,3,4,5),List(6,7,8,9,10),List(11,12,13,14,15),List(16,17,18,19,20),List(21,22,23,24,25)),List(6,7,8,9,10),comparar)
    println(s"En la lista List(1,2,3,4,5),List(6,7,8,9,10),List(11,12,13,14,15),List(16,17,18,19,20),List(21,22,23,24,25) busco el list List(6,7,8,9,10), y devuelve: $res, y deberia devolver true")
    assert(busquedaBinariaGenerica(List(List(1,2,3,4,5),List(6,7,8,9,10),List(11,12,13,14,15),List(16,17,18,19,20),List(21,22,23,24,25)),List(6,7,8,9,10),comparar)==true)
  }

  // Prueba 5.1: Se busca un elemento lista de enteros dentro de una lista de listas de enteros
  test("Prueba busqueda binaria con listas de enteros que falla") {
    def comparar(elem1:List[Int],elem2:List[Int]):Boolean={
      @annotation.tailrec
      def iterar(indice:Int):Boolean = {
        //Caso base 1: esta en el ultimo elemento de la lista y coincide, entonces devolvemos true
        if(indice==elem1.length-1 && elem1(indice)==elem2(indice)) true
        // Caso base 2: coincide el elemento y seguimos iterando
        else if(elem1(indice) == elem2(indice)) iterar(indice+1)
        // Caso base 3: no coincide un elemento por lo que las listas no son iguales, entonces devolvemos false
        else false
      }

      // Desencadenamos la ejecucion
      iterar(0)
    }
    val res=busquedaBinariaGenerica(List(List(1,2,3,4,5),List(6,7,8,9,10),List(11,12,13,14,15),List(16,17,18,19,20),List(21,22,23,24,25)),List(6,17,18,9,15),comparar)
    println(s"En la lista List(1,2,3,4,5),List(6,7,8,9,10),List(11,12,13,14,15),List(16,17,18,19,20),List(21,22,23,24,25) busco el list List(6,17,18,9,15), y devuelve: $res, y deberia devolver false")
    assert(busquedaBinariaGenerica(List(List(1,2,3,4,5),List(6,7,8,9,10),List(11,12,13,14,15),List(16,17,18,19,20),List(21,22,23,24,25)),List(6,17,18,9,15),comparar)==false)
  }
}
