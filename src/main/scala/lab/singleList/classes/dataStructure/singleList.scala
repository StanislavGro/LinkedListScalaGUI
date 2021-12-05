package lab.singleList.classes.dataStructure

import lab.singleList.traits.{Linked, Comparator, someAction}
//import lab.lab.singleList.classes.dataStructure.Node

class singleList[E]() extends Linked[E], Serializable{

  class Node[E](var e:E, var n:Node[E]) extends Serializable{

    var elem:E = e
    var next:Node[E] = n

    override def toString(): String = "Node { " + "elem = " + elem + " }"

  }

  private var size:Int = 0
  var current:Node[E] = null
  var last:Node[E] = null
  var first:Node[E] = null
  var comparator:Comparator = null

  def this(elem:E) = {

    this()
    if(current == null){
      current = new Node(elem, null)
      first = current
      last = current
    }
    else
      addLast(elem)

  }

  override def addLast(e: E):Unit = {

    if (size == 0) {
      current = new Node(e, null)
      first = current
      last = current
    } else {
      val newLastNode = new Node(e, null)
      this.last.next = newLastNode
      this.last = newLastNode
    }

    size += 1

  }

  override def getSize:Int = this.size

  def setSize (newSize:Int): Unit = {
    this.size = newSize
  }
  
  def addComparator(comparator: Comparator): Unit = {
    this.comparator = comparator
  }

  override def addFirst(e: E): Unit = {

    if (size == 0) {
      current = new Node(e, null)
      first = current
      last = current
    } else
      first = new Node(e, this.first)

    size += 1

  }

  override def add(elem: E, index: Int): Unit = {

    if (index >= size) {
      if (index > size)
        System.out.println("Вы вышли за размер списка! Поэтому добавим элемент просто в конец!")
      this.addLast(elem)
    }
    else if (index == 0)
      this.addFirst(elem)
    else {
      var temp:Node[E] = null
      current = first
      for (i <- 0 until index - 1) {
        current = current.next
      }
      temp = new Node(elem, current.next)
      current.next = temp
      size += 1
    }

  }

  override def delete(index: Int): Unit = {

    if (size != 0) {
      if (index >= size)
        throw new NullPointerException("Вы вышли за пределы индексирования! Невозможно удалить этот элемент")
      else if (index == 0)
        if (size == 1) {
          first = null
          current = first
        } else {
          first = first.next
          current = first
        }
      else {
        current = first

        for (i <- 0 until index - 1)
          current = current.next

        current.next = current.next.next
      }

      while (current != null && current.next != null)
        current = current.next

      last = current
      size -= 1
    }
    else
      throw new NullPointerException("Массив абослютно пуст!!!")

  }

  override def isEmpty: Boolean = size == 0

  override def getElemByIndex(index: Int): E = {

    if (index >= size)
      throw new NullPointerException("Вы вышли за пределы индексирования!")
    else {
      current = first
      for (i <- 0 until index) {
        current = current.next
      }
      current.elem
    }

  }

  override def setElemByIndex(elem: E, index: Int): Unit = {

    if (index >= size || index < 0)
      throw new NullPointerException("Вы вышли за пределы индексирования!")
    else {
      current = first
      for (i <- 0 until index) {
        current = current.next
      }
      current.elem = elem
    }

  }

  def setNewSize(): Unit = {
    size = 0
    current = first
    while (current != null) {
      size += 1
      current = current.next
    }
  }

  def forEach(someAction: someAction[E]): Unit = {
    if (size != 0) {
      var temp = first
      for (i <- 0 until size) {
        someAction.toDo(temp.elem)
        temp = temp.next
      }
    }
    else
      println("**Пусто**")
  }

  def merge(secondList: singleList[E], comparator: Comparator): singleList[E] = {
    val out = new singleList[E]
    while (first != null && secondList.first != null)
      if (comparator.compare(first.elem, secondList.first.elem) < 0) {
        out.addLast(first.elem)
        first = first.next
      }
      else {
        out.addLast(secondList.first.elem)
        secondList.first = secondList.first.next
    }

    while (first != null) {
      out.addLast(first.elem)
      first = first.next
    }

    while (secondList.first != null) {
      out.addLast(secondList.first.elem)
      secondList.first = secondList.first.next
    }

    out

  }

}