package com.oocl.springbootemployee.repository;

import com.oocl.springbootemployee.enums.Gender;
import com.oocl.springbootemployee.model.Company;
import com.oocl.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyRepository {
    List<Company> companies;

    public CompanyRepository() {
        companies = new ArrayList<>();
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1,"a",25, Gender.MALE,5000.0));
        companies.add(new Company(1, "A",employees));
    }

    public List<Company> getAll(){
        return companies;
    }
}
