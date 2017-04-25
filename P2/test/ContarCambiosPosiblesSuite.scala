

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ContarCambiosPosiblesSuite extends FunSuite {
  import Main.contarCambiosPosibles

  // Prueba 1
  test("contar cambios posibles: cambio de 4 con monedas de 1 y 2") {
    val res=contarCambiosPosibles(4,List(1,2))
    println(s"Para la cantidad 4, y las monedas 1,2 hay: $res cambios posibles y debe ser 3")
    assert(contarCambiosPosibles(4,List(1,2)) === 3)
  }

  // Prueba 2
  test("contar cambios posibles: cambio de 300") {
    val res=contarCambiosPosibles(300,List(5,10,20,50,100,200,500))
    println(s"Para la cantidad 300, y las monedas 5,10,20,50,100,200,500 hay: $res cambios posibles y debe ser 1022")
    assert(contarCambiosPosibles(300,List(5,10,20,50,100,200,500)) === 1022)
  }

  // Prueba 3
  test("contar cambios posibles: cambio de 301") {
    val res=contarCambiosPosibles(301,List(5,10,20,50,100,200,500))
    println(s"Para la cantidad 301, y las monedas 5,10,20,50,100,200,500 hay: $res cambios posibles y debe ser 0")
    assert(contarCambiosPosibles(301,List(5,10,20,50,100,200,500)) === 0)
  }

  test("contar cambios posibles: cambio de 300 (cambiando de orden las monedas)") {
    val res=contarCambiosPosibles(300,List(500,5,50,100,20,200,10))
    println(s"Para la cantidad 300, y las monedas 500,5,50,100,20,200,10 hay: $res cambios posibles y debe ser 1022")
    assert(contarCambiosPosibles(300,List(500,5,50,100,20,200,10)) === 1022)
  }
}
