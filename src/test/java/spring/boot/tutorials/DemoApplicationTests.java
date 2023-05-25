package spring.boot.tutorials;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(classes = DemoApplicationTests.class)
class DemoApplicationTests {

  @Test
  void contextLoads() {
    final int actual = 2;
    final int expected = 2;
    assertEquals(actual, expected);
  }

}
