package de.fh.rosenheim.aline.model.dtos.bill;

import com.fasterxml.jackson.annotation.JsonView;
import de.fh.rosenheim.aline.model.dtos.json.view.View;
import de.fh.rosenheim.aline.model.dtos.seminar.SeminarDTO;
import de.fh.rosenheim.aline.model.dtos.user.UserDTO;
import de.fh.rosenheim.aline.util.SwaggerTexts;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class BillDTO {

    @JsonView(View.BillView.class)
    private SeminarDTO seminar;

    @JsonView(View.BillView.class)
    private List<UserDTO> participants;

    @JsonView(View.BillView.class)
    private int participantCount;

    @JsonView(View.BillView.class)
    @ApiModelProperty(notes = SwaggerTexts.CURRENCY)
    private long totalCost;

    @JsonView(View.BillView.class)
    private List<DivisionSumDTO> divisionSums;
}
