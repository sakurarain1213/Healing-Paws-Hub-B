package com.example.hou.util;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.alibaba.fastjson.JSON;
import com.example.hou.entity.EsQueryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ESUtils<T> {
    @Autowired
    private ElasticsearchClient client;


    /**
     *  模糊搜索api
     */
    public List<T> fuzzyQueryByPage(String indexName, String text, List<String> fields,
                                    Integer pageNum, Integer pageSize,
                                    Class<T> target) throws Exception{

        SearchResponse<HashMap> response = client.search(s -> s
                .index(indexName)
                .query(q -> q
                        .multiMatch(m -> m
                                .query(text).fields(fields)
                        )
                )
                .from(pageNum)
                .size(pageSize), HashMap.class);

        return hitToTarget(response.hits().hits(), target);
    }

    /** 供参考，暂未使用
     *  根据关键字分页查询
     */
    public List<T> queryByPage(EsQueryDTO dto, Class<T> target) throws Exception {
        List<T> result = new ArrayList<>();
        SearchResponse<HashMap> search = client.search(s -> s
                        .index(dto.getIndexName())
                        .query(q -> q.term(t -> t
                                .field(dto.getField())
                                .value(dto.getWord())
                        ))
                        .from(dto.getFrom())
                        .size(dto.getSize()),
                HashMap.class);

        List<Hit<HashMap>> hits = search.hits().hits();
        Iterator<Hit<HashMap>> iterator = hits.iterator();
        while (iterator.hasNext()){
            Hit<HashMap> decodeBeanHit = iterator.next();
            Map<String,Object> docMap = decodeBeanHit.source();
            String json = JSON.toJSONString(docMap);
            T obj  = JSON.parseObject(json, target);
            result.add(obj);
        }
        return result;
    }

    /** 供参考，暂未使用
     * 根据关键字查询记录总数
     */
    public long queryCountByPage(EsQueryDTO dto) throws Exception {
        CountResponse response = client.count(c -> c.index(dto.getIndexName())
                .query(q -> q.term(
                        t -> t.field(dto.getField()).value(dto.getWord())
                ))
        );
        return response.count();
    }

    /** 供参考，暂未使用
     * 根据文档id查询
     */
    public T queryDocById(EsQueryDTO dto, Class<T> target) throws Exception{
        GetResponse<HashMap> response = client.get(s -> s
                        .index(dto.getIndexName())
                        .id(dto.getWord()),
                HashMap.class
        );
        HashMap<String, Object> doc = response.source();
        String jsonDoc = JSON.toJSONString(doc);
        T res = JSON.parseObject(jsonDoc, target);
        return res;
    }

    public List<T> hitToTarget(List<Hit<HashMap>> hits, Class<T> target){
        List<T> result = new ArrayList<>();

        for(Hit<HashMap> hit : hits){
            Map<String,Object> docMap = hit.source();
            docMap.put("id", hit.id());

            String json = JSON.toJSONString(docMap);
            T doc  =  JSON.parseObject(json, target);
            result.add(doc);
        }

        return result;
    }

}
/*

package com.example.hou.util;

import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.search.TrackHits;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import com.example.hou.config.ElasticSearchConfig;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class ESUtils {
    @Autowired
    private ElasticSearchConfig config;

    /**
     * 增加index
     * @throws IOException

public void createIndex(String index) throws IOException {
        //写法比RestHighLevelClient更加简洁
        CreateIndexResponse indexResponse = config.client().indices().create(c -> c.index(index));
        }

/**
 * 查询Index
 * @throws  java.io.IOException
 *
public GetIndexResponse queryIndex(String index) throws IOException {
        GetIndexResponse getIndexResponse = config.client().indices().get(i -> i.index(index));
        return getIndexResponse;
        }

/**
 *  判断index是否存在
 * @return
 * @throws java.io.IOException
 *
public boolean existsIndex(String index) throws IOException {
        BooleanResponse booleanResponse = config.client().indices().exists(e -> e.index(index));
        return booleanResponse.value();
        }

/**
 * 删除index
 * @param index
 * @return
 * @throws java.io.IOException

public DeleteIndexResponse deleteIndex(String index) throws IOException {
        DeleteIndexResponse deleteIndexResponse = config.client().indices().delete(d -> d.index(index));
        return deleteIndexResponse;
        }

/**
 * 插入数据

public IndexResponse document(String index, String id, T t) throws IOException {
        IndexResponse indexResponse = config.client().index(i -> i
        .index(index)
        //设置id
        .id(id)
        //传入user对象
        .document(t));
        return indexResponse;
        }

/**
 * 批量插入Document

public BulkResponse documentAll(String index, List<BulkOperation> bulkOperationArrayList) throws IOException {
        BulkResponse bulkResponse = config.client().bulk(b -> b.index(index)
        .operations(bulkOperationArrayList));
        return bulkResponse;
        }

/**
 * 更新Document
 * @throws java.io.IOException

public UpdateResponse updateDocumentIndex(String index, String id, T t) throws IOException {
        UpdateResponse<T> updateResponse = config.client().update(u -> u
        .index(index)
        .id(id)
        .doc(t)
        , T.class);
        return updateResponse;
        }

/**
 * 判断Document是否存在
 * @throws java.io.IOException

public BooleanResponse existDocumentIndex(String index) throws IOException {
        BooleanResponse indexResponse = config.client().exists(e -> e.index(index).id("1"));
        return indexResponse;
        }


/**
 * 查询Document
 * @throws java.io.IOException

public GetResponse getDocumentIndex(String index, String id, T t) throws IOException {
        GetResponse<T> getResponse = config.client().get(g -> g
        .index(index)
        .id(id)
        , T.class
        );
                return getResponse;
                }

/**
 * 分页查询[精确查找]
 * @param index
 * @param map
 * @return
 * @throws java.io.IOException

public SearchResponse<T> searchPage(String index, Map<String, Object> map) throws IOException {
        SearchResponse<T> search = config.client().search(s -> s
        .index(index)
        //查询name字段包含hello的document(不使用分词器精确查找)
        .query(q -> q
        .term(e -> e
        .field(map.get("key").toString())
        .value(v -> v.stringValue(map.get("value").toString()))
        ))
        .trackTotalHits(new TrackHits.Builder().enabled(true).build())
        //分页查询
        .from((Integer) map.get("pageNum"))
        .size((Integer) map.get("pageSize"))
        //按age降序排序
        .sort(f->f.field(o->o.field("createDate").order(SortOrder.Desc))),T.class
        );
                return search;
                }

/**
 * 删除Document
 * @throws java.io.IOException

public DeleteResponse deleteDocumentIndex(String index, String id) throws IOException {
        DeleteResponse deleteResponse = config.client().delete(d -> d
        .index(index)
        .id(id)
        );
        return deleteResponse;
        }
        }






*/