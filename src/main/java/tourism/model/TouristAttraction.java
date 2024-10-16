package tourism.model;

import java.util.ArrayList;
import java.util.List;

public class TouristAttraction {
    private String name;
    private String description;
    private String location;
    private List<Tag> tags;

    public TouristAttraction(String name, String description, String location, List<Tag> tags) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.tags = tags != null ? tags : new ArrayList<>(); // Hvis tags ikke er null, instansiér ny ArratList
    }

    // Semi-tom constructor nødvendig for data binding med Spring
    public TouristAttraction() {
        this.tags = new ArrayList<>(); // Sikrer at listen ikke er null
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }

    public List<Tag> getTags() { return tags; }

    public void setTags(List<Tag> tags) { this.tags = tags; }

    public void addTag(Tag tag) { tags.add(tag); }

}
