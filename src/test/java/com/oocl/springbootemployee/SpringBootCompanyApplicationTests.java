package com.oocl.springbootemployee;

import com.oocl.springbootemployee.enums.Gender;
import com.oocl.springbootemployee.model.Company;
import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.repository.CompanyRepository;
import com.oocl.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class SpringBootCompanyApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    CompanyRepository companyRepository;
    List<Company> companies = companyRepository.getAll();

    @BeforeEach
    public void setUp() {
        companies.clear();
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1,"a",25, Gender.MALE,5000.0));
        companies.add(new Company(1, "A",employees));
    }

    @Test
    void should_return_companies_when_get_all_given_companies() throws Exception {
        //Given

        //When

        //Then
        mockMvc.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(companies.get(0).getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[0].name").value(companies.get(0).getEmployees().get(0).getName()));
    }
}
