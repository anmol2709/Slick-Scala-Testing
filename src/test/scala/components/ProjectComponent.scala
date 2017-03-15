import components.{ProjectComponent}
import models.{Employee, Project}
import org.scalatest.AsyncFunSuite
import providers.H2Provider

object ProjectComponent1 extends ProjectComponent with H2Provider

class ProjectComponentTest extends AsyncFunSuite {

  test("getting details") {
    ProjectComponent1.getAll().map(x => assert(x ==List(Project(1, "code-squad"), Project(2, "fit-files"))))
  }

  test("inserting project") {
    ProjectComponent1.insert(Project(12, "sql")).map(x => assert(x == 1))
  }

  test("deleting project by ID") {
    ProjectComponent1.deleteById(1).map(x => assert(x == 1))
  }

  test("deleting project by Name") {
    ProjectComponent1.deleteByName("fit-files").map(x => assert(x == 1))
  }


  test("updating project"){
    ProjectComponent1.updateName(1,"dbms").map(x => assert(x == 1))
  }

  test("fetching projects from id"){
    ProjectComponent1.projectsById(1).map(res=>assert(res==List(Project(1, "code-squad"))))
  }

  test("testing Plain sql"){
    ProjectComponent1.insertPlainSql.map(x=>assert(x==1))
  }

  test("testing joins with employee Table"){
    ProjectComponent1.projectsByEmployeeName.map(res=>assert(res==List(("anmol","code-squad"),("nitin","fit-files"))))
  }

  test("adding multiple projects") {
    ProjectComponent1.insertMultipleProjects(Project(8,"abc"), Project(9,"xyz")).map(x => assert(x == 1))
  }

}
