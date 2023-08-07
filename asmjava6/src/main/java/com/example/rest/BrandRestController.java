package com.example.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Brand;
import com.example.jparepository.BrandRepository;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/brands")
public class BrandRestController {
    @Autowired
    BrandRepository brandRepository;

    @GetMapping()
    public List<Brand> getAll() {
        return brandRepository.findAll();
    }

    @GetMapping("{id}")
    public Brand getOne(@PathVariable("id") Integer id) {
        return brandRepository.findById(id).get();
    }
    @PostMapping()
    public Brand create(@RequestBody Brand brand) {
        return brandRepository.save(brand);
    }
    @PutMapping("{id}")
    public Brand update(@PathVariable("id") Integer id, @RequestBody Brand brand) {
        return brandRepository.save(brand);
    }
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Integer id) {
        brandRepository.deleteById(id);
    }
}
