package cz.javadays.feedback;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Testy HTTP vrstvy bez startu celé aplikace (MockMvc v režimu standalone).
 */
class RatingControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new RatingController(new RatingService()))
                .build();
    }

    @Test
    @DisplayName("POST s platným hodnocením vrátí 201 a aktuální souhrn")
    void validRatingReturnsCreated() throws Exception {
        mockMvc.perform(post("/api/talks/copilot/ratings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"stars\": 5}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.talkId").value("copilot"))
                .andExpect(jsonPath("$.count").value(1))
                .andExpect(jsonPath("$.average").value(5.0));
    }

    @Test
    @DisplayName("POST s hodnocením mimo 1–5 vrátí 400 a chybovou zprávu")
    void invalidRatingReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/talks/copilot/ratings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"stars\": 7}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    @DisplayName("GET pro přednášku bez hodnocení vrátí počet 0")
    void summaryOfUnratedTalk() throws Exception {
        mockMvc.perform(get("/api/talks/nova-prednaska/ratings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(0))
                .andExpect(jsonPath("$.average").value(0.0));
    }
}
