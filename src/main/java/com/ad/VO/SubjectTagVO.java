package com.ad.VO;

import com.ad.dto.PostDTO;
import com.ad.dto.TagDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Create By  @林俊杰
 * 2020/8/31 21:51
 *
 * @version 1.0
 */
@Data
public class SubjectTagVO {
    @JsonProperty("total")
    private Integer total;

    @JsonProperty("tagDTOList")
    private List<TagDTO> tagDTOList;
}
