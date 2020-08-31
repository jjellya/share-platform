package com.ad.VO;

import com.ad.dto.PostDTO;
import com.ad.pojo.DocInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author WenZhikun
 * @data 2020-08-31 11:45
 */
@Data
public class MyStarVO {
    @JsonProperty("docDTO")
    private List<DocInfo> docInfoList;

    @JsonProperty("total")
    private int total;
}
