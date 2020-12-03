package com.takeaway.challenge.command;

import java.time.LocalDate;
import java.util.UUID;

public class EmployeeCommand {

    private UUID uuid;

    private String mailAddress;

    private String firstName;

    private String lastName;

    private LocalDate birthday;

    private DepartmentCommand departmentCommand;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public DepartmentCommand getDepartmentCommand() {
        return departmentCommand;
    }

    public void setDepartmentCommand(DepartmentCommand departmentCommand) {
        this.departmentCommand = departmentCommand;
    }
}
