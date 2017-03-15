package components


import models.{Dependent, Employee, EmployeeTable}
import org.scalatest.AsyncFunSuite
import providers.{H2Provider, PostGresDBProvider}
import slick.lifted.TableQuery

object DependentComponent1 extends DependentComponent with H2Provider

class DependentComponentTest extends AsyncFunSuite {

  test("getting details"){
    DependentComponent1.getAll().map(x=>assert(x==List(Dependent(1,"karan","brother",Some(23)),Dependent(2,"neha","sister",Some(23)))))
  }

  test("inserting dependent") {
    DependentComponent1.insert(Dependent(12,"karan","brother",Some(23))).map(x => assert(x == 1))
  }

  test("deleting dependent by ID") {
    DependentComponent1.deleteById(1).map(x => assert(x == 1))
  }


  test("deleting dependent by Name") {
    DependentComponent1.deleteByName("neha").map(x => assert(x == 1))
  }

  test("updating dependent Name"){
    DependentComponent1.updateName(1,"Anil").map(x => assert(x == 1))
  }

  test("updating dependent Age"){
    DependentComponent1.updateAge(1,Some(18)).map(x => assert(x == 1))
  }

  test("getting dependents name by id"){
    DependentComponent1.dependentNamesById(1).map(x=>(assert(x==List("karan"))))
  }

  test("getting dependents by id"){
    DependentComponent1.dependentsById(1).map(x=>(assert(x==List(Dependent(1,"karan","brother",Some(23))))))
  }
  test("updating dependent Relation"){
    DependentComponent1.updateRelation(1,"Brother").map(x => assert(x == 1))
  }



  test("adding multiple dependents") {
    DependentComponent1.addMultiple(Dependent(2,"ABC","brother",Some(12)),Dependent(2,"xyz","sister",Some(15))).map(x => assert(x == 1))
  }

  test("testing Plain sql"){
    DependentComponent1.insertPlainSql.map(x=>assert(x==1))
  }



  test("testing joins"){
    DependentComponent1.dependentsOfEmployees.map(res=>assert(res==List(("anmol","karan"),("nitin","neha"))))
  }

}
