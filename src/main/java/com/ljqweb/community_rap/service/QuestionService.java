package com.ljqweb.community_rap.service;


import com.ljqweb.community_rap.dto.PageinationDTO;
import com.ljqweb.community_rap.dto.QuestionDTO;
import com.ljqweb.community_rap.mapper.QuestionMapper;
import com.ljqweb.community_rap.mapper.UserMapper;
import com.ljqweb.community_rap.model.Question;
import com.ljqweb.community_rap.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;
    public PageinationDTO List(Integer page, Integer size) {
        //size(page-1)
        PageinationDTO pageinationDTO = new PageinationDTO();
        Integer totalPage;
        Integer totalCount = questionMapper.count();

        if(totalCount%size==0){
            totalPage = totalCount/size;
        }else{
            totalPage=totalCount/size + 1;
        }
        if(page<1){
            page =1;
        }
        if(page>totalPage){
            page=totalPage;
        }

        pageinationDTO.setPageination(totalPage,page);
        List<Question> questions = new ArrayList<>();
        Integer offset;
        if(size*(page-1)<0){
            questions = questionMapper.list(0,0);
        }else{
            offset = size*(page-1);
            questions = questionMapper.list(offset,size);
        }



        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question :questions){
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO =new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageinationDTO.setQuestions(questionDTOList);

        return pageinationDTO;
    }

    public PageinationDTO list(Integer userId, Integer page, Integer size) {
        //size(page-1)
        PageinationDTO pageinationDTO = new PageinationDTO();
        Integer totalPage;
        Integer totalCount = questionMapper.countByUserId(userId);

        if(totalCount%size==0){
            totalPage = totalCount/size;
        }else{
            totalPage=totalCount/size + 1;
        }
        if(page<1){
            page =1;
        }
        if(page>totalPage){
            page=totalPage;
        }
        pageinationDTO.setPageination(totalPage,page);

        List<Question> questions = new ArrayList<>();
        Integer offset;
        if(size*(page-1)<0){
            questions = questionMapper.listByUserId(userId,0,0);
        }else{
            offset = size*(page-1);
            questions = questionMapper.listByUserId(userId,offset,size);
        }
        //List<Question> questions = questionMapper.listByUserId(userId,offset,size);

        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question :questions){
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO =new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageinationDTO.setQuestions(questionDTOList);

        return pageinationDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.GetById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if(question.getId()==null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
        }else{
            question.setGmtModified(question.getGmtCreate());
            questionMapper.update(question);
        }
    }
}
