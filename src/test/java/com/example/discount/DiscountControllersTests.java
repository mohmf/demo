package com.example.discount;

import com.example.discount.controller.UserController;
import com.example.discount.enums.UserType;
import com.example.discount.model.User;
import com.example.discount.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {UserController.class})
class DiscountControllersTests {
    public static final String USERNAME = "user";
    public static final String PASSWORD = "123";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void should_return_401_without_auth() throws Exception {
        mockMvc.perform(get("/user/111")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().is(401));
    }

    @Test
    @WithMockUser(username = "user", password = "123")
    public void should_return_200_with_auth() throws Exception {

        User user = new User("1234", "Mohammed", UserType.EMPLOYEE, 2);
        when(userService.findById("1234")).thenReturn(user);

        mockMvc.perform(get("/user/1234")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @Test
    @WithMockUser(username = "user", password = "123")
    public void test_create_user() throws Exception {
        User user = new User("1234", "Mohammed", UserType.EMPLOYEE, 2);
        when(userService.create(user)).thenReturn(user);

        mockMvc.perform(post("/user")
                        .with(csrf())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Mohammed"))
                .andExpect(jsonPath("$.userType").value("EMPLOYEE"))
                .andExpect(jsonPath("$.durationInYears").value(2));
    }
}