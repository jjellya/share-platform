package com.ad.VO;

import com.ad.dto.AdvertisementDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author WenZhikun
 * @data 2020-08-29 21:04
 */
@Data
public class AdvertisementVO {
    @JsonProperty("total")
    private int total;

    @JsonProperty("advertiseDTOList")
    private List<AdvertisementDTO>advertisementDTOList;

}
