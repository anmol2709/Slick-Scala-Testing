package models

import providers.DBProvider

trait EmployeeTable {
  this:DBProvider =>
  import driver.api._

  class EmployeeTable(tag: Tag) extends Table[Employee](tag, "employee") {
    val id = column[Int]("id", O.PrimaryKey)
    val name = column[String]("name")
    val experience = column[Double]("experience")

    def * = (id, name, experience) <>(Employee.tupled, Employee.unapply)

  }
  def employeeTableQuery = TableQuery[EmployeeTable]


  def employeeTableAutoInc = employeeTableQuery returning employeeTableQuery.map(_.id)

}

case class Employee(id: Int, name: String, experience: Double)