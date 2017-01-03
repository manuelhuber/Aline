package de.fh.rosenheim.aline.model.dtos.bill;

import com.fasterxml.jackson.annotation.JsonView;
import de.fh.rosenheim.aline.model.dtos.json.view.View;
import de.fh.rosenheim.aline.util.SwaggerTexts;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DivisionSumDTO {

    @JsonView(View.BillView.class)
    private String division;

    @JsonView(View.BillView.class)
    @ApiModelProperty(notes = SwaggerTexts.CURRENCY)
    private long totalCost;
}
