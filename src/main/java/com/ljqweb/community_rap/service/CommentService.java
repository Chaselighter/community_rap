package com.ljqweb.community_rap.service;

import com.ljqweb.community_rap.enums.CommentTypeEnum;
import com.ljqweb.community_rap.exception.CustomizeErrorCode;
import com.ljqweb.community_rap.exception.CustomizeException;
import com.ljqweb.community_rap.mapper.CommentMapper;
import com.ljqweb.community_rap.mapper.QuestionMapper;
import com.ljqweb.community_rap.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CommentService {
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    public void insert(Comment comment) {
        if(comment.getParentId()==null||comment.getParentId()==0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if(comment.getType()==null|| !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if(comment.getType()==CommentTypeEnum.COMMENT.getType()){
                //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbComment==null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        }else{
                //回复问题
                questionMapper.selectByPrimaryKey(comment.getParentId());
            }

    }
}
