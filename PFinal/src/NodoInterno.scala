/**
  * Author: Miguel Ángel Torres López
  */
case class NodoInterno(val hijoIzquierda:Nodo, val hijoDerecha:Nodo, val caracteres:List[Char], val peso:Int) extends Nodo {
  override def toString: String = "\n(Nodo Intermedio) Caracteres: " + caracteres + " , peso: " + peso + "\n"
}
