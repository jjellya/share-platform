package com.ad.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author WenZhikun
 * @data 2020-08-28 17:10
 */
@Data
public class UploadVO {
    @JsonProperty("postId")
    private int postId;

    @JsonProperty("docId")
    private int docId;

    @JsonProperty("commentId")
    private int commentId;

    @JsonProperty("docName")
    private String docName;

    @JsonProperty("starNum")
    private int starNum;
}
