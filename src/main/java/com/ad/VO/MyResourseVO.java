package com.ad.VO;

import com.ad.dto.DocDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author WenZhikun
 * @data 2020-08-31 10:56
 */
@Data
public class MyResourseVO {
    @JsonProperty("total")
    private int total;

    @JsonProperty("type")
    private int type;

    @JsonProperty("resList")
    private List<DocDTO>docDTOList;

}
