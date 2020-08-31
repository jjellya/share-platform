package com.ad.converter;

import com.ad.dto.TagDTO;
import com.ad.pojo.TagInfo;

/**
 * Create By  @林俊杰
 * 2020/8/31 21:34
 *
 * @version 1.0
 */
public class TagInfo2TagDTOConverter {
    public static TagDTO convert(TagInfo tagInfo){
        TagDTO tagDTO  = new TagDTO();
        tagDTO.setTagId(tagInfo.getTagId());
        String content ;String img;
        if (tagInfo.getTagContent().contains("+")){
            img = tagInfo.getTagContent().split("\\+")[tagInfo.getTagContent().split("\\+").length-1];
            if (tagInfo.getTagContent().length()-img.length()-1-1>0)
                content =  tagInfo.getTagContent().substring(0,tagInfo.getTagContent().length()-img.length()-1);
            else content = null;
        }
        else {
            content = tagInfo.getTagContent();
            img = null;
        }
        tagDTO.setImg(img);
        tagDTO.setTagContent(content);
        return tagDTO;
    }
}
