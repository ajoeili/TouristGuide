package tourism.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import tourism.model.Tag;
import tourism.model.TouristAttraction;
import tourism.service.TouristService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TouristController.class)
public class TouristControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TouristService touristService;

    @InjectMocks
    private TouristController touristController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTouristAttractions() throws Exception {
        List<TouristAttraction> attractions = Arrays.asList(
                new TouristAttraction("SMK", "Museum of art", "Copenhagen", List.of(Tag.ART)),
                new TouristAttraction("Tivoli", "Amusement park", "Copenhagen", List.of(Tag.FAMILY_FRIENDLY))
        );

        when(touristService.getTouristAttractions()).thenReturn(attractions);

        mockMvc.perform(get("/attractions")
                .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("touristAttractions"))
                .andExpect(view().name("attractionsList"));
    }

    @Test
    public void testGetTouristAttractionByName() throws Exception {
        TouristAttraction attraction = new TouristAttraction("SMK", "Museum of art", "Copenhagen", List.of(Tag.ART));
        when(touristService.findAttractionByName("SMK", null)).thenReturn(attraction);

        mockMvc.perform(get("/attractions/SMK"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("SMK"))
                .andExpect(jsonPath("$.description").value("Museum of art"));
    }

    @Test
    public void testShowAddTouristAttractionForm() throws Exception {
        when(touristService.getCities()).thenReturn(Arrays.asList("Copenhagen", "Paris"));
        when(touristService.getTags()).thenReturn(List.of(Tag.ART, Tag.CULTURE));

        mockMvc.perform(get("/attractions/add"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("touristAttraction"))
                .andExpect(model().attributeExists("locations"))
                .andExpect(model().attributeExists("tags"))
                .andExpect(view().name("addTouristAttractionForm"));
    }

    @Test
    public void testShowEditTouristAttractionForm() throws Exception {
        TouristAttraction attraction = new TouristAttraction("SMK", "Museum of art", "Copenhagen", List.of(Tag.ART));
        when(touristService.findAttractionByName("SMK", null)).thenReturn(attraction);
        when(touristService.getCities()).thenReturn(Arrays.asList("Copenhagen", "Paris"));
        when(touristService.getTags()).thenReturn(List.of(Tag.ART, Tag.CULTURE));

        mockMvc.perform(get("/attractions/edit/SMK"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("attraction"))
                .andExpect(model().attributeExists("locations"))
                .andExpect(model().attributeExists("tags"))
                .andExpect(view().name("editTouristAttractionForm"));
    }

    @Test
    public void testUpdateTouristAttraction() throws Exception {
        TouristAttraction attraction = new TouristAttraction("SMK", "Museum of art", "Copenhagen", List.of(Tag.ART));

        mockMvc.perform(post("/attractions/update")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "SMK")
                        .param("description", "Museum of art")
                        .param("location", "Copenhagen"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/attractions"));

        verify(touristService).updateTouristAttraction(any(TouristAttraction.class)); // Verify service interaction
    }

    @Test
    public void testSaveTouristAttraction() throws Exception {
        TouristAttraction attraction = new TouristAttraction("SMK", "Museum of art", "Copenhagen", List.of(Tag.ART));

        mockMvc.perform(post("/attractions/save")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "SMK")
                        .param("description", "Museum of art")
                        .param("location", "Copenhagen"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/attractions"));

        verify(touristService).addTouristAttraction(any(TouristAttraction.class)); // Verify service interaction
    }

    @Test
    public void testDeleteTouristAttraction() throws Exception {
        when(touristService.deleteAttractionByName("SMK")).thenReturn(true);

        mockMvc.perform(post("/attractions/delete/SMK"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/attractions"));

        verify(touristService).deleteAttractionByName("SMK"); // Verify service interaction
    }

}
