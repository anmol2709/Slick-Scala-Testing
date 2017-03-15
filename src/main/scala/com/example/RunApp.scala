//package com.example
//
//import components.{DependentComponent, ProjectComponent, EmployeeComponent}
//import models.{Project, Dependent, Employee}
//import scala.concurrent.{Await, Future}
//import scala.concurrent.duration._
//import scala.concurrent.ExecutionContext.Implicits.global
//object RunApp extends App {



//
//  //EMPLOYEE TABLE ENTRIES
////val insertRes=EmployeeComponent.insertNew
////
//
//     val insertRes= EmployeeComponent.insert(Employee(90,"anmol",2D))
////
//  val res = insertRes.map { res => s"$res" }.recover {
//   case ex: Throwable => ex.getMessage
//   }
//
//val resFinal=Await.result(res,15 seconds)
//  print(resFinal)
////    EmployeeComponent.create
////    EmployeeComponent.insert(Employee(1,"anmol",2D))
////    EmployeeComponent.insert(Employee(6,"abcd",3D))
////    EmployeeComponent.insert(Employee(9,"abc",2D))
////    EmployeeComponent.insertOrUpdate(Employee(6, "anmol", 5D))
////    EmployeeComponent.updateName(9,"anmol")
//////  EmployeeComponent.delete(9)
////  EmployeeComponent.addMultiple(Employee(107,"a",2D),Employee(101,"b",5D))   //new
////val dependentNames=EmployeeComponent.namesOfDependents
//
//  //  //PROJECT TABLE ENTRIES
////
////    ProjectComponent.create
////    ProjectComponent.insert(Project(1,"p3"))
////    ProjectComponent.insert(Project(1,"p2"))
////    ProjectComponent.insert(Project(9,"p1"))
////    //ProjectComponent.deleteAll
////
////  //DEPENDENT TABLE ENTRIES
////
////      DependentComponent.create
////      DependentComponent.insert(Dependent(1,"abc","brother"))
////      DependentComponent.insert(Dependent(1,"abcd","sister"))
//
//
/////*  val listOfEmployees=EmployeeComponent.getAll()
////  val listOfProjects=ProjectComponent.getAll()
////  val listOfDependents=DependentComponent.getAll()
////  val dependentsOnEmployee=DependentComponent.dependentsById(1)*/
////  val projectsOfEmployee=Await.result(ProjectComponent.projectsById(1),5 seconds)
////
//////
/////*
////    println(listOfEmployees)
////    println(listOfProjects)
////    println(listOfDependents)
////    println(dependentsOnEmployee)*/
////    println(projectsOfEmployee)
//////    println(dependentNames)
//
//  Thread.sleep(5000)
//}
