package components

import models.{Employee, Project, ProjectTable}
import providers.{PostGresDBProvider, DBProvider,MySqlProvider}
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

trait ProjectComponent extends ProjectTable {
  this: DBProvider =>

  import driver.api._

  def create = db.run(projectTableQuery.schema.create)

  def insert(project: Project) = db.run(projectTableQuery += project)

  def deleteById(id: Int) = {
    val query = projectTableQuery.filter(x => x.emp_id === id)
    db.run(query.delete)
  }

  def deleteByName(name: String) = {
    val query = projectTableQuery.filter(x => x.name === name)
    db.run(query.delete)
  }

  def updateName(id: Int, name: String): Future[Int] = {
    val query = projectTableQuery.filter(x => x.emp_id === id)
      .map(_.name).update(name)
    db.run(query)
  }

def updateIdForProject(name:String,id:Int)={
  val query = projectTableQuery.filter(x => x.name === name)
    .map(_.emp_id).update(id)
  db.run(query)
}

  def insertOrUpdate(project: Project) = {
    val query = projectTableQuery.insertOrUpdate(project)
    db.run(query)
  }

  def getAll(): Future[List[Project]] = {
    db.run {
      projectTableQuery.to[List].result
    }
  }

  def deleteAll = {
    db.run(projectTableQuery.delete)
  }

  def insertMultipleProjects(project1: Project,project2: Project) ={
    val action1: DBIO[Int] = projectTableQuery += project1
    val action2: DBIO[Int] = projectTableQuery += project2
    //    val action:DBIO[(Int,Int)]=action1 zip action2
    val action: DBIO[(Int)] = action1 andThen action2

    db.run(action.cleanUp{
      case None=>action1 //when exception call this
      case _ => action1
    })

  }

  def projectsByEmployeeName = {
    val action = {
      for {
        (e, p) <- employeeTableQuery join projectTableQuery on (_.id === _.emp_id)
      } yield (e.name, p.name)
    }.to[List].result
    db.run(action)
  }

  def projectsWithMinimumExperience(exp:Double)={
    val query = {
      for {
        (e, p) <- employeeTableQuery join projectTableQuery on ((e,p)=>e.id === p.emp_id)
      } yield (e.name,e.experience,p.name)
    }.filter(x=>x._2>exp)
      val action =query.to[List].result
    db.run(action)
  }


  def peopleWorkingOnProject(name:String)={
    val query = {
      for {
        (e, p) <- employeeTableQuery join projectTableQuery on ((e,p)=>e.id === p.emp_id)
      } yield (e.name,e.experience,p.name)
    }.filter(x=>x._3===name)
    val action =query.to[List].result
    db.run(action)
  }

  def projectsById(id: Int): Future[List[Project]] = {
    val query = projectTableQuery.filter(x => x.emp_id === id).to[List].result
    db.run(query)
  }


  def insertPlainSql = {
    val action = sqlu"insert into project values(102, 'abc');"
    db.run(action)

  }


}
object ProjectComponent extends ProjectComponent  with PostGresDBProvider    //change here to change the database(mySql or Postgres)