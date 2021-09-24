
package vboled.netcracker.musicstreamer.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vboled.netcracker.musicstreamer.service.RegionService;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/region")
public class RegionController {

    private final RegionService regionService;

    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping("/")
    @PreAuthorize("hasAuthority('admin:perm')")
    public ResponseEntity<?> getRegionByName(@RequestParam String name) {
        try {
            return new ResponseEntity(regionService.getByName(name), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all/")
    public ResponseEntity<?> getAllRegions() {
        return new ResponseEntity(regionService.getAllRegions(), HttpStatus.OK);
    }
}
