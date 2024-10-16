package tourism.service;

import org.springframework.stereotype.Service;
import tourism.model.Tag;
import tourism.model.TouristAttraction;
import tourism.repository.TouristRepository;

import java.util.List;

@Service
public class TouristService {
    private final TouristRepository touristRepository;

    public TouristService(TouristRepository touristRepository) {
        this.touristRepository = touristRepository;
    }

    public List<TouristAttraction> getTouristAttractions() {
        return touristRepository.getTouristAttractions();
    }

    public TouristAttraction findAttractionByName(String name, String caps) {
        TouristAttraction touristAttraction = touristRepository.findAttractionByName(name);
        if (caps != null && caps.equals("yes")) {
            return new TouristAttraction(touristAttraction.getName(), touristAttraction.getDescription(), touristAttraction.getLocation(), touristAttraction.getTags());
        }
        return touristAttraction;
    }

    public TouristAttraction addTouristAttraction(TouristAttraction touristAttraction) {
        return touristRepository.addTouristAttraction(touristAttraction);
    }

    public TouristAttraction updateTouristAttraction(TouristAttraction touristAttraction) {
        return touristRepository.updateTouristAttraction(touristAttraction);
    }

    public boolean deleteAttractionByName(String name) {
        return touristRepository.deletetAttractionByName(name);
    }

    public List<String> getCities() {
        return touristRepository.getCities();
    }

    public List<Tag> getTags() {
        return touristRepository.getTags();
    }
}