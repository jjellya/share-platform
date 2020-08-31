package com.ad.converter;

import com.ad.dto.DocShowDTO;
import com.ad.dto.PostDTO;
import com.ad.enums.DocTypeEnum;
import com.ad.pojo.DocInfo;
import com.ad.pojo.PostInfo;
import com.ad.pojo.TagInfo;
import com.ad.pojo.UserInfo;
import com.ad.utils.DocTypeImg;
import com.ad.utils.MyDateUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By  @林俊杰
 * 2020/8/30 22:11
 *
 * @version 1.0
 */
@Slf4j
public class DocInfo2DocShowDTOConverter {

    public static DocShowDTO convert(DocInfo docInfo, UserInfo userInfo, List<TagInfo> tagInfoList){

        DocShowDTO docShowDTO = new DocShowDTO();
        docShowDTO.setAvatarUrl(userInfo.getAvatarUrl());
        docShowDTO.setUsername(userInfo.getUserName());
        docShowDTO.setDocId(docInfo.getDocId());
        docShowDTO.setDocName(docInfo.getDocName());
        docShowDTO.setStarNum(docInfo.getStarNum());
        docShowDTO.setUploadTime(MyDateUtil.convertTimeToFormat(docInfo.getUploadTime().getTime()));
        String img = null;

        if (docInfo.getDocType()==DocTypeEnum.TXT.getCode()){
            img = DocTypeImg.TXT;
        }else {

        }
        docShowDTO.setImg(img);

        List<String> tagContentList = new ArrayList<>();
        for (TagInfo tag:tagInfoList) {
            tagContentList.add(tag.getTagContent());
        }
        docShowDTO.setTag(tagContentList);

        return docShowDTO;
    }
}
