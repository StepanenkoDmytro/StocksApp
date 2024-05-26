package com.stock.rest;

import com.stock.model.icons.GoogleIcon;
import com.stock.repository.icons.GoogleIconsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/icons/")
@CrossOrigin
public class IconsController {
    private final GoogleIconsRepository googleIconsRepository;

    public IconsController(GoogleIconsRepository googleIconsRepository) {
        this.googleIconsRepository = googleIconsRepository;
    }

    @GetMapping("")
    public ResponseEntity<List<String>> getAllIcons() {
        List<String> all = googleIconsRepository.findAll().stream().map(GoogleIcon::getIcon).collect(Collectors.toList());
        return ResponseEntity.ok(all);
    }
}
