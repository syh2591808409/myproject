package com.example.myproject.dao;

import com.example.myproject.domain.Admin;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
@Mapper
public interface AdminDao {
    @Select("select * from `springboot_db`.`admin` where userName = #{userName} and password = #{password} and state = 0;")
    Admin findByNameAndPassword(Admin admin);

    @Select("select * from `springboot_db`.`admin` where userName = #{userName} and realName = #{realName} and emailAddress = #{emailAddress} and state = 0;")
    Admin findByNameAndRealAndEmail(Admin admin);

    @Select("select * from `springboot_db`.`admin` where userName = #{userName}  and state = 0;")
    Admin findByName(Admin admin);

    @Select("select * from `springboot_db`.`admin` where userName = #{userName}  and state = 0;")
    Admin findByNameSingle(@Param("userName") String userName);

    @Insert("insert into `springboot_db`.`admin` (`id`, `userName`, `password`, `realName`, `emailAddress`, `sex`, `province`, `city`, `age`, `phoneNumber`, `headPicture`, `addDate`, `updateDate`, `state`) VALUES (null, #{userName}, #{password}, #{realName}, #{emailAddress}, #{sex}, #{province}, #{city}, #{age}, #{phoneNumber}, #{headPicture}, now(), now(), 0);")
    int insert(Admin admin);

    @Update("update `springboot_db`.`admin`  set  `password` = #{password} where `userName` = #{userName} and `realName` = #{realName};")
    int updatePasswordByUnameAndRname(Admin admin);

    @Update("update `springboot_db`.`admin`  set  `password` = #{password} where `userName` = #{userName};")
    int updatePasswordByUname(@Param("userName") String userName,@Param("password") String password);

    @Update("update `springboot_db`.`admin`  set  `realName` = #{realName} where `userName` = #{userName};")
    int updateRealNameByUname(@Param("userName") String userName,@Param("realName") String realName);

    @Update("update `springboot_db`.`admin`  set  `province` = #{province},`city` = #{city} where `userName` = #{userName};")
    int updateProvinceAndCityByUname(@Param("userName") String userName,@Param("province") String province,@Param("city") String city);

    @Update("update `springboot_db`.`admin`  set  `headPicture` = #{headPicture} where `userName` = #{userName};")
    int updateheadPictureByUname(@Param("userName") String userName,@Param("headPicture")String headPicture);
    //    @Select("select * from `springboot_db`.`admin` where userName = #{userName} and password = #{password} and realName = #{realName}")
//    List<Admin> findByAdminWithPage(Admin admin, int start, int end);
//

//
//    @Update("update `springboot_db`.`admin`  set  `userName` = #{userName}, `password` = #{password}, `realName` = #{realName}, `age` = #{age}, `phoneNumber` = #{phoneNumber}, `headPicture` = #{headPicture}, `updateDate` = now(), `state` = #{state} where `id` = #{id};")
//    int updateStateById(int id);
//    @Delete("delete from `springboot_db`.`admin` where id = #{id}")
//    int deleteById(int id);
}
