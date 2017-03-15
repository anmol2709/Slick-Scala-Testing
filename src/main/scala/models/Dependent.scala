package models

import providers.{DBProvider, PostGresDBProvider}



trait DependentTable extends EmployeeTable{
  this:DBProvider =>
  import driver.api._

  class DependentTable(tag: Tag) extends Table[Dependent](tag, "dependent") {
    val emp_id = column[Int]("emp_id")
    val name = column[String]("name")
    val relation=column[String]("relation")
    val age=column[Option[Int]]("age")

    def depForeignKey = foreignKey("emp_dependent_fk", emp_id, employeeTableQuery)(_.id)


    def * = (emp_id, name, relation,age) <>(Dependent.tupled, Dependent.unapply)

  }
  def dependentTableQuery = TableQuery[DependentTable]

}

case class Dependent(emp_id:Int,name:String,relation:String,age:Option[Int]=None)
