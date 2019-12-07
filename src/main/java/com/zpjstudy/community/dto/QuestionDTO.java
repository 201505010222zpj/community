package com.zpjstudy.community.dto;

import com.zpjstudy.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer commentCount;
    private Integer likeCount;
    private Integer ViewCount;
    private User user;
}
