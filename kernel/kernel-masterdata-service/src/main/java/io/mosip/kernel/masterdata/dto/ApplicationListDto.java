package io.mosip.kernel.masterdata.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationListDto {

	private List<ApplicationDto> applicationtypes;
}
