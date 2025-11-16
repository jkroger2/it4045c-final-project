package app.campassist.enterprise.web.controller;

import app.campassist.enterprise.service.CampsiteService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/campsites")
public class CampsiteViewController {

    private final CampsiteService campsiteService;

    public CampsiteViewController(CampsiteService campsiteService) {
        this.campsiteService = campsiteService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("campsites", campsiteService.fetchAllCampsites());
        return "campsites/list";
    }

    @GetMapping("/{id}/details")
    public String detail(@PathVariable String id, Model model) {
        UUID campsiteId = UUID.fromString(id);
        model.addAttribute("campsite", campsiteService.fetchCampsiteById(campsiteId));
        return "campsites/details";
    }

}
