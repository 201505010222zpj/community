package com.zpjstudy.community.Service;

import com.zpjstudy.community.dto.PaginationDTO;
import com.zpjstudy.community.dto.QuestionDTO;
import com.zpjstudy.community.mapper.QuestionMapper;
import com.zpjstudy.community.mapper.UserMapper;
import com.zpjstudy.community.model.Question;
import com.zpjstudy.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;

    public PaginationDTO list(Integer page, Integer size) {


        Integer totalCount = questionMapper.count();
        PaginationDTO pagination = new PaginationDTO();
        pagination.setPagination(totalCount,page,size);


        if(page<1) page = 1;
        else if(page>pagination.getTotalPage()) page = pagination.getTotalPage();


        Integer offset = (page-1)*size;
        List<Question> questionList= questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();



        for (Question question : questionList) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pagination.setQuestions(questionDTOList);


        return pagination;
    }

    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        Integer totalCount = questionMapper.countByUserId(userId);
        PaginationDTO pagination = new PaginationDTO();
        pagination.setPagination(totalCount,page,size);


        if(page<1) page = 1;
        else if(page>pagination.getTotalPage()) page = pagination.getTotalPage();


        Integer offset = (page-1)*size;
        List<Question> questionList= questionMapper.listByUserId(userId,offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();



        for (Question question : questionList) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pagination.setQuestions(questionDTOList);


        return pagination;
    }
}
