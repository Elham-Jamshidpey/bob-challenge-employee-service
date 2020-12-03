package com.takeaway.challenge.command;

public class DepartmentCommand   {

  private String name;

  public DepartmentCommand(String name){
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public boolean validate(){
    if(this.name == null || this.name.isEmpty()){
      return false;
    }
    return true;
  }
}

