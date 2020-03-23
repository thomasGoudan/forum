package com.wuxiu.forum.model;

import com.wuxiu.forum.dto.QuestionDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questionDTOList;
    private boolean showPrevious;//上一页按钮
    private boolean showFirstPage;//首页按钮
    private boolean showNext;//下一页按钮
    private boolean showEedPage;//尾页按钮
    private Integer pageIndex;//当前页
    private List<Integer> pageList = new ArrayList<>();//当前页列表数
    private Integer totalPage;//总页数

    public void setPageIation(Integer totalCount, Integer pageIndex, Integer pageSize) {
        //总页数
        if (totalCount % pageSize == 0){
            this.totalPage = totalCount/pageSize;
        }else {
            this.totalPage = totalCount/pageSize + 1;
        }
        //判断页面传入参数极值
        if (pageIndex <1){
            pageIndex = 1;
        }
        if (pageIndex > totalPage){
            pageIndex = totalPage;
        }
        this.pageIndex = pageIndex;
        //保证作有两边都不大于三个，即最多显示7个
        pageList.add(pageIndex);//当前页码
        for (int i = 1; i <=3 ; i++) {//循环三次，保证列表中只添加七页
            if(pageIndex - i > 0){//判断当前页是第一页时，不显示前三页
                pageList.add(0,pageIndex -i);//列表加上前三页,确保元素每次都是加在当前页码后面
            }
            if(pageIndex + i <= totalPage){//判断当前页未是最后一页，不显示后三页
                pageList.add(pageIndex + i);//列表加上后三页
            }
        }


        //当前页是第一页的时候没有上一页按钮
        if (pageIndex == 1){
            showPrevious = false;
        }else {
            showPrevious = true;
        }
        //当前页等于最后一页的时候，没有下一页按钮
        if(pageIndex == totalPage){
            showNext = false;
        }else {
            showNext = true;
        }
        //如果是第一页，没有首页按钮
        if(pageList.contains(1)){
            showFirstPage = false;
        }else {
            showFirstPage = true;
        }
        //如果是最后一页，没有尾页按钮
        if(pageList.contains(totalPage)){
            showEedPage = false;
        }else {
            showEedPage = true;
        }
    }
}
