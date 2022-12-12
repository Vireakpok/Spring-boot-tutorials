package spring.boot.tutorials.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.boot.books.controller.RoleController;
import com.spring.boot.books.service.RoleService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import spring.boot.tutorials.constant.RoleConstant;

@WebMvcTest(
    value = RoleController.class,
    excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ContextConfiguration(classes = {RoleController.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RoleControllerTest {

  @MockBean
  RoleService roleService;

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @Test
  void whenSaveRoleValid_ThenResponseOk() throws Exception {
    MockHttpServletResponse response = mockMvc.perform(
            MockMvcRequestBuilders.post(RoleConstant.URL).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(RoleConstant.ROLE_DTO)))
        .andExpect(status().isOk())
        .andDo(print())
        .andReturn()
        .getResponse();
    assertNotNull(response);

  }

}
