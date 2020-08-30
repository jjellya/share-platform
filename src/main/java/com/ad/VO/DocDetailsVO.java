package com.ad.VO;

import com.ad.dto.CommentDTO;
import com.ad.pojo.CommentInfo;
import com.ad.pojo.DocInfo;
import com.ad.pojo.TagInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author WenZhikun
 * @data 2020-08-30 17:18
 */
@Data
public class DocDetailsVO {

    @JsonProperty("docInfo")
    private DocInfo docInfo;

    @JsonProperty("tagInfoList")
    private List<TagInfo> tagInfoList;

    @JsonProperty("commentDTO")
    private CommentDTO commentDTO;

}
