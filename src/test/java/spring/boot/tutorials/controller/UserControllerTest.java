package spring.boot.tutorials.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.boot.books.controller.UserController;
import com.spring.boot.books.dto.UserDetailsDTO;
import com.spring.boot.books.service.UserService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import spring.boot.tutorials.constant.UserConstant;

@WebMvcTest(
    value = UserController.class,
    excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ContextConfiguration(classes = {UserController.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
class UserControllerTest {

  @MockBean
  UserService userService;
  @Autowired
  MockMvc mockMvc;
  @Autowired
  ObjectMapper objectMapper;

  @Test
  @Order(1)
  void whenSaveUserNameUnValid_thenReturnBadRequest() throws Exception {
    when(userService.saveUser(any(UserDetailsDTO.class))).thenReturn(
        UserConstant.USER_NAME_UN_VALID);
    this.mockMvc.perform(MockMvcRequestBuilders.post(UserConstant.URL)
            .content(objectMapper.writeValueAsString(UserConstant.USER_NAME_UN_VALID))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andDo(print())
        .andReturn()
        .getResponse();
  }

  @Test
  @Order(2)
  void whenSaveUserUnValidEmail_thenReturnBadRequest() throws Exception {
    when(userService.saveUser(any(UserDetailsDTO.class))).thenCallRealMethod()
        .thenReturn(UserConstant.USER_EMAIL_UN_VALID);
    this.mockMvc.perform(MockMvcRequestBuilders.post(UserConstant.URL)
            .content(objectMapper.writeValueAsString(UserConstant.USER_EMAIL_UN_VALID))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andDo(print())
        .andReturn()
        .getResponse();
  }

  @Test
  @Order(3)
  void whenSaveUserUnValidPassword_thenReturnBadRequest() throws Exception {
    when(userService.saveUser(any(UserDetailsDTO.class))).thenCallRealMethod()
        .thenReturn(UserConstant.USER_PASSWORD_UN_VALID);
    this.mockMvc.perform(MockMvcRequestBuilders.post(UserConstant.URL)
            .content(objectMapper.writeValueAsString(UserConstant.USER_PASSWORD_UN_VALID))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andDo(print())
        .andReturn()
        .getResponse();
  }

  @Test
  @Order(4)
  void whenSaveUser_thenReturnCurrentUserSave() throws Exception {
    when(userService.saveUser(any(UserDetailsDTO.class))).thenReturn(UserConstant.MOCK_USER);
    this.mockMvc.perform(MockMvcRequestBuilders.post(UserConstant.URL)
            .content(objectMapper.writeValueAsString(UserConstant.MOCK_USER))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.jsonPath("$.name")
            .value(UserConstant.MOCK_USER.getName()))
        .andDo(print())
        .andReturn()
        .getResponse();
  }

  @Test
  @Order(5)
  void whenGetAllUsers_thenReturnUserDetails() throws Exception {

    when(userService.getAllUsers()).thenReturn(List.of(UserConstant.MOCK_USER));
    this.mockMvc.perform(
            MockMvcRequestBuilders.get(UserConstant.URL).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].roles[0].name").isNotEmpty())
        .andDo(print())
        .andReturn()
        .getResponse();
  }
}
