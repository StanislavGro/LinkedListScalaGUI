package lab.singleList.classes.dataStructure

import lab.singleList.traits.Comparator
import lab.singleList.classes.dataStructure.singleList
import scala.util.control.Breaks.breakable

class superList[E] {

  var first:superNode = null
  var last:superNode = null

  def create(inputList: singleList[E]): Unit = {

    var p:singleList[E]#Node[E] = inputList.first

    while ( p != null) {
      var list:singleList[E] = new singleList[E](p.elem)
      add(list)
      p = p.next
    }

  }

  def add(list: singleList[E]): Unit = {
    if (first == null) {
      first = new superNode(list)
      last = first
    } else {
      last.next = new superNode(list)
      last = last.next
    }
  }

  def sort(list: singleList[E], comparator: Comparator): singleList[E] = {

    this.create(list)

    while (first.next != null) do{
      val temp = first
      while ( first != null && first.next != null ) {
        first.data = first.data.merge(first.next.data, comparator)
        first.next = first.next.next
        first = first.next
      }
      last = first
      first = temp
    }


    first.data

  }

  class superNode(var data: singleList[E]) {
    var next: superNode = null
  }

}
