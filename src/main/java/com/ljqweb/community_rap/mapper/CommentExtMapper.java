package com.ljqweb.community_rap.mapper;

import com.ljqweb.community_rap.model.Comment;
import com.ljqweb.community_rap.model.CommentExample;
import com.ljqweb.community_rap.model.Question;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}