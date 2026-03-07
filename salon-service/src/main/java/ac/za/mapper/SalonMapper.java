package ac.za.mapper;

import ac.za.model.Salon;
import ac.za.payload.dto.SalonDTO;

public class SalonMapper {

    public static SalonDTO mapToDTO(Salon salon) {
        SalonDTO salonDTO = new SalonDTO();
        salonDTO.setId(salon.getId());

        salonDTO.setName(salon.getName());
        salonDTO.setAddress(salon.getAddress());
        salonDTO.setCity(salon.getCity());
        salonDTO.setImages(salon.getImages());
        salonDTO.setPhoneNumber(salon.getPhoneNumber());
        salonDTO.setEmail(salon.getEmail());
        salonDTO.setOpenTime(salon.getOpenTime());
        salonDTO.setCloseTime(salon.getCloseTime());
        salonDTO.setOwnerId(salon.getOwnerId());
        return salonDTO;
    }
}
