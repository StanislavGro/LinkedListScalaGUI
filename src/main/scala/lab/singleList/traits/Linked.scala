package lab.singleList.traits

trait Linked[E] {

  def addFirst(elem: E): Unit

  def addLast(elem: E): Unit

  def add(elem: E, index: Int): Unit

  def delete(index: Int): Unit

  def getSize: Int

  def isEmpty: Boolean

  def getElemByIndex(index: Int): E

  def setElemByIndex(elem: E, index: Int): Unit

}
