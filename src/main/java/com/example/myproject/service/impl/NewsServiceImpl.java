package com.example.myproject.service.impl;

import com.example.myproject.dao.NewsDao;
import com.example.myproject.domain.News;
import com.example.myproject.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsDao newsDao;

    public News findById(News news){
        return newsDao.findById(news);
    }

    public List<News> list(News news){
        return newsDao.list(news);
    }
    public int count(News news){
        return newsDao.count(news);
    }
    public int insert(News news){
        newsDao.insert(news);
        return 1;
    }
    public int update(News news){
        newsDao.update(news);
        return 1;
    }
    public int updateState(News news){
        newsDao.updateState(news);
        return 1;
    }

}
