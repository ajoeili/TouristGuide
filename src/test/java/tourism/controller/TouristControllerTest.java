package tourism.controller;

import net.bytebuddy.agent.VirtualMachine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import tourism.model.Tag;
import tourism.model.TouristAttraction;
import tourism.service.TouristService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TouristControllerTest {

    @InjectMocks
    private TouristController touristController;

    @Mock
    private TouristService touristService;

    @Mock
    private Model model;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTouristAttractions() {
        List<TouristAttraction> attractions = new ArrayList<>();
        attractions.add(new TouristAttraction("SMK", "Museum of art", "Copenhagen", List.of(Tag.ART)));

        when(touristService.getTouristAttractions()).thenReturn(attractions);

        String viewName = touristController.getTouristAttractions(model);

        assertEquals("attractionsList", viewName);
        verify(model).addAttribute("touristAttractions", attractions);
    }

    @Test
    public void testGetTouristAttractionByName() {
        TouristAttraction attraction = new TouristAttraction("SMK", "Museum of art", "Copenhagen", List.of(Tag.ART));
        when(touristService.findAttractionByName("SMK", null)).thenReturn(attraction);

        ResponseEntity<TouristAttraction> response = touristController.getTouristAttractionByName("SMK", null);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("attraction", response.getBody());
    }

    @Test
    public void testShowAddTouristAttractionForm() {
        String viewName = touristController.showAddTouristAttractionForm(model);

        assertEquals("addTouristAttractionForm", viewName);
        verify(model).addAttribute(eq("touristAttraction"), any(TouristAttraction.class));
        verify(model).addAttribute(eq("locations"), anyList());
        verify(model).addAttribute(eq("tags"), anyList());
    }

    @Test
    public void testShowTagsPage() {
        String attractionName = "SMK";
        TouristAttraction attraction = new TouristAttraction(attractionName, "Museum of art", "Copenhagen", List.of(Tag.ART));
        when(touristService.findAttractionByName(attractionName,null)).thenReturn(attraction);

        String viewName = touristController.showTagsPage(attractionName, model);

        assertEquals("tagsList", viewName);
        verify(model).addAttribute("attraction", attraction);
    }

    @Test
    public void testShowEditTouristAttractionForm() {
        String attractionName = "SMK";
        TouristAttraction attraction = new TouristAttraction(attractionName, "Museum of art", "Copenhagen", List.of(Tag.ART));
        when(touristService.findAttractionByName(attractionName,null)).thenReturn(attraction);

        String viewName = touristController.showEditTouristAttractionForm(attractionName, model);

        assertEquals("editTouristAttractionForm", viewName);
        verify(model).addAttribute("attraction", attraction);
        verify(model).addAttribute("locations", anyList());
        verify(model).addAttribute(eq("tags"), anyList());
    }

    @Test
    public void testUpdateTouristAttraction() {
        TouristAttraction attraction = new TouristAttraction("SMK", "Updated description", "Copenhagen", List.of(Tag.CULTURE));
        String viewName = touristController.updateTouristAttraction(attraction);

        assertEquals("redirect:/attractions", viewName);
        verify(touristService).updateTouristAttraction(attraction);
    }

    @Test
    public void testSaveTouristAttraction() {
        TouristAttraction attraction = new TouristAttraction("New Attraction", "Description", "City", List.of(Tag.ART));
        String viewName = touristController.saveTouristAttraction(attraction);

        assertEquals("redirect:/attractions", viewName);
        verify(touristService).addTouristAttraction(attraction);
    }

    @Test
    public void testDeleteTouristAttraction() {
        String attractionName = "SMK";
        when(touristService.deleteAttractionByName(attractionName)).thenReturn(true);

        String viewName = touristController.deleteTouristAttraction(attractionName);

        assertEquals("redirect:/attractions", viewName);
        verify(touristService).deleteAttractionByName(attractionName);
    }

    @Test
    public void testDeleteTouristAttraction_NotFound() {
        String attractionName = "NonExistentAttraction";
        when(touristService.deleteAttractionByName(attractionName)).thenReturn(false);

        String viewName = touristController.deleteTouristAttraction(attractionName);

        assertEquals("redirect:/attractions?error=not_found", viewName);
        verify(touristService).deleteAttractionByName(attractionName);
    }
}