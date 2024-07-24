/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.mapper;

import lombok.RequiredArgsConstructor;
import main.dto.CartItemDTO;
import main.models.CartItem;
import main.repo.CartItemRepo;
import main.repo.ProductRepo;
import main.repo.SessionRepo;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
@RequiredArgsConstructor
public class CartItemMapper {
    private final CartItemRepo repo;
    private final SessionRepo sessionRepo;
    private final ProductRepo productRepo;
    
    public CartItem toEntity(CartItemDTO x){
        var c = new CartItem();
        sessionRepo.findById(x.sessionId()).ifPresent(c::setSession);
        productRepo.findById(x.productId()).ifPresent(c::setProduct);
        c.setQuantity(x.quantity());
        return c;
    }
    
    public CartItemDTO toDTO(CartItem c){
        return (c.getProduct()!=null && c.getSession()!=null) ?
                new CartItemDTO(c.getId(),c.getSession().getId(),c.getProduct().getId(),c.getQuantity()) : null;
    }
}
