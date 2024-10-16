package tourism.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tourism.model.Tag;
import tourism.model.TouristAttraction;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TouristRepositoryTest {

    private TouristRepository touristRepository;

    @BeforeEach
    public void setUp() {
       touristRepository = new TouristRepository();
    }

    @Test
    public void tesGetTouristAttractions() {
        List<TouristAttraction> attractions = touristRepository.getTouristAttractions();
        assertNotNull(attractions);
        assertEquals(5, attractions.size());
    }

    @Test
    public void testFindAttractionByName() {
        TouristAttraction attraction = touristRepository.findAttractionByName("SMK");
        assertNotNull(attraction);
        assertEquals("SMK", attraction.getName());
    }

    @Test
    public void testAddTouristAttraction() {
        TouristAttraction newAttraction = new TouristAttraction("New Attraction", "Description", "City", List.of(Tag.ART));
        touristRepository.addTouristAttraction(newAttraction);

        TouristAttraction foundAttraction = touristRepository.findAttractionByName("New Attraction");
        assertNotNull(foundAttraction);
        assertEquals("New Attraction", foundAttraction.getDescription());
    }

    @Test
    public void testUpdateTouristAttraction() {
        TouristAttraction updateAttraction = new TouristAttraction("SMK", "Updated Description", "Copenhagen", List.of(Tag.CULTURE));
        touristRepository.updateTouristAttraction(updateAttraction);

        TouristAttraction foundAttraction = touristRepository.findAttractionByName("SMK");
        assertNotNull(foundAttraction);
        assertEquals("Updated Description", foundAttraction.getDescription());
    }

    @Test
    public void testDeleteAttractionByName() {
        boolean deleted = touristRepository.deletetAttractionByName("SMK");
        assertTrue(deleted);

        TouristAttraction foundAttraction = touristRepository.findAttractionByName("SMK");
        assertNull(foundAttraction);
    }

    @Test
    public void testGetCities() {
        List<String> cities = touristRepository.getCities();
        assertNotNull(cities);
        assertFalse(cities.isEmpty());
        assertTrue(cities.contains("Copenhagen"));
    }

    @Test
    public void testGetTags() {
        List<Tag> tags = touristRepository.getTags();
        assertNotNull(tags);
        assertFalse(tags.isEmpty());
        assertTrue(tags.contains(Tag.ART));
    }
}