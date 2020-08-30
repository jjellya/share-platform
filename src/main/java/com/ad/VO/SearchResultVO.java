package com.ad.VO;

import com.ad.dto.DocShowDTO;
import com.ad.dto.PostDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Create By  @林俊杰
 * 2020/8/30 23:00
 *
 * @version 1.0
 */
@Data
public class SearchResultVO {

    @JsonProperty("total")
    private Integer total;

    @JsonProperty("postDTOList")
    private List<PostDTO> postDTOList;

    @JsonProperty("docDTOList")
    private List<DocShowDTO> docDTOList;

}
