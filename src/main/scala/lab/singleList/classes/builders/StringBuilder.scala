package lab.singleList.classes.builders

import lab.singleList.traits.{Comparator, TypeBuilder}

import java.security.SecureRandom

class StringBuilder extends TypeBuilder {

  val codeLength:Int = 8

  override def typeName() = "String"

  override def create(): String = {
    val min = 65 // A
    val max = 90 // Z
    val sb = new StringBuffer(codeLength)
    val random = new SecureRandom
    for (i <- 0 until codeLength) {
      var c:Char = ' ';

      while(sb.indexOf(c.toString) > -1){
        c = (random.nextInt((max - min) + 1) + min).toChar
      }

      sb.append(c)
    }
    val output = sb.toString
    output
  }

  override def getComparator(): Comparator = (o1: Any, o2: Any) => ((o1.asInstanceOf[String]).compareTo(o2.asInstanceOf[String]))
}
