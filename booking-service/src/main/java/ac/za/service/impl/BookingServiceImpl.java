package ac.za.service.impl;

import ac.za.domain.BookingStatus;
import ac.za.dto.BookingRequest;
import ac.za.dto.SalonDTO;
import ac.za.dto.ServiceDTO;
import ac.za.dto.UserDTO;
import ac.za.model.Booking;
import ac.za.model.SalonReport;
import ac.za.repository.BookingRepository;
import ac.za.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    @Override
    public Booking createBooking(BookingRequest booking, UserDTO user, SalonDTO salon,
                                 Set<ServiceDTO> serviceDTOSet) throws Exception {

        int totalDuration = serviceDTOSet.stream().mapToInt(ServiceDTO::getDuration).sum();
        LocalDateTime bookingStartTime = booking.getStartTime();
        LocalDateTime bookingEndTime = bookingStartTime.plusMinutes(totalDuration);

        Boolean isSlotAvailable = isTimeSlotAvailable(salon, bookingStartTime, bookingEndTime);

        int totalPrince = serviceDTOSet.stream().mapToInt(ServiceDTO::getPrice).sum();

        Set<Long> idList = serviceDTOSet.stream().map(ServiceDTO::getId).collect(Collectors.toSet());

        Booking newBooking = new Booking();
        newBooking.setCustomerId(user.getId());
        newBooking.setSalonId(salon.getId());
        newBooking.setServiceIds(idList);
        newBooking.setStatus(BookingStatus.PENDING);
        newBooking.setStartTime(booking.getStartTime());
        newBooking.setEndTime(booking.getEndTime());
        newBooking.setTotalPrice(totalPrince);

        return bookingRepository.save(newBooking);
    }

    public Boolean isTimeSlotAvailable(SalonDTO salonDTO, LocalDateTime bookingStartTime,
                                       LocalDateTime bookingEndTime) throws Exception {

        List<Booking> existingBookings = getBookingsBySalon(salonDTO.getId());

        LocalTime OpenTime = salonDTO.getOpenTime().toLocalTime();
        LocalDateTime salonOpenTime = LocalDateTime.of(bookingStartTime.toLocalDate(), OpenTime);

        LocalTime CloseTime = salonDTO.getOpenTime().toLocalTime();
        LocalDateTime salonCloseTime = LocalDateTime.of(bookingStartTime.toLocalDate(), CloseTime);

        if (bookingStartTime.isBefore(salonOpenTime) || bookingEndTime.isAfter(salonCloseTime)) {
            throw new Exception("Booking time must be within salon's working hours");

        }

        for (Booking existingBooking : existingBookings) {
            LocalDateTime existingBookingStartTime = existingBooking.getStartTime();
            LocalDateTime existingBookingEndTime = existingBooking.getEndTime();

            if (bookingStartTime.isBefore(existingBookingEndTime) && bookingEndTime.isAfter(existingBookingStartTime)) {
                throw new Exception("slot not available, choose different time");
            }

            if (bookingStartTime.isEqual(existingBookingStartTime) || bookingEndTime.isEqual(existingBookingEndTime)) {
                throw new Exception("slot not available, choose different time");
            }
        }

        return true;
    }

    @Override
    public List<Booking> getBookingsByCustomer(Long customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Booking> getBookingsBySalon(Long SalonId) {
        return bookingRepository.findBySalonId(SalonId);
    }

    @Override
    public Booking getBookingById(Long id) throws Exception {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking == null) {
            throw new Exception("Booking bor found");
        }
        return booking;
    }

    @Override
    public Booking updateBooking(Long bookingId, BookingStatus status) throws Exception {
        Booking booking = getBookingById(bookingId);
        booking.setStatus(status);
        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getBookingsByDate(LocalDate date, Long SalonId) {
        List<Booking> allBookings = getBookingsBySalon(SalonId);

        if (date == null) {
            return allBookings;
        }

        return allBookings.stream().filter(booking -> isSameDate(booking.getStartTime(), date) ||
                isSameDate(booking.getEndTime(), date)).collect(Collectors.toList());

    }

    private boolean isSameDate(LocalDateTime dateTime, LocalDate date) {
        return dateTime.toLocalDate().isEqual(date);
    }


    @Override
    public SalonReport getSalonReport(Long salonId) {
        List<Booking> bookings = getBookingsBySalon(salonId);

        int totalEarnings = bookings.stream().mapToInt(Booking::getTotalPrice).sum();

        Integer totalBookings = bookings.size();

        List<Booking> cancelledBookings = bookings.stream().filter(booking -> booking.getStatus() == BookingStatus.CANCELLED).collect(Collectors.toList());

        Double totalRefund = cancelledBookings.stream().mapToDouble(Booking::getTotalPrice).sum();

        SalonReport report = new SalonReport();
        report.setSalonId(salonId);
        report.setCancelledBookings(cancelledBookings.size());
        report.setTotalBookings(totalEarnings);
        report.setTotalEarnings(totalEarnings);
        report.setTotalRefund(totalRefund);
        report.setTotalBookings(totalBookings);

        return report;
    }
}
