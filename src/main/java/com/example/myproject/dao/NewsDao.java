package com.example.myproject.dao;

import com.example.myproject.domain.News;
import com.example.myproject.service.Constant;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface NewsDao {
    @Select("select * from `springboot_db`.`news` where id = #{id};")
    News findById(News news);

    @Select({
            "<script>",
            "select N.*,C.name as CATEGORYNAME,C.image AS CATEGORYIMAGE from springboot_db.news N ",
            "left join springboot_db.news_category C on N.category = C.id ",
            "where N.state = 1 ",
            "<when test='title!=null'>",
            "and N.title like concat('%',#{title},'%')",
            "</when>",
            "<when test='category!=0'>",
            "and category = #{category}",
            "</when>",
            "<when test='commendState!=0'>",
            "and commendState = #{commendState}",
            "</when>",
            "<when test='orderBy==\""+ Constant.OrderByAddDateAsc+"\"'>",
            "order by "+Constant.OrderByAddDateAsc+",addDate desc",
            "</when>",
            "<when test='orderBy==\""+Constant.OrderByAddDateDesc+"\"'>",
            "order by "+Constant.OrderByAddDateDesc,
            "</when>",
            "<when test='orderBy==\""+Constant.OrderByBrowsesDesc+"\"'>",
            "order by "+Constant.OrderByBrowsesDesc+",addDate desc",
            "</when>",
            "<when test='orderBy==\""+Constant.OrderByCommentsDesc+"\"'>",
            "order by "+Constant.OrderByCommentsDesc+",addDate desc",
            "</when>",
            "<when test='orderBy==\""+Constant.OrderByLikesDesc+"\"'>",
            "order by "+Constant.OrderByLikesDesc+",addDate desc",
            "</when>",
            "<when test='orderBy==\""+Constant.OrderByScoreDesc+"\"'>",
            "order by "+Constant.OrderByScoreDesc+",addDate desc",
            "</when>",
            "limit #{start},#{end}",
            "</script>"
    })
    List<News> list(News news);

    @Select({
            "<script>",
            "select count(*) from springboot_db.news N ",
            "LEFT JOIN springboot_db.news_category C on N.category = C.id ",
            "where N.state = 1 ",
            "<when test='title!=null'>",
            "and N.title like concat('%',#{title},'%')",
            "</when>",
            "<when test='category!=0'>",
            "and category = #{category}",
            "</when>",
            "<when test='commendState!=0'>",
            "and commendState = #{commendState}",
            "</when>",
            "</script>"
    })
    int count(News news);

    @Insert("insert into `springboot_db`.`news` (`id`,`title`,`description`,`category`,`image`,`content`,`addDate`,`updateDate`,`commendState`,`state`,`browses`,`likes`,`comments`,`score`) VALUES (null,#{title},#{description},#{category},#{image},#{content},now(),now(),1,1,0,0,0,0);")
    int insert(News news);

    @Update("update `springboot_db`.`news` SET `title` = #{title}, `description` = #{description}, `category` = #{category}, `image` = #{image}, `content` = #{content}, `updateDate` = now()  WHERE `id` = #{id};")
    int update(News news);

    @Update("update `springboot_db`.`news` SET `state` = #{state}, `commendState` = #{commendState}, `browses` = #{browses}, `likes` = #{likes}, `comments` = #{comments}, `score` = #{score} WHERE `id` = #{id};")
    int updateState(News news);

}
