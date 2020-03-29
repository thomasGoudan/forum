package com.wuxiu.forum.service;

import com.wuxiu.forum.dto.CommentDTO;
import com.wuxiu.forum.enums.CommentTypeEnum;
import com.wuxiu.forum.enums.NotificationTypeEnum;
import com.wuxiu.forum.enums.NotificationStatusEnum;
import com.wuxiu.forum.exception.CustomizeErrorCode;
import com.wuxiu.forum.exception.CustomizeException;
import com.wuxiu.forum.mapper.*;
import com.wuxiu.forum.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 评论服务
 */
@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExaMapper questionExaMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentExtMapper commentExtMapper;
    @Autowired
    private NotificationMapper notificationMapper;
    @Transactional
    public void insert(Comment comment, User commentator) {
        if (comment.getParentId() ==null || comment.getParentId()==0){
            throw  new CustomizeException(CustomizeErrorCode.TARGET_PRAM_NOT_FOUND);
        }
        if(comment.getType()==null || !CommentTypeEnum.isExist(comment.getType())){
            throw  new CustomizeException(CustomizeErrorCode.TYPE_PRAM_WRONG);
        }
        if(comment.getType() == CommentTypeEnum.COMMENT.getType()){
            //回复评论2
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null){
                throw  new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUNT);
            }
            //回复问题1
            Question question = questionMapper.selectByPrimaryKey(dbComment.getParentId());
            if (question == null){
                throw  new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            //增加评论数
            Comment parentComment = new Comment();
            parentComment.setId(comment.getParentId());
            parentComment.setCommentCount(1);
            commentExtMapper.incCommentCount(parentComment);
            //创建通知
            createNotify(comment, dbComment.getCommentator(),commentator.getName(), question.getTitle(), NotificationTypeEnum.REPLY_COMMENT,question.getId());
        }else {
            //回复问题1
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null){
                throw  new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExaMapper.incCommentCount(question);
            //创建通知
            createNotify(comment,question.getCreator(), commentator.getName(),question.getTitle(), NotificationTypeEnum.REPLY_QUESTION,question.getId());

        }
    }

    private void createNotify(Comment comment, Long receiver, String notifierName, String outerTitle, NotificationTypeEnum notificationType, Long outerId) {
        //通知回复
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());//创建时间
        notification.setType(notificationType.getType());//类型
        notification.setOuterId(outerId);//回复谁
        notification.setNotifiter(comment.getCommentator());//通知人
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());//状态
        notification.setReceiver(receiver);//评论人
        notification.setNotifierName(notifierName);//评论人名字
        notification.setOuterTitle(outerTitle);//评论标题
        notificationMapper.insert(notification);
    }

    /**
     * 通过id查询问题列表
     * @param userQuestionId
     * @param type
     * @return
     */
    public List<CommentDTO> listByTargetId(Long userQuestionId, CommentTypeEnum type) {

        //获取评论列表
        CommentExample example = new CommentExample();
        example.createCriteria()//通过id和类型查找
                .andParentIdEqualTo(userQuestionId)
                .andTypeEqualTo(type.getType());
        example.setOrderByClause("GMT_CREATE desc");
        List<Comment> commentList = commentMapper.selectByExample(example);
        if (commentList.size() == 0){
            return new ArrayList<>();
        }
        //遍历获取commentList所有的评论者。返回评论者集合.去重
        Set<Long> commentators = commentList.stream().map(Comment -> Comment.getCommentator()).collect(Collectors.toSet());
        //获取所有用户，通过commentators
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentators);
        //查询所有user集合
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        //遍历所有User集合，存入map
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(User -> User.getId(), User -> User));
        //遍历所有comment转换为commentDTO
        List<CommentDTO> commentDTOS = commentList.stream().map(Comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(Comment,commentDTO);
            commentDTO.setUser(userMap.get(Comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;
    }
}
