package de.htwg.se.Roulette.model

object Thirds {
  private val ft = Set(1,2,3,4,5,6,7,8,9,10,11,12)
  private val st = Set(13,14,15,16,17,18,19,20,21,22,23,24)
  private val tt = Set(25,26,27,28,29,30,31,32,33,34,35,36)

  def thirdOf(n: Int): String = n match {
    case x if ft.contains(x) => "1/3"
    case x if st.contains(x) => "2/3"
    case x if tt.contains(x) => "3/3"
    case _ => "None"
  }
}
