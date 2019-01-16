package com.example.myproject.service;

import com.example.myproject.domain.NewsCategory;

import java.util.List;

public interface NewsCategoryService {
    NewsCategory findById(NewsCategory newsCategory);
    List<NewsCategory> list(NewsCategory newsCategory);
    int count(NewsCategory newsCategory);
    int insert(NewsCategory newsCategory);
    int update(NewsCategory newsCategory);
    int updateState(NewsCategory newsCategory);
}
