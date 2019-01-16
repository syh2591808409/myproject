package com.example.myproject.service.impl;

import com.example.myproject.dao.NewsCategoryDao;
import com.example.myproject.domain.NewsCategory;
import com.example.myproject.service.NewsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsCategoryServiceImpl implements NewsCategoryService {

    @Autowired
    private NewsCategoryDao newsCategoryDao;

    public NewsCategory findById(NewsCategory newsCategory){
        return newsCategoryDao.findById(newsCategory);
    }
    public List<NewsCategory> list(NewsCategory newsCategory){
        return newsCategoryDao.list(newsCategory);
    }
    public int count(NewsCategory newsCategory){
        return newsCategoryDao.count(newsCategory);
    }
    public int insert(NewsCategory newsCategory){
        newsCategoryDao.insert(newsCategory);
        return 1;
    }
    public int update(NewsCategory newsCategory){
        newsCategoryDao.update(newsCategory);
        return 1;
    }
    public int updateState(NewsCategory newsCategory){
        newsCategoryDao.updateState(newsCategory);
        return 1;
    }

}
