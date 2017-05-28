/**
  * Author: Miguel Ángel Torres López
  */
case class NodoHoja(val caracter:Char, val peso:Int) extends Nodo {
  override def toString: String = "\n(Nodo hoja) Caracter: " + caracter + " , peso: " + peso + "\n"
}
