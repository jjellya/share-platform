package com.ad.converter;

import com.ad.dto.DocDTO;
import com.ad.pojo.DocInfo;
import com.ad.pojo.UserInfo;
import com.ad.utils.MyDateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WenZhikun
 * @data 2020-08-31 10:46
 */
public class DocInfo2DocDTOConvert {
   public static DocDTO covert(DocInfo docInfo, UserInfo userInfo){
       DocDTO docDTO = new DocDTO();
       docDTO.setDocId(docInfo.getDocId());
       docDTO.setDownloadNum(docInfo.getDownloadNum());
       docDTO.setDocName(docInfo.getDocName());
       docDTO.setStarNum(docInfo.getStarNum());
       docDTO.setUserId(docInfo.getUserId());
       docDTO.setAvartUrl(userInfo.getAvatarUrl());
       docDTO.setUserName(userInfo.getUserName());
       docDTO.setUpdateTime(MyDateUtil.convertTimeToFormat(docInfo.getUploadTime().getTime()));
       //System.out.println(docDTO);
       return docDTO;
   }

   public static List covertList(List<DocInfo>docInfoList,UserInfo userInfo){
       List<DocDTO>docDTOList = new ArrayList<>();
       for (int i=0;i<docInfoList.size();i++){
           docDTOList.add(covert(docInfoList.get(i),userInfo));
       }
       return docDTOList;
   }

    /**
     * 返回文件类型为type的DTO
     * @param docInfoList
     * @param type
     * @return
     */
   public static List selectDocType(List<DocInfo>docInfoList,int type,UserInfo userInfo){
       List<DocDTO>docDTOList = new ArrayList<>();
       String lastName = null;
       for (int i=0;i<docInfoList.size();i++){
           //获取文件后缀名
           //System.out.println(docInfoList.get(i).getDocName());
           //System.out.println(docInfoList.get(i).getDocName().split("\\.")[1]);
           lastName = docInfoList.get(i).getDocName().split("\\.")[docInfoList.get(i).getDocName().split("\\.").length-1];
           if (type==0&&lastName.contains("doc")){
               docDTOList.add(covert(docInfoList.get(i),userInfo));
           }else if (type==1&&lastName.equals("pdf")){
               docDTOList.add(covert(docInfoList.get(i),userInfo));
           }else if (type==2&&lastName.equals("ppt")){
               docDTOList.add(covert(docInfoList.get(i),userInfo));
           }else{
               docDTOList.add(covert(docInfoList.get(i),userInfo));
           }
       }
       return docDTOList;
   }
}
