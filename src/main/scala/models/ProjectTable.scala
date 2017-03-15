package models

import providers.{DBProvider, PostGresDBProvider}

trait ProjectTable extends EmployeeTable {
  this:DBProvider =>
  import driver.api._
  class ProjectTable(tag: Tag) extends Table[Project](tag, "project") {
    val name = column[String]("name")
    val emp_id = column[Int]("emp_id")
    def empForeignKey = foreignKey("emp_project_fk", emp_id, employeeTableQuery)(_.id)
    def * = (emp_id, name) <>(Project.tupled, Project.unapply)
  }

  val projectTableQuery = TableQuery[ProjectTable]

}

case class Project(emp_id:Int, name: String)