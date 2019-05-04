package com.xiaofeng.springbootsolr.vo;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: 晓枫
 * @Date: 2019/5/3 11:19
 * @Description:
 */
@Data
@Accessors(chain = true)
public class UserVo implements Serializable{

    @Field("id")
    private Long id;
    // 如果是String, solr 中存的是["name"]字符串
    @Field("name")
    private String name;
    @Field("password")
    private String password;
    @Field("url")
    private String url;
    @Field("title")
    private String title;
}
