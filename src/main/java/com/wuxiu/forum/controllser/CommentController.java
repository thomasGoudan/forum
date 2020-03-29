package com.wuxiu.forum.controllser;

import com.wuxiu.forum.dto.CommentCreateDTO;
import com.wuxiu.forum.dto.CommentDTO;
import com.wuxiu.forum.dto.ResultDTO;
import com.wuxiu.forum.enums.CommentTypeEnum;
import com.wuxiu.forum.exception.CustomizeErrorCode;
import com.wuxiu.forum.model.Comment;
import com.wuxiu.forum.model.User;
import com.wuxiu.forum.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 评论控制器
 */
@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;


    /**
     * 获取与一级评论
     * @param commentCreateDTO
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO, HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if (user == null){
            return ResultDTO.errorOf(CustomizeErrorCode.NOT_LOGIN);
        }
        if(commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())){
            return ResultDTO.errorOf(CustomizeErrorCode.COMMENT_is_EMPTY);
        }
        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        comment.setCommentCount(0);
        commentService.insert(comment,user);
        return ResultDTO.succeedOf();

    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    public ResultDTO<List<CommentDTO>> comments(@PathVariable(name = "id")Long id){
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okof(commentDTOS);
    }
}
