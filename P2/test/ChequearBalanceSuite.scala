

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
  * Author:Miguel Ángel Torres López
  */
@RunWith(classOf[JUnitRunner])
class ChequearBalanceSuite extends FunSuite {
  import Main.chequearBalance

  // Prueba 1
  test("chequear balance: '(if (zero? x) max (/ 1 x))' esta balanceada") {
    val res=chequearBalance("(if (zero? x) max (/ 1 x))".toList)
    println(s"La cadena: '(if (zero? x) max (/ 1 x))' esta balanceada devuelve: $res y deberia devolver true")
    assert(chequearBalance("(if (zero? x) max (/ 1 x))".toList))
  }

  // Prueba 2
  test("chequear balance: 'Te lo dije ...' esta balanceada") {
    val res=chequearBalance("Te lo dije (eso no esta (todavia) hecho).\n(Pero el no estaba escuchando)".toList)
    println(s"La cadena: 'Te lo dije (eso no esta (todavia) hecho) (Pero el no estaba escuchando)' esta balanceada devuelve: $res y deberia devolver true")
    assert(chequearBalance("Te lo dije (eso no esta (todavia) hecho).\n(Pero el no estaba escuchando)".toList))
  }

  // Prueba 3
  test("chequear balance: ':-)' no esta balanceada") {
    val res=chequearBalance(":-)".toList)
    println(s"La cadena: ':-)' esta balanceada devuelve: $res y deberia devolver false")
    assert(!chequearBalance(":-)".toList))
  }

  // Prueba 4
  test("chequear balance: no basta con contar sin mas") {
    val res=chequearBalance("())(".toList)
    println(s"La cadena: '())(' esta balanceada devuelve: $res y deberia devolver false")
    assert(!chequearBalance("())(".toList))
  }

  // Prueba 5
  test("(if (a > b) (b/a) else (a/(b*b)))"){
    val res=chequearBalance("(if (a > b) (b/a) else (a/(b*b)))".toList)
    println(s"La cadena: '(if (a > b) (b/a) else (a/(b*b)))' esta balanceada devuelve: $res y deberia devolver true")
    assert(chequearBalance("(if (a > b) (b/a) else (a/(b*b)))".toList))
  }

  // Prueba 6
  test("(ccc(ccc)cc((ccc(c))))"){
    val res=chequearBalance("(ccc(ccc)cc((ccc(c))))".toList)
    println(s"La cadena: '(ccc(ccc)cc((ccc(c))))' esta balanceada devuelve: $res y deberia devolver true")
    assert(chequearBalance("(ccc(ccc)cc((ccc(c))))".toList))
  }

  // Prueba 7
  test("(if (a > b) b/a) else (a/(b*b)))"){
    val res=chequearBalance("(if (a > b) b/a) else (a/(b*b)))".toList)
    println(s"La cadena: '(if (a > b) b/a) else (a/(b*b)))' esta balanceada devuelve: $res y deberia devolver false")
    assert(!chequearBalance("(if (a > b) b/a) else (a/(b*b)))".toList))
  }

  // Prueba 7
  test("(ccc(ccccc((ccc(c))))"){
    val res=chequearBalance("(ccc(ccccc((ccc(c))))".toList)
    println(s"La cadena: '(ccc(ccccc((ccc(c))))' esta balanceada devuelve: $res y deberia devolver false")
    assert(!chequearBalance("(ccc(ccccc((ccc(c))))".toList))
  }

  // Prueba 8
  test("())()())"){
    val res=chequearBalance("())()())".toList)
    println(s"La cadena: '())()())' esta balanceada devuelve: $res y deberia devolver false")
    assert(!chequearBalance("())()())".toList))
  }
}
