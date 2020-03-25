package com.wuxiu.forum.mapper;

import com.wuxiu.forum.dto.QuestionDTO;
import com.wuxiu.forum.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("INSERT INTO question (title,description,gmt_create,gmt_modified,creator,tag) VALUES (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

    @Select("SELECT * FROM question LIMIT #{offSet},#{pageSize}")
    List<Question> getList(@Param("offSet") Integer offSet, @Param("pageSize")Integer pageSize);

    @Select("SELECT COUNT(1) from question")
    Integer getQuestionTotal();

    @Select("SELECT * FROM question WHERE creator = #{userId}  LIMIT #{offSet},#{pageSize}")
    List<Question> getProfileList(@Param("userId") Integer userId, @Param("offSet") Integer offSet,@Param("pageSize") Integer pageSize);

    @Select("SELECT COUNT(1) from question WHERE creator = #{userId}")
    Integer getUserQuestionListByUserId(Integer userId);

    @Select("SELECT * from question WHERE id = #{userQuestionId}")
    Question getUserQuestionByQuestionId(Integer userQuestionId);
}
