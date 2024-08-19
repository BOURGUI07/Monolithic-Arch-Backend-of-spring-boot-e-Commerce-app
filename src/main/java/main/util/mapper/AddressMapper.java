/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.mapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import main.dto.UserAddressDTO;
import main.models.UserAddress;
import main.repo.UserRepo;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal=true, level=AccessLevel.PRIVATE)
public class AddressMapper {
      UserRepo userRepo;
    
    public UserAddress toEntity(UserAddressDTO x){
        var a = new UserAddress()
        .setAddressLine1(x.addressLine1())
        .setAddressLine2(x.addressLine2())
        .setCity(x.city())
        .setPostalCode(x.postalcode())
        .setCountry(x.country());
        var user = userRepo.findById(x.userId());
        if(user.isPresent()){
            a.setUser(user.get());
        }
        return a;
    }
    
    public UserAddressDTO toDTO(UserAddress a){
        return a.getUser()!=null ? new UserAddressDTO(a.getId(),a.getUser().getId(),
                a.getAddressLine1(),a.getAddressLine2(),a.getCity(),a.getPostalCode(),a.getCountry()) : null;
    }
}
