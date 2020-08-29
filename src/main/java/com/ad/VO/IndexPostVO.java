package com.ad.VO;

import com.ad.dto.PostDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author WenZhikun
 * @data 2020-08-29 11:55
 */
@Data
public class IndexPostVO {
    @JsonProperty("postDTO")
    private List<PostDTO>postDTOS;

    @JsonProperty("postNumInGroup")
    private int postNumInGroup;
}
