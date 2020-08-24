package com.ad.VO;

import com.ad.dto.PostDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Create By  @林俊杰
 * 2020/8/23 21:53
 *
 * @version 1.0
 */
@Data
public class CommunityVO {

    @JsonProperty("total")
    private Integer total;

    @JsonProperty("postDTOList")
    private List<PostDTO> postDTOList;
}
