package com.ljqweb.community_rap.dto;

import lombok.Data;

import java.util.List;
@Data
public class TagDTO {
    private String CategoryName;
    private List<String> tags;
}
