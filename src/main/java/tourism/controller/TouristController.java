package tourism.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tourism.model.TouristAttraction;
import tourism.service.TouristService;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("attractions")
public class TouristController {
    private final TouristService touristService;

    public TouristController(TouristService touristService) {
        this.touristService = touristService;
    }

    @GetMapping("")
    public String getTouristAttractions(Model model) {
        List<TouristAttraction> touristAttractions = touristService.getTouristAttractions();
        System.out.println("Tourist Attractions: " + touristAttractions);
        model.addAttribute("touristAttractions", touristAttractions);
        return "attractionsList"; // Returns html list
    }

    @GetMapping("/{name}")
    public ResponseEntity<TouristAttraction> getTouristAttractionByName(@PathVariable String name, @RequestParam(required = false) String caps) {
        TouristAttraction touristAttraction = touristService.findAttractionByName(name, caps);
        return new ResponseEntity<>(touristAttraction, HttpStatus.OK);
    }

    @GetMapping("/add")
    public String showAddTouristAttractionForm(Model model) {
        model.addAttribute("touristAttraction", new TouristAttraction());
        model.addAttribute("locations", touristService.getCities());
        model.addAttribute("tags", touristService.getTags());
        return "addTouristAttractionForm";
    }

    @GetMapping("/tags/{name}")
    public String showTagsPage(@PathVariable String name, Model model) {
        TouristAttraction attraction = touristService.findAttractionByName(name, null);

        model.addAttribute("attraction", attraction);
        return "tags";
    }

    @GetMapping("/edit/{name}")
    public String showEditTouristAttractionForm(@PathVariable String name, Model model) {
        TouristAttraction touristAttraction = touristService.findAttractionByName(name, null);
        model.addAttribute("attraction", touristAttraction);
        model.addAttribute("locations", touristService.getCities());
        model.addAttribute("tags", touristService.getTags());
        return "editTouristAttractionForm";
    }

    @PostMapping("/update")
    public String updateTouristAttraction(@ModelAttribute TouristAttraction touristAttraction) {
        touristService.updateTouristAttraction(touristAttraction);
        return "redirect:/attractions";
    }

    @PostMapping("/save")
    public String saveTouristAttraction(@ModelAttribute TouristAttraction touristAttraction) {
        touristService.addTouristAttraction(touristAttraction);
        return "redirect:/attractions";
    }

    @PostMapping("/delete/{name}")
    public String deleteTouristAttraction(@PathVariable String name) {
        boolean isDeleted = touristService.deleteAttractionByName(name);
        if (isDeleted) {
            return "redirect:/attractions";
        } else {
            return "redirect:/attractions?error=not_found";
        }
    }
}