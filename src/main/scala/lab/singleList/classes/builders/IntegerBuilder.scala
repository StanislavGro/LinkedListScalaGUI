package lab.singleList.classes.builders

import lab.singleList.traits.{Comparator, TypeBuilder}

class IntegerBuilder extends TypeBuilder {

  val range:Int = 100

  override def typeName() = "Int"

  override def create(): Int = (Math.random * range + 1).toInt

  override def getComparator(): Comparator = (o1: Any, o2: Any) => o1.asInstanceOf[Int] - o2.asInstanceOf[Int]

}
