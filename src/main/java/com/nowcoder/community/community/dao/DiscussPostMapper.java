package com.nowcoder.community.community.dao;


import com.nowcoder.community.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {
                                                        // 起始行号 一页的上限
    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);
    //当需要动态sql查询一个条件，且只有一个参数，并在if中使用，一定要取别名，Param
    int selectDiscussPostRows(@Param("userId") int userId);



}
