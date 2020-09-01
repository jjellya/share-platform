package com.ad.controller;

import com.ad.VO.ResultVO;
import com.ad.VO.SearchResultVO;
import com.ad.dto.DocShowDTO;
import com.ad.dto.PostDTO;
import com.ad.service.Impl.SearchServiceImpl;
import com.ad.service.SearchService;
import com.ad.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Create By  @林俊杰
 * 2020/8/30 22:41
 *
 * @version 1.0
 */
@Controller
@Slf4j
public class SearchController {

    @Autowired
    private SearchServiceImpl searchService;

    @RequestMapping("api/index/search")
    @ResponseBody
    public ResultVO search(@RequestParam("keyword") String keyword,
                           @RequestParam("index") int index,
                           @RequestParam("size") int size){

        try {
            List<DocShowDTO> docShowDTOList = searchService.searchDoc(keyword, index, size);

            List<PostDTO> postDTOList = searchService.searchPost(keyword, index, size-docShowDTOList.size());

            if (docShowDTOList.isEmpty()&&postDTOList.isEmpty()){
                log.error("有关"+keyword+"的内容为空");
                return ResultVOUtil.errorMsg("有关"+keyword+"的内容为空");
            }

            SearchResultVO searchResultVO = new SearchResultVO();
            searchResultVO.setDocDTOList(docShowDTOList);
            searchResultVO.setPostDTOList(postDTOList);
            searchResultVO.setTotal(docShowDTOList.size()+postDTOList.size());

            return ResultVOUtil.success(searchResultVO);
        }catch (Exception e){
            log.error("遇到未知错误："+e.getMessage());
            return ResultVOUtil.errorMsg("遇到未知错误");
        }
    }
}
