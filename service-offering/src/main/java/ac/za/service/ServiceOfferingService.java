package ac.za.service;

import ac.za.dto.CategoryDTO;
import ac.za.dto.SalonDTO;
import ac.za.dto.ServiceDTO;
import ac.za.model.ServiceOffering;

import java.util.Set;

public interface ServiceOfferingService {

    ServiceOffering createServiceOffering(SalonDTO salonDTO, ServiceDTO serviceDTO, CategoryDTO categoryDTO);

    ServiceOffering updateService(Long serviceId, ServiceOffering service) throws Exception;

    Set<ServiceOffering> getAllServiceBySalonId(Long salonId, Long categoryId);

    Set<ServiceOffering> getServiceByIds(Set<Long> ids);

    ServiceOffering getServiceById(Long id) throws Exception;
}
