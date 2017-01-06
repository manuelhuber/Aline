package de.fh.rosenheim.aline.model.dtos.bill;

import de.fh.rosenheim.aline.model.domain.Booking;
import de.fh.rosenheim.aline.model.domain.BookingStatus;
import de.fh.rosenheim.aline.model.domain.Seminar;
import de.fh.rosenheim.aline.model.dtos.seminar.SeminarFactory;
import de.fh.rosenheim.aline.model.dtos.user.UserDTO;
import de.fh.rosenheim.aline.model.dtos.user.UserFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Generates and transforms all Bill models & DTOs
 */
@Component
public class BillFactory {

    private final UserFactory userFactory;

    public BillFactory(UserFactory userFactory) {
        this.userFactory = userFactory;
    }

    /**
     * Generates a BillDTO for the given seminar
     *
     * @return BillDTO
     */
    public BillDTO generateBill(Seminar seminar) {
        BillDTO bill = new BillDTO();
        bill.setSeminar(SeminarFactory.toSeminarDTO(seminar));
        List<UserDTO> users = seminar.getBookings()
                .stream()
                .filter(booking -> booking.getStatus().equals(BookingStatus.GRANTED))
                .map(Booking::getUser)
                .map(userFactory::toUserDTO)
                .collect(Collectors.toList());
        int participantCount = users.size();
        bill.setParticipants(users);
        bill.setParticipantCount(participantCount);
        bill.setTotalCost(seminar.getCostsPerParticipant() * participantCount);

        Map<String, Integer> divisionCount = new HashMap<>();
        users.forEach(user -> {
            String division = user.getDivision();
            int count = divisionCount.containsKey(division) ? divisionCount.get(division) : 0;
            divisionCount.put(
                    division,
                    count + 1
            );
        });

        List<DivisionSumDTO> divisionSums = new ArrayList<>();
        divisionCount.forEach((division, count) -> {
            DivisionSumDTO sum = new DivisionSumDTO();
            sum.setDivision(division);
            sum.setTotalCost(count * seminar.getCostsPerParticipant());
            divisionSums.add(sum);
        });
        bill.setDivisionSums(divisionSums);

        return bill;
    }
}
