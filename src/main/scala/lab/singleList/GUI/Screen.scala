package lab.singleList.GUI

import lab.singleList.classes.*
import lab.singleList.classes.builders.{IntegerBuilder, StringBuilder}
import lab.singleList.classes.dataStructure.{singleList, superList}
import lab.singleList.traits.{TypeBuilder, someAction}
import scalafx.Includes.*
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.beans.property.StringProperty
import scalafx.collections.ObservableBuffer
import scalafx.event.ActionEvent
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.*
import scalafx.scene.image.Image
import scalafx.scene.layout.VBox
import scalafx.scene.paint.Color.{Black, LightGray}

import java.awt.Desktop.Action
import java.io.{FileInputStream, FileOutputStream, ObjectInputStream, ObjectOutputStream}


object Screen extends JFXApp3 {

  class NodeTableElement(val value: String) {
    val nodeValue = new StringProperty(value)
  }

  var box:VBox = null
  var nodeTable:TableView[NodeTableElement] = null
  var integerBuilder:IntegerBuilder = null
  var stringBuilder:StringBuilder = null
  var singleLInt:singleList[Int] = null
  var singleLString:singleList[String] = null

  //От сюда происходит запуск программы
  override def start(): Unit = {

    box = new VBox {
      autosize()
    }

    stage = new PrimaryStage {

      //Отрисовывем сцену в окне
      scene = new Scene(1000, 500) {
        //Название окна
        title = "LinkedList ScalaFX"

        fill = LightGray
        root = box

      }
    }

    val choices = Seq("Integer", "String")

    val dialog = new ChoiceDialog(defaultChoice = "Integer", choices = choices) {
      initOwner(stage)
      title = "List type"
      headerText = "Please choose type of your list"
    }

    val result = dialog.showAndWait()

    result match {
      case Some(choice) =>
        if (choice == "Integer") {
          integerBuilder = new IntegerBuilder()
          singleLInt = new singleList[Int]()
        }
        else {
          stringBuilder = new StringBuilder()
          singleLString = new singleList[String]()
        }
      case None =>
        println("No selection")
        this.stopApp()
    }

    configureTable(box)

  }

  def configureTable(root: VBox): Unit = {

    val menuBar = new MenuBar()

    val fileMenu = new Menu("File")

    val saveFileItem = new MenuItem("Save")

    saveFileItem.onAction = (event: ActionEvent) => {
      if (singleLInt != null) {
        val oos = new ObjectOutputStream(new FileOutputStream("IntLinkedList.txt"))
        oos.writeObject(singleLInt)
        oos.close()
      } else {
        val oos = new ObjectOutputStream(
          new FileOutputStream("StrLinkedList.txt"))
        oos.writeObject(singleLString)
        oos.close()
      }
    }

    val loadFileItem = new MenuItem("Load")

    loadFileItem.onAction = (event: ActionEvent) => {

      if (singleLInt != null) {
        val ois = new ObjectInputStream(new FileInputStream("IntLinkedList.txt"))
        singleLInt = ois.readObject.asInstanceOf[singleList[Int]]
        ois.close()

      }
      else {
        val ois = new ObjectInputStream(new FileInputStream("StrLinkedList.txt"))
        singleLString = ois.readObject.asInstanceOf[singleList[String]]
        ois.close()
      }

      updateTables()
    }

    fileMenu.items = List(saveFileItem, loadFileItem)

    val actionsMenu = new Menu("Actions")
    val addItem = new MenuItem("Add some elements")
    addItem.onAction = (event: ActionEvent) => {
      val dialog = new TextInputDialog(defaultValue = "number") {
        initOwner(stage)
        title = "Addition"
        headerText = "Number of elements"
        contentText = "Please enter the number of elements:"
      }

      val result = dialog.showAndWait()

      result match {
        case Some(name) =>
          if (singleLInt != null) {
            for(i <- 0 until name.toInt){
              singleLInt.addLast(integerBuilder.create())
            }
            //singleLInt.forEach(println)
          }
          else {
            for(i <- 0 until name.toInt){
              singleLString.addLast(stringBuilder.create())
            }
            //singleLString.forEach(println)
          }
        case None       =>
          new Alert(AlertType.Information) {
            initOwner(stage)
            title = "Information"
            headerText = "Сancel addition"
            contentText = "You canceled adding elements, so nothing will happen"
          }.showAndWait()
      }

      updateTables()
    }

    val deleteItem = new MenuItem("Delete element by index")
    deleteItem.onAction = (event: ActionEvent) => {
      val dialog = new TextInputDialog(defaultValue = "index") {
        initOwner(stage)
        title = "Deletion"
        headerText = "Index of element"
        contentText = "Please enter the index of the element:"
      }

      val result = dialog.showAndWait()

      result match {
        case Some(name) =>
          if (singleLInt != null) {
            singleLInt.delete(name.toInt)
          }
          else {
            singleLString.delete(name.toInt)
          }
        case None =>
          new Alert(AlertType.Information) {
            initOwner(stage)
            title = "Information"
            headerText = "Сancel deletion"
            contentText = "You have canceled the deletion of elements, so nothing will happen"
          }.showAndWait()
      }

      updateTables()
    }

    val sortItem = new MenuItem("Sort")
    sortItem.onAction = (event: ActionEvent) => {

      if(singleLInt!= null){
        if(singleLInt.getSize!=0) {
          var superL = new superList[Int]()

          var lastTime = System.currentTimeMillis
          singleLInt = superL.sort(singleLInt, integerBuilder.getComparator())
          val number = System.currentTimeMillis() - lastTime

          new Alert(AlertType.Information) {
            initOwner(stage)
            title = "Information"
            headerText = "Time"
            contentText = s"Sorting was done in ${number} milliseconds"
          }.showAndWait()
          updateTables()
        }
        else{
          new Alert(AlertType.Information) {
            initOwner(stage)
            title = "Information"
            headerText = "You have nothing to sorting"
            contentText = "Please ADD some elements"
          }.showAndWait()
        }
      }
      else if(singleLString!= null) {
        if(singleLString.getSize!=0) {
          var superL = new superList[String]()

          var lastTime = System.currentTimeMillis
          singleLString = superL.sort(singleLString, stringBuilder.getComparator())
          val number = System.currentTimeMillis() - lastTime

          new Alert(AlertType.Information) {
            initOwner(stage)
            title = "Information"
            headerText = "Time"
            contentText = s"Sorting was done in ${number} milliseconds"
          }.showAndWait()
          updateTables()
        }
        else{
          new Alert(AlertType.Information) {
            initOwner(stage)
            title = "Information"
            headerText = "You have nothing to sorting"
            contentText = "Please ADD some elements"
          }.showAndWait()
        }
      }
    }

    actionsMenu.items = List(addItem, deleteItem, sortItem)

    menuBar.menus = List(fileMenu, actionsMenu)

    val nodeData = ObservableBuffer(
      new NodeTableElement("\t-")
    )

    nodeTable = createNodeTableView(nodeData)
    nodeTable.columnResizePolicy = TableView.ConstrainedResizePolicy

    var label: Label = null
    if (singleLInt != null)
      label = new Label("\tInteger List Size: " + 0)
    else if (singleLString != null)
      label = new Label("\tString List Size: " + 0)

    root.children = Seq(
      menuBar,
      label,
      nodeTable
    )

  }

  def updateTables() = {
    var nodeData: ObservableBuffer[NodeTableElement] = null

    if (integerBuilder!=null) {
      var size = singleLInt.getSize
      for(i <- 0 until size){
        if (nodeData == null)
          nodeData = ObservableBuffer(
            new NodeTableElement(
              singleLInt.getElemByIndex(i).toString()
            )
          )
        else
          nodeData.addOne(
          new NodeTableElement(
            singleLInt.getElemByIndex(i).toString()
          )
        )
      }
    }
    else if (stringBuilder!=null) {
      var size = singleLString.getSize
      for(i <- 0 until size){
        if (nodeData == null)
          nodeData = ObservableBuffer(
            new NodeTableElement(
              singleLString.getElemByIndex(i).toString()
            )
          )
        else
          nodeData.addOne(
            new NodeTableElement(
              singleLString.getElemByIndex(i).toString()
            )
          )
      }
    }

    var label: Label = null
    if (singleLInt != null)
      label = new Label("\tInteger List Size: " + singleLInt.getSize)
    else if (singleLString != null)
      label = new Label("\tString List Size: " + singleLString.getSize)

    nodeTable = createNodeTableView(nodeData)
    nodeTable.columnResizePolicy = TableView.ConstrainedResizePolicy

    box.children.set(1, label)
    box.children.set(2, nodeTable)
  }

  def createNodeTableView(data: ObservableBuffer[NodeTableElement]): TableView[NodeTableElement] = {
    val table = new TableView[NodeTableElement] {
      columns ++= Seq(
        new TableColumn[NodeTableElement, String] {
          text = "Elements"
          prefWidth = 10
          cellValueFactory = _.value.nodeValue
        }
      )
      items = data
    }
    table
  }

}
