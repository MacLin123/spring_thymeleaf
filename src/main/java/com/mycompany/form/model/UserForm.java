package com.mycompany.form.model;

import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
/**
 * This class represents form data of user entity.
 */
public class UserForm {

    @Pattern(regexp = "[A-Za-z]{1,100}", message = "first name should contains from 1 to 100  alphabet characters")
    private String firstName;

    @Pattern(regexp = "[A-Za-z]{1,100}", message = "first name should contains from 1 to 100  alphabet characters")
    private String lastName;

    @Pattern(regexp = "[A-Za-z]{1,100}", message = "first name should contains from 1 to 100  alphabet characters")
    private String middleName;

    @NotNull
    @Range(max = 300, min = 0)
    private Integer age;

    @NotNull
    @Min(value = 0)
    private Double salary;

    @NotEmpty
    @Email
    private String email;


    @Pattern(regexp = "\\w.*", message = "company name should starts from alphabet character")
    private String company;

    private MultipartFile file;

    public UserForm() {

    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "UserForm{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                ", email='" + email + '\'' +
                ", company='" + company + '\'' +
                '}';
    }
}
