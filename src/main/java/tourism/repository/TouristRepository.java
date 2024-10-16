package tourism.repository;

import org.springframework.stereotype.Repository;
import tourism.model.Tag;
import tourism.model.TouristAttraction;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class TouristRepository {
    private List<TouristAttraction> touristAttractions;
    private List<String> cities = new ArrayList<>(List.of("Copenhagen", "Paris", "New York City", "Rome", "Pisa", "London"));

    public TouristRepository() {
        fillTouristAttractions();
    }

    private void fillTouristAttractions() {
        touristAttractions = new ArrayList<>(List.of(
                new TouristAttraction("SMK", "Museum of art", "Copenhagen", List.of(Tag.ART, Tag.CULTURE)),
                new TouristAttraction("Tivoli", "Amusement park", "Copenhagen", List.of(Tag.FAMILY_FRIENDLY)),
                new TouristAttraction("Leaning Tower of Pisa", "Tower that is leaning", "Pisa", List.of(Tag.ARCHITECTURE, Tag.HISTORICAL)),
                new TouristAttraction("The Eiffel Tower", "Big tower", "Paris", List.of(Tag.ARCHITECTURE, Tag.LANDMARK)),
                new TouristAttraction("Statue of Liberty", "Big statue", "New York City", List.of(Tag.LANDMARK))));
    }

    public List<TouristAttraction> getTouristAttractions() {
        return touristAttractions;
    }

    public TouristAttraction findAttractionByName(String name) {
        for (TouristAttraction touristAttraction : touristAttractions) {
            if (touristAttraction.getName().equals(name)) {
                return touristAttraction;
            }
        }
        return null;
    }

    public TouristAttraction addTouristAttraction(TouristAttraction touristAttraction) {
        touristAttractions.add(touristAttraction);
        return touristAttraction;
    }

    public TouristAttraction updateTouristAttraction(TouristAttraction touristAttraction) {
        for (TouristAttraction existingAttraction : touristAttractions) {
            if (existingAttraction.getName().equalsIgnoreCase(touristAttraction.getName())) {
                existingAttraction.setDescription(touristAttraction.getDescription());
                existingAttraction.setLocation(touristAttraction.getLocation());
                existingAttraction.setTags(touristAttraction.getTags());
                return existingAttraction;
            }
        }
        return null;
    }

    public boolean deletetAttractionByName(String name) {
        return touristAttractions.removeIf(touristAttraction -> touristAttraction.getName().equalsIgnoreCase(name));
    }

    public List<String> getCities() {
        return cities;
    }

    public List<Tag> getTags() {
        return Arrays.asList(Tag.values());
    }
}