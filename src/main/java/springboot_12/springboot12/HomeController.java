package springboot_12.springboot12;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.ObjectUtils;

import java.io.IOException;
import java.util.AbstractCollection;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired ActorRepository actorRepository;
    @Autowired CloudinaryConfig cloudc;

    @RequestMapping("/")
    public String listActor(Model model){
        model.addAttribute("actors",actorRepository.findAll());
        return "list";
    }
    @GetMapping("/add")
    public String newActore(Model model){
        model.addAttribute("actor", new Actor());
        return "form";
        }
        @PostMapping("/add")
    public String processActore(@ModelAttribute Actor actor, @RequestParam("file")MultipartFile file){
        if(file.isEmpty()){
            return "redirect:/";
        }
        try{
            Map uploadResult=cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcetype","auto"));
            actor.setHeadshot(uploadResult.get("url").toString());
            actorRepository.save(actor);

        }catch (IOException e){
            e.printStackTrace();
            return "redirect:/add";
        }
        return "redirect:/";

        }

}
