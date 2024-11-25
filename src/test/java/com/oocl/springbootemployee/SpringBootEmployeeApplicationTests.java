package com.oocl.springbootemployee;

import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;


@SpringBootTest
@AutoConfigureMockMvc
class SpringBootEmployeeApplicationTests {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MockMvc mockMvc;
	@Test
	void contextLoads() {
	}

    @Test
    void should_return_employees_when_get_all_given_employees() throws Exception {
        //Given
        List<Employee> employees = employeeRepository.getAll();

        //When

        //Then
        mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(employees.get(0).getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(employees.get(1).getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name").value(employees.get(2).getName()));
    }


    @Test
    void should_return_employee_when_get_by_id_given_id() throws Exception {
        //Given
        List<Employee> employees = employeeRepository.getAll();
        Integer id = employees.get(0).getId();

        //When

        //Then
        mockMvc.perform(MockMvcRequestBuilders.get("/employees/" + id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(employees.get(0).getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(employees.get(0).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(employees.get(0).getAge()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value(employees.get(0).getGender().name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(employees.get(0).getSalary()));
    }

    @Test
    void should_return_employees_when_get_by_gender_given_MALE() throws Exception {
        //Given
        List<Employee> employees = employeeRepository.getAll();
        String gender = "MALE";

        //When

        //Then
        mockMvc.perform(MockMvcRequestBuilders.get("/employees")
                        .param("gender", gender))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(employees.get(0).getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(employees.get(1).getName()));
    }
}
