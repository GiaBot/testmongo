package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Repository.ModelRepository;
import com.example.demo.models.Model;

@RestController
@RequestMapping("/api")
public class ModelController {

    private final ModelRepository repository;

    ModelController(ModelRepository repository) {
        this.repository = repository;
    }

    @PostMapping("addModel")
    public Model addModel(Model model) {
        return repository.save(model);
    }

    @PostMapping("addModels")
    public List<Model> addModels(@RequestBody List<Model> models) {
        return repository.saveAll(models);
    }

    @GetMapping("modelId/{id}")
    public Optional<Model> getModelById(@PathVariable String id) {
        return repository.findById(id);
    }

    @GetMapping("modelAll")
    public List<Model> getAllModels() {
        return repository.findAll();
    }

    @DeleteMapping("deleteModel/{id}")
    public void deleteModelById(@PathVariable String id) {
        repository.deleteById(id);
    }

    @DeleteMapping("deleteAllModels")
    public void deleteAllModels() {
        repository.deleteAll();
    }

    @PutMapping("saveModel")
    public Model saveModel(@RequestBody Model model) {
        return repository.save(model);
    }

    @PutMapping("saveModels")
    public List<Model> saveModels(List<Model> models) {
        return repository.saveAll(models);
    }
}