package ac.za.service;

import ac.za.model.Salon;
import ac.za.payload.dto.SalonDTO;
import ac.za.payload.dto.UserDTO;

import java.util.List;

public interface SalonService {

    Salon createSalon(SalonDTO salonDTO, UserDTO user);

    Salon updateSalon(SalonDTO salonDTO, UserDTO user, Long salonId) throws Exception;

    List<Salon> getAllSalons();

    Salon getSalonById(Long salonId) throws Exception;

    Salon getSalonByOwnerId(Long ownerId);

    List<Salon> searchSalonByCity(String city);
}
