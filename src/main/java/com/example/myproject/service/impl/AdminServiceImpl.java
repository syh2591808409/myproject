package com.example.myproject.service.impl;

import com.example.myproject.dao.AdminDao;
import com.example.myproject.domain.Admin;
import com.example.myproject.service.AdminService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;


    public Admin findByNameAndPassword(Admin admin){
        return adminDao.findByNameAndPassword(admin);
    }
    public Admin findByNameAndRealAndEmail(Admin admin){
        return adminDao.findByNameAndRealAndEmail(admin);
    }
    public Admin findByName(Admin admin){
        return adminDao.findByName(admin);
    }
    public Admin findByNameSingle(@Param("userName") String userName){
        return adminDao.findByNameSingle(userName);
    }
    public int insert(Admin admin){
        adminDao.insert(admin);
        return 1;
    }
    public int updatePasswordByUnameAndRname(Admin admin){
        adminDao.updatePasswordByUnameAndRname(admin);
        return 1;
    }
    public int updatePasswordByUname(String userName,String password){
        adminDao.updatePasswordByUname(userName,password);
        return 1;
    }
    public int updateRealNameByUname(String userName,String realName){
        adminDao.updateRealNameByUname(userName,realName);
        return 1;
    }
    public int updateProvinceAndCityByUname(String userName,String province,String city){
        adminDao.updateProvinceAndCityByUname(userName, province, city);
        return 1;
    }
    public int updateheadPictureByUname(String userName,String headPicture){
        adminDao.updateheadPictureByUname(userName,headPicture);
        return 1;
    }
//    public List<Admin> findByAdminWithPage(Admin admin, int start, int end){
//        return
//    }
//    public int insert(Admin admin){
//
//    }
//    public int updateStateById(int id){
//
//    }
//    public int deleteById(int id){
//
//    }
}
