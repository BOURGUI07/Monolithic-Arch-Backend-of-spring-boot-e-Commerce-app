/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.controller;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import main.dto.ProductDTO;
import main.exception.EntityNotFoundException;
import main.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hp
 */
@RestController
@RequestMapping("/api/products")
@Validated
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;
    
    @GetMapping
    public ResponseEntity<Page<ProductDTO>> findAll(
            @RequestParam(defaultValue="0")int page,
            @RequestParam (defaultValue="10")int size){
        var result = service.findAll(page, size);
        if(result.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Integer id){
        var product = service.findById(id);
        try{
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch(EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @PostMapping
    public ResponseEntity<ProductDTO> create(@Valid @RequestBody ProductDTO x){
        var createdProduct = service.create(x);
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        }catch(ConstraintViolationException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Integer id, @Valid @RequestBody ProductDTO x){
        var updatedProduct = service.update(id, x);
        try{
            return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
        }catch(IllegalArgumentException | ConstraintViolationException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch(EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        try{
            service.delete(id);
            return ResponseEntity.noContent().build();
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch(EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @GetMapping("/search")
    public ResponseEntity<Page<ProductDTO>> search(
            @RequestParam(required=false) String name,
            @RequestParam(required=false) String desc,
            @RequestParam(required=false) Boolean discountStatus,
            @RequestParam(required=false) String categoryName,
            @RequestParam(required=false) Double minPrice,
            @RequestParam(required=false) Double maxPrice,
            @RequestParam(defaultValue="0") int page,
            @RequestParam(defaultValue="10") int size
    ){
        var result = service.search(name, desc, discountStatus, categoryName, minPrice, maxPrice, page, size);
        if(result.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    
    @GetMapping("/category/{id}")
    public ResponseEntity<List<ProductDTO>> findProductsWithCategoryId(@PathVariable Integer id){
        var list = service.findProductsWithCategoryId(id);
        try{
            if(list.isEmpty()){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(list);
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch(EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
