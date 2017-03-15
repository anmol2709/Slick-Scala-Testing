package components


import models.{Employee, EmployeeTable}
import org.scalatest.AsyncFunSuite
import providers.{H2Provider, PostGresDBProvider}
import slick.lifted.TableQuery

object EmployeeComponent1 extends EmployeeComponent with H2Provider

class EmployeeComponentTest extends AsyncFunSuite {

test("getting details"){
  EmployeeComponent1.getAll().map(x=>assert(x==List(Employee(1,"anmol",5),Employee(2,"nitin",5))))
}

  test("inserting employee") {
    EmployeeComponent1.insert(Employee(15, "abc", 6D)).map(x => assert(x == 1))
  }

  test("deleting employee by ID") {
    EmployeeComponent1.deleteById(1).map(x => assert(x == 1))
  }


  test("deleting employee by Name") {
    EmployeeComponent1.deleteByName("anmol").map(x => assert(x == 1))
  }

  test("getting name by id"){
    EmployeeComponent1.employeeNameById(2).map(x=>assert(x==List("nitin")))
  }

  test("deleting employee by Experience") {
    EmployeeComponent1.deleteByExperience(5D).map(x => assert(x == 2))  //deletes both as both have experience 5
  }

  test("updating employee Name"){
    EmployeeComponent1.updateName(1,"Karan").map(x => assert(x == 1))
  }

  test("updating employee Experience"){
    EmployeeComponent1.updateExperience(1,3D).map(x => assert(x == 1))
  }

  test("updating or inserting employee"){
    EmployeeComponent1.insertOrUpdate(Employee(8, "abc", 6D)).map(x => assert(x == 1))
  }


  test("adding 2 employees") {
    EmployeeComponent1.addMultiple(Employee(21, "abc", 6D), Employee(22, "xyz", 7D)).map(x => assert(x == 1))
  }

test("testing Plain sql"){
  EmployeeComponent1.insertNew.map(x=>assert(x==1))
}



test("testing joins"){
    EmployeeComponent1.namesOfDependents.map(res=>assert(res==List(("anmol","karan"),("nitin","neha"))))
  }




}