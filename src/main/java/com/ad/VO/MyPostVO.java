package com.ad.VO;

import com.ad.dto.PostDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author WenZhikun
 * @data 2020-08-31 12:23
 */
@Data
public class MyPostVO {
    @JsonProperty("postDTO")
    private List<PostDTO>postDTOList;

    @JsonProperty("total")
    private int total;
}
