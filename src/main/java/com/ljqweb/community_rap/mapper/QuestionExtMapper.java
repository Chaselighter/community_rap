package com.ljqweb.community_rap.mapper;

import com.ljqweb.community_rap.dto.QuestionQueryDTO;
import com.ljqweb.community_rap.model.Question;
import com.ljqweb.community_rap.model.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface QuestionExtMapper {
    int incView(Question record);
    int incCommentCount(Question record);
    List<Question> selectRelated(Question question );

    Integer countBySearch(QuestionQueryDTO questionQueryDTO);

    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);
}