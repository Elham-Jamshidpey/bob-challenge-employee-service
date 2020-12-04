package com.takeaway.challenge.command;

import org.springframework.util.StringUtils;

public class DepartmentCommand   {

  private String name;

  public DepartmentCommand(String name){
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public boolean isValid(){
    return !StringUtils.isEmpty(this.name);
  }
}

