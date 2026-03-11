package ac.za.service;

import ac.za.domain.BookingStatus;
import ac.za.dto.BookingRequest;
import ac.za.dto.SalonDTO;
import ac.za.dto.ServiceDTO;
import ac.za.dto.UserDTO;
import ac.za.model.Booking;
import ac.za.model.SalonReport;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface BookingService {

    Booking createBooking(BookingRequest booking, UserDTO user,
                          SalonDTO salon, Set<ServiceDTO> serviceDTOSet) throws Exception;

    List<Booking> getBookingsByCustomer(Long customerId);

    List<Booking> getBookingsBySalon(Long SalonId);

    Booking getBookingById(Long id) throws Exception;

    Booking updateBooking(Long bookingId, BookingStatus status) throws Exception;

    List<Booking> getBookingsByDate(LocalDate date, Long SalonId);

    SalonReport getSalonReport(Long salonId);
}
