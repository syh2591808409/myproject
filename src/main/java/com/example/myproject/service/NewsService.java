package com.example.myproject.service;

import com.example.myproject.domain.News;

import java.util.List;

public interface NewsService {
    News findById(News news);
    List<News> list(News news);
    int count(News news);
    int insert(News news);
    int update(News news);
    int updateState(News news);
}
