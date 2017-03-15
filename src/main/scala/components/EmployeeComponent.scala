package components


import models.{DependentTable, Employee, EmployeeTable}
import providers.{MySqlProvider, DBProvider, PostGresDBProvider}
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


trait EmployeeComponent extends EmployeeTable with DependentTable {

  this: DBProvider =>

  import driver.api._


  def create = db.run(employeeTableQuery.schema.create)

  def insert(employee: Employee) = db.run(employeeTableQuery += employee)


  // insert that returns id of inserted
  def insertAndReturnID(employee: Employee) = db.run({
    employeeTableQuery returning employeeTableQuery.map(_.id)} += employee)

  //insert that returns employee object of inserted
  def insertAndReturnEmployee(employee: Employee) = db.run({
    employeeTableQuery returning employeeTableQuery.map(x=>x)} += employee)

  def deleteById(id: Int) = {
    val query = employeeTableQuery.filter(x => x.id === id)
    db.run(query.delete)
  }

  def deleteByName(name: String)={
    val query = employeeTableQuery.filter(_.name === name)
      val action=query.delete
    db.run(action)
  }

  def deleteByExperience(exp: Double)={
    val query = employeeTableQuery.filter(_.experience === exp)
    val action=query.delete
    db.run(action)
  }

  def updateName(id: Int, name: String): Future[Int] = {
    val query = employeeTableQuery.filter(x => x.id === id)
      .map(_.name).update(name)
    db.run(query)
  }

  def updateExperience(id: Int, experience: Double): Future[Int] = {
    val query = employeeTableQuery.filter(_.id === id).map(_.experience).update(experience)
    db.run(query)
  }

  def updateEmployee(employee: Employee) = {
    employeeTableQuery.filter(x => x.id === employee.id)
      .map(x => (x.name, x.experience)).update((employee.name, employee.experience))
  }

  def employeeNameById(id:Int)={
    val query=(employeeTableQuery.filter(x=>x.id===id)).map(_.name).to[List].result
    db.run(query)
  }

  def getAll(): Future[List[Employee]] = {
    db.run {
      employeeTableQuery.to[List].result
    }
  }

  def deleteAll = {
    db.run(employeeTableQuery.delete)

  }

  def insertOrUpdate(employee: Employee) = {
    val query = employeeTableQuery.insertOrUpdate(employee)
    db.run(query)

  }

  //multiple actions //action composition
  def addMultiple(emp1: Employee, emp2: Employee) = {
    val action1: DBIO[Int] = employeeTableQuery += emp1
    val action2: DBIO[Int] = employeeTableQuery += emp2
    //    val action:DBIO[(Int,Int)]=action1 zip action2
    val action: DBIO[(Int)] = action1 andThen action2

    db.run(action.cleanUp{
      case None=>action1 //when exception call this
      case _ => action1
    })
  }


  //using join on dependents and employee
  def namesOfDependents = {
    val action = {
      for {
        (e, d) <- employeeTableQuery join dependentTableQuery on (_.id === _.emp_id)
      } yield (e.name, d.name)
    }.to[List].result
    db.run(action)
  }

  //Plain Sql
  def insertNew = {
    val action = sqlu"insert into employee values(102, 'anmol', 5);"
db.run(action)

  }
}


object EmployeeComponent extends EmployeeComponent with PostGresDBProvider    //change here to change the database(mySql or Postgres)