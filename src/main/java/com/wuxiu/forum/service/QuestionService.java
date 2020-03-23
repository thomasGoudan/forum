package com.wuxiu.forum.service;

import com.wuxiu.forum.dto.QuestionDTO;
import com.wuxiu.forum.mapper.QuestionMapper;
import com.wuxiu.forum.mapper.UserMapper;
import com.wuxiu.forum.model.PaginationDTO;
import com.wuxiu.forum.model.Question;
import com.wuxiu.forum.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public PaginationDTO getList(Integer pageIndex, Integer pageSize) {
        //分页数据
        PaginationDTO paginationDTO = new PaginationDTO();
        //数据传输列表对象
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        //总记录数
        Integer totalCount = questionMapper.getQuestionTotal();
        //分页逻辑判断
        paginationDTO.setPageIation(totalCount,pageIndex,pageSize);
        //判断页面传入参数极值
        if (pageIndex <1){
            pageIndex = 1;
        }
        if (pageIndex > paginationDTO.getTotalPage()){
            pageIndex = paginationDTO.getTotalPage();
        }
        //开始查询处
        Integer offSet = pageSize * (pageIndex -1);
        //查询每页显示的数据
        List<Question> questionList = questionMapper.getList(offSet,pageSize);
        if (questionList != null){
            for (Question question : questionList) {
                User user = userMapper.findUserById(question.getCreator());
                QuestionDTO questionDTO = new QuestionDTO();
                //拷贝到数据传输层
                BeanUtils.copyProperties(question,questionDTO);
                questionDTO.setUser(user);
                questionDTOList.add(questionDTO);
            }
            paginationDTO.setQuestionDTOList(questionDTOList);

        }
        return paginationDTO;
    }
}
