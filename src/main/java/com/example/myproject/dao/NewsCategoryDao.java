package com.example.myproject.dao;

import com.example.myproject.domain.NewsCategory;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface NewsCategoryDao {
    @Select("select * from `springboot_db`.`news_category` where id = #{id};")
    NewsCategory findById(NewsCategory newsCategory);

    @Select({
            "<script>",
            "select * from `springboot_db`.`news_category`",
            "where state = 1",
            "<when test='name!=null'>",
            "and name like concat('%',#{name},'%')",
            "</when>",
            "order by addDate desc limit #{start},#{end}",
            "</script>"
    })
    List<NewsCategory> list(NewsCategory newsCategory);

    @Select({
            "<script>",
            "select count(*) from `springboot_db`.`news_category`",
            "where state = 1",
            "<when test='name!=null'>",
            "and name like concat('%',#{name},'%')",
            "</when>",
            "</script>"
    })
    int count(NewsCategory newsCategory);

    @Insert("insert into `springboot_db`.`news_category` (`id`, `name`, `description`, `image`, `addDate`, `state`) VALUES (null, #{name}, #{description}, #{image}, now(), 1);")
    int insert(NewsCategory newsCategory);

    @Update("update `springboot_db`.`news_category`SET `name` = #{name}, `description` = #{description}, `image` = #{image} WHERE `id` = #{id};")
    int update(NewsCategory newsCategory);

    @Update("update `springboot_db`.`news_category`SET `state` = #{state} WHERE `id` = #{id};")
    int updateState(NewsCategory newsCategory);
}
