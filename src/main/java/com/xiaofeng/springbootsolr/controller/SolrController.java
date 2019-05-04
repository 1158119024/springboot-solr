package com.xiaofeng.springbootsolr.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xiaofeng.springbootsolr.base.PageInfo;
import com.xiaofeng.springbootsolr.vo.UserVo;
import org.apache.catalina.User;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.SolrParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 晓枫
 * @Date: 2019/5/3 11:17
 * @Description:
 */
@RestController
@RequestMapping("/solr")
public class SolrController {
    @Autowired
    private SolrClient solrClient;

    @RequestMapping("/page")
    public List<UserVo> page(PageInfo pageInfo) throws Exception{
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.add("q", "*:*");
        solrQuery.setStart(pageInfo.getRecordStart());
        solrQuery.setRows(pageInfo.getSize());
        QueryResponse query = solrClient.query(solrQuery);
        SolrDocumentList results = query.getResults();

        List<UserVo> userVos = JSON.parseArray(JSON.toJSONString(results), UserVo.class);
        return userVos;
    }

    @RequestMapping("/list")
    public List<UserVo> list() throws Exception{
        SolrQuery solrQuery = new SolrQuery();
        // 模糊查询时 汉字两边不需要接*, 数字两边需要
//        solrQuery.add("q", "name:晓枫 and url:小蓝");

        solrQuery.add("q", "*:*");
        QueryResponse response = solrClient.query(solrQuery);
        SolrDocumentList results = response.getResults();
        System.out.println(results);
        results.forEach(solr -> {
            System.out.println(solr);
        });
        List<UserVo> userVos = JSON.parseArray(JSON.toJSONString(results), UserVo.class);
        return userVos;
    }

    @RequestMapping("/get")
    public UserVo get(Long id) throws Exception {
        SolrDocument solrDocument = solrClient.getById(id.toString());
        // 获取所有key名
        Collection<String> fieldNames = solrDocument.getFieldNames();
        System.out.println(fieldNames);
        // 获取所有键值对
        Map<String, Collection<Object>> fieldValuesMap = solrDocument.getFieldValuesMap();
        System.out.println(fieldValuesMap);
        // 获取所有键值对
        Map<String, Object> fieldValueMap = solrDocument.getFieldValueMap();
        System.out.println(fieldValueMap);
//        Integer childDocumentCount = solrDocument.getChildDocumentCount();
//        System.out.println(childDocumentCount);
        List<SolrDocument> childDocuments = solrDocument.getChildDocuments();
        System.out.println(childDocuments);
        System.out.println(solrDocument);
        UserVo userVo = JSON.parseObject(JSON.toJSONString(solrDocument), UserVo.class);
        System.out.println(userVo);
        return userVo;
    }

    @RequestMapping("/delete")
    public void delete(Long id) throws Exception {
        UpdateResponse updateResponse = solrClient.deleteById(id.toString());
        UpdateResponse commit = solrClient.commit();
        System.out.println(commit);
        System.out.println(updateResponse);
    }

    @RequestMapping("/save")
    public void save(UserVo userVo) throws Exception{
        UpdateResponse response = solrClient.addBean(userVo);
        solrClient.commit();
        System.out.println(response);
    }

}
