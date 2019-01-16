package com.example.myproject.service;

import com.example.myproject.domain.Admin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminService {
    Admin findByNameAndPassword(Admin admin);
    Admin findByNameAndRealAndEmail(Admin admin);
    Admin findByName(Admin admin);
    Admin findByNameSingle(@Param("userName") String userName);
    int insert(Admin admin);
    int updatePasswordByUnameAndRname(Admin admin);
    int updatePasswordByUname(String userName,String password);
    int updateRealNameByUname(String userName,String realName);
    int updateProvinceAndCityByUname(String userName,String province,String city);
    int updateheadPictureByUname(String userName,String headPicture);
//    List<Admin> findByAdminWithPage(Admin admin, int start, int end);
//    int updateStateById(int id);
//    int deleteById(int id);
}
