package com.oocl.springbootemployee;

import com.oocl.springbootemployee.enums.Gender;
import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SpringBootEmployeeApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    EmployeeRepository employeeRepository;
    List<Employee> employees = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        employees.clear();
        employees.add(new Employee(1, "a", 20, Gender.MALE, 5000.0));
        employees.add(new Employee(2, "b", 20, Gender.MALE, 5000.0));
        employees.add(new Employee(3, "c", 20, Gender.FEMALE, 5000.0));
    }

    @Test
    @Order(1)
    void should_return_employees_when_get_all_given_employees() throws Exception {
        //Given

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
    @Order(2)
    void should_return_employee_when_get_by_id_given_id() throws Exception {
        //Given
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
    @Order(3)
    void should_return_employees_when_get_by_gender_given_MALE() throws Exception {
        //Given
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

    @Test
    @Order(4)
    void should_save_employee_when_create_employee_given_employee() throws Exception {
        //Given
        String givenEmployee = "{\n" +
                "    \"name\": \"d\",\n" +
                "    \"age\": 20,\n" +
                "    \"gender\": \"MALE\",\n" +
                "    \"salary\": 5000.0\n" +
                "}";

        //When

        //Then
        mockMvc.perform(MockMvcRequestBuilders.post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(givenEmployee))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("4"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("d"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(20))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("MALE"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(5000.0));
    }

    @Test
    @Order(5)
    void should_update_employee_age_and_salary_when_update_employee_given_age_and_salary() throws Exception {
        //Given

        String givenEmployee = "{\n" +
                "    \"age\": 30,\n" +
                "    \"salary\": 8000.0\n" +
                "}";

        int id = 1;

        //When

        //Then
        mockMvc.perform(MockMvcRequestBuilders.put("/employees/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(givenEmployee))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("a"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(30))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("MALE"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(8000.0));
    }

    @Test
    @Order(6)
    void should_return_null_when_delete_given_id() throws Exception {
        //Given
        int id = 2;
        //When

        //Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/employees/" + id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        assertEquals(employeeRepository.getAll().size(),3);
    }
}
