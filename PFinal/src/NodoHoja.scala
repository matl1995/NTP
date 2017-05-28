/**
  * Created by m_ang on 28/05/2017.
  */
case class NodoHoja(val caracter:Char, val peso:Int) extends Nodo {
  override def toString: String = "\n(Nodo hoja) Caracter: " + caracter + " , peso: " + peso + "\n"
}
