package lab.singleList.traits

trait TypeBuilder {
  def typeName(): String

  def create(): Any

  def getComparator(): lab.singleList.traits.Comparator
}
