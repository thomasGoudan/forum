package com.wuxiu.forum.service;

import com.wuxiu.forum.dto.QuestionDTO;
import com.wuxiu.forum.exception.CustomizeErrorCode;
import com.wuxiu.forum.exception.CustomizeException;
import com.wuxiu.forum.mapper.QuestionExaMapper;
import com.wuxiu.forum.mapper.QuestionMapper;
import com.wuxiu.forum.mapper.UserMapper;
import com.wuxiu.forum.dto.PaginationDTO;
import com.wuxiu.forum.model.Question;
import com.wuxiu.forum.model.QuestionExample;
import com.wuxiu.forum.model.User;
import org.apache.ibatis.session.RowBounds;
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
    @Autowired
    private QuestionExaMapper questionExaMapper;

    public PaginationDTO getList(Integer pageIndex, Integer pageSize) {
        //分页数据
        PaginationDTO paginationDTO = new PaginationDTO();
        //数据传输列表对象
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        //总记录数
        Integer totalCount = (int)questionMapper.countByExample(new QuestionExample());
        //总页数
        if (totalCount % pageSize == 0) {
            totalCount = totalCount / pageSize;
        } else {
            totalCount = totalCount / pageSize + 1;
        }
        //判断页面传入参数极值
        if (pageIndex < 1) {
            pageIndex = 1;
        }
        if (pageIndex > totalCount) {
            pageIndex = totalCount;
        }
        //分页逻辑判断
        paginationDTO.setPageIation(totalCount, pageIndex);
        //开始查询处
        Integer offSet = pageSize * (pageIndex - 1);
        //查询每页显示的数据
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offSet, pageSize));
        if (questionList != null) {
            for (Question question : questionList) {
                User user = userMapper.selectByPrimaryKey(question.getCreator());
                QuestionDTO questionDTO = new QuestionDTO();
                //拷贝到数据传输层
                BeanUtils.copyProperties(question, questionDTO);
                questionDTO.setUser(user);
                questionDTOList.add(questionDTO);
            }
            paginationDTO.setQuestionDTOList(questionDTOList);

        }
        return paginationDTO;
    }


    /**
     * @param userId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public PaginationDTO list(Long userId, Integer pageIndex, Integer pageSize) {
        //分页数据
        PaginationDTO paginationDTO = new PaginationDTO();
        //数据传输列表对象
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        //总记录数
        QuestionExample questionExampleAndUserId = new QuestionExample();
        questionExampleAndUserId.createCriteria().andCreatorEqualTo(userId);
        Integer totalCount = (int)questionMapper.countByExample(questionExampleAndUserId);
        Integer pageCount;
        //总页数
        if (totalCount % pageSize == 0) {
            pageCount = totalCount / pageSize;
        } else {
            pageCount = totalCount / pageSize + 1;
        }
        //判断页面传入参数极值
        if (pageIndex < 1) {
            pageIndex = 1;
        }
        if (pageIndex > pageCount) {
            pageIndex = pageCount;
        }
        //分页逻辑判断
        paginationDTO.setPageIation(pageCount, pageIndex);
        //开始查询处
        Integer offSet = pageSize * (pageIndex - 1);
        //查询每页显示的数据
        QuestionExample exampleAndUserId = new QuestionExample();
        exampleAndUserId.createCriteria().andCreatorEqualTo(userId);
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(exampleAndUserId, new RowBounds(offSet, pageSize));
        if (questionList != null) {
            for (Question question : questionList) {
                User user = userMapper.selectByPrimaryKey(question.getCreator());
                QuestionDTO questionDTO = new QuestionDTO();
                //拷贝到数据传输层
                BeanUtils.copyProperties(question, questionDTO);
                questionDTO.setUser(user);
                questionDTOList.add(questionDTO);
            }
            paginationDTO.setQuestionDTOList(questionDTOList);

        }
        return paginationDTO;
    }

    public QuestionDTO getUserQuestionByQuestionId(Long userQuestionId) {
        Question userQuestion = this.questionMapper.selectByPrimaryKey(userQuestionId);
        if (userQuestion == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        User user = userMapper.selectByPrimaryKey(userQuestion.getCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(userQuestion, questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setCommentCount(0);
            question.setViewCount(0);
            question.setLikeCount(0);
            questionMapper.insert(question);
        } else {
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            //添加的约束,比如添加
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria().andIdEqualTo(question.getId());
            //前面一个参数是你要修改的对象，后面一个是约束
            int i = questionMapper.updateByExampleSelective(updateQuestion, questionExample);
            if (i != 1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public Question getQuestionById(Long questionId) {
        Question question = questionMapper.selectByPrimaryKey(questionId);
        return question;
    }

    /**
     * 添加阅读数
     * @param userQuestionId
     */
    public void incView(Long userQuestionId) {
        Question question = new Question();
        question.setId(userQuestionId);
        question.setViewCount(1);
        questionExaMapper.incView(question);
    }
}
