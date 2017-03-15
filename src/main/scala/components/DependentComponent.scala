package components

import components.ProjectComponent._
import models._
import providers.{PostGresDBProvider, DBProvider,MySqlProvider}
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

trait DependentComponent extends DependentTable{
  this: DBProvider =>
  import driver.api._

  def create = db.run(dependentTableQuery.schema.create)

  def insert(dependent: Dependent) = db.run(dependentTableQuery += dependent)

  def deleteById(id: Int) = {
    val query = dependentTableQuery.filter(x => x.emp_id === id)
    db.run(query.delete)
  }

  def deleteByName(name: String) = {
    val query = dependentTableQuery.filter(x => x.name === name)
    db.run(query.delete)
  }

  def updateName(id: Int, name: String): Future[Int] = {
    val query = dependentTableQuery.filter(x => x.emp_id === id)
      .map(_.name).update(name)
    db.run(query)
  }


  def updateAge(id: Int, age: Option[Int]): Future[Int] = {
    val query = dependentTableQuery.filter(x => x.emp_id === id)
      .map(_.age).update(age)
    db.run(query)
  }


  def updateRelation(id: Int, relation: String): Future[Int] = {
    val query = dependentTableQuery.filter(x => x.emp_id === id)
      .map(_.relation).update(relation)
    db.run(query)
  }

  def deleteAll = {
    db.run(dependentTableQuery.delete)
  }

  def getAll(): Future[List[Dependent]] ={
    db.run { dependentTableQuery.to[List].result }
  }

  def insertOrUpdate(dependent: Dependent) = {
    val query = dependentTableQuery.insertOrUpdate(dependent)
    db.run(query)
  }


  //Real Life Operation for Dependents of a Particular Employee
   def dependentsById(id:Int):Future[List[Dependent]] ={
     val query=dependentTableQuery.filter(x=>x.emp_id===id).to[List].result
   db.run(query)
  }

  def dependentNamesById(id:Int):Future[List[String]] ={
    val query=dependentTableQuery.filter(x=>x.emp_id===id).map(_.name).to[List].result
    db.run(query)
  }

  def addMultiple(dep1: Dependent, dep2: Dependent) = {
    val action1: DBIO[Int] = dependentTableQuery += dep1
    val action2: DBIO[Int] = dependentTableQuery += dep2
    //    val action:DBIO[(Int,Int)]=action1 zip action2
    val action: DBIO[(Int)] = action1 andThen action2

    db.run(action.cleanUp{
      case None=>action1 //when exception call this
      case _ => action1
    })
  }

  def insertPlainSql = {
    val action = sqlu"insert into dependent values(102, 'abc','sister',23);"
    db.run(action)

  }

  def dependentsOfEmployees = {
    val action = {
      for {
        (d, e) <- dependentTableQuery join employeeTableQuery  on (_.emp_id === _.id)
      } yield (e.name, d.name)
    }.to[List].result
    db.run(action)
  }
}

object DependentComponent extends DependentComponent  with PostGresDBProvider    //change here to change the database(mySql or Postgres)