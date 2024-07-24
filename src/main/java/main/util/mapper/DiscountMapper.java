/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.mapper;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import main.dto.DiscountDTO;
import main.models.Discount;
import main.repo.DiscountRepo;
import main.repo.ProductRepo;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@RequiredArgsConstructor
@Service
public class DiscountMapper {
    private final DiscountRepo repo;
    private final ProductRepo productRepo;
    
    public Discount toEntity(DiscountDTO x){
        var d = new Discount();
        d.setActive(x.active());
        d.setDesc(x.desc());
        d.setName(x.name());
        d.setPercent(x.percent());
        var list = x.productIds();
        if (list != null) {
            d.setProducts(productRepo.findAllById(list));
        }
        return d;
    }
    
    public DiscountDTO toDTO(Discount d){
        var list = d.getProducts().stream().map(x -> x.getId()).collect(Collectors.toList());
        return new DiscountDTO(d.getId(),d.getName(),d.getDesc(),d.getPercent(),d.isActive(),list);
    }
}
