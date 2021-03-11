package email.course.springboottestproject;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void shouldReturnAllUsersForUnauthenticatedUsers() throws Exception {
        when(userService.getAllUsers())
                .thenReturn(Collections.singletonList(new User("duke", "duke@spring.io")));

        this.mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].username").value("duke"))
                .andExpect(jsonPath("$[0].email").value("duke@spring.io"));
    }

    @Test
    void shouldAllowCreationForUnauthenticatedUsers() throws Exception {
        this.mockMvc.perform(
                post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"duke\", \"email\":\"duke@spring.io\"}")
                .with(csrf())
        )
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", Matchers.containsString("duke")));
        verify(userService).storeNewUser(any(User.class));
    }
}