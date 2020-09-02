package com.ad.VO;

import com.ad.dto.CommentDTO;
import com.ad.dto.CommentDocDTO;
import com.ad.dto.PostDTO;
import com.ad.pojo.CommentInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author WenZhikun
 * @data 2020-08-26 21:11
 */
@Data
public class PostDetailsVO {

    @JsonProperty("postDTO")
    private PostDTO postDTO;

    @JsonProperty("commentList")
    private List<CommentDTO> commentDTOList;

    @JsonProperty("commentDocList")
    private List<CommentDocDTO>commentDocDTOList;

}
