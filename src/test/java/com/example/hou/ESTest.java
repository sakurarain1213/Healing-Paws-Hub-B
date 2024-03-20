package com.example.hou;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * @program: Healing-Paws-Hub-B
 * @description:   ES8版本结合springboot的实现上 适合用Elasticsearch Java API Client
 * @author: 作者
 * @create: 2024-03-14 16:33
 */
@SpringBootTest
public class ESTest {

    @Test
    public void createIndex() throws IOException {


    }
}



/*
springboot+ES8的相关操作可以参考
https://blog.csdn.net/qq_24473507/article/details/123924463
有三篇系列文章


增加
User user = new User();
user.setUserName("aaa");
user.setAge(12);
String data = JSON.toJSONString(user);
esUtil.addDocument("aaa-*", data);



批量增加
List<User > datas = new ArrayList<>();
User user = new User();
user.setUserName("aaa");
user.setAge(12);
datas.add(user);
if (CollectionUtils.isNotEmpty(datas)) {
                if (!esUtil.existsIndex("aaa-*")) {
                    esUtil.createIndex("aaa-*");
                }
                List<BulkOperation> bulkOperationArrayList = new ArrayList<>();
                //遍历添加到bulk中
                for (User obj : datas) {
                    bulkOperationArrayList.add(BulkOperation.of(o -> o.index(i -> i.document(obj))));
                }
                esUtil.documentAll("aaa-*", bulkOperationArrayList);
            }



批量删除
List<Query> queryList = new ArrayList<>();
Query queryTime = MatchQuery.of(m -> m.field("age").query(12))._toQuery();
queryList.add(queryTime);
Query queryAppId = MatchQuery.of(m -> m.field("userName.keyword").query("aaa"))._toQuery();
queryList.add(queryAppId);
SearchResponse<User> search = config.client().search(builder ->
builder.index("aaa-*")
.query(q -> q.bool(b -> b.must(queryList)))
.trackTotalHits(new TrackHits.Builder().enabled(true).build())
,User.class);
        List<BulkOperation> list = new ArrayList<>();
        List<Hit<User>> hitList = search.hits().hits();
        for (Hit<User> hit : hitList) {
            list.add(new BulkOperation.Builder().delete(d-> d.index(hit.index()).id(hit.id())).build());
        }
        config.client().bulk(e->e.index("aaa-*").operations(list));


查
List<Query> queryList = new ArrayList<>();
Query queryTime = MatchQuery.of(m -> m.field("age").query(12))._toQuery();
queryList.add(queryTime);
Query queryAppId = MatchQuery.of(m -> m.field("userName.keyword").query("aaa"))._toQuery();
queryList.add(queryAppId);
SearchResponse<User> search = config.client().search(builder ->
builder.index("aaa-*")
.query(q -> q.bool(b -> b.must(queryList)))
.trackTotalHits(new TrackHits.Builder().enabled(true).build())
,User.class);
List<Hit<User>> hitList = search.hits().hits();


分页查
Integer pageNum = 1;
Integer pageSize = 10;
// 动态组装查询条件
List<Query> queryList = new ArrayList<>();
Query queryTime = MatchQuery.of(m -> m.field("age").query(12))._toQuery();
queryList.add(queryTime);
Query queryAppId = MatchQuery.of(m -> m.field("userName.keyword").query("aaa"))._toQuery();
queryList.add(queryAppId);
// 分页查询
SearchResponse<User> search = config.client().search(builder ->
builder.index("aaa-*")
.query(q -> q.bool(b -> b.must(queryList)))
.from((pageNum - 1) * pageSize)
.trackTotalHits(new TrackHits.Builder().enabled(true).build())
.size(pageSize),User.class);
List<Hit<User>> hitList = search.hits().hits();
   List<User> list = new ArrayList<>();
      for (Hit<User> hit : hitList) {
          list.add(hit.source());
      }



聚合查
List<Query> queryList = new ArrayList<>();
        if (com.zdww.common.core.utils.StringUtils.isNotEmpty(time)) {
            Query queryTime = MatchQuery.of(m -> m.field("time").query(time))._toQuery();
            queryList.add(queryTime);
        }
        if (com.zdww.common.core.utils.StringUtils.isNotEmpty(userId)) {
            Query queryUserId = MatchQuery.of(m -> m.field("userId").query(userId))._toQuery();
            queryList.add(queryUserId);
        }
        SearchRequest searchRequest = new SearchRequest.Builder()
                //0:结果不返回列表，只返回结果
                .size(0)
                .index("aaa-*")
                .query(q -> q.bool(b -> b.must(queryList)))
                .aggregations("userId_aggs", userIdAgg ->
                        userIdAgg.terms(aterm -> aterm.field("userId.keyword")
                        //分组查询时，显示10000个，默认只查出10个
                        .size(10000))
                                .aggregations("age_aggs", ageAgg ->
                                        ageAgg.terms(oterm -> oterm.field("age.keyword")
                                        //分组查询时，显示10000个，默认只查出10个
                                        .size(10000))
                                                .aggregations("avgTime", avgsum ->
                                                                                avgsum.sum(sum -> sum.field("timeLong")))
                                                                        .aggregations("userIdCount", cardagg ->
                                                                                cardagg.cardinality(car -> car.field("userId.keyword")))
                                                                        .aggregations("count", useragg ->
                                                                                useragg.valueCount(u -> u.field("userId.keyword")))
                                                                        .aggregations("timeCount", csagg ->
                                                                                csagg.valueCount(u -> u.field("time")))
                                                        )
                                )
                ).build();

上述也就是SQL（      SELECT
        time,
        userId,
        age,
        count( DISTINCT userId ) AS userIdCount ,
        count( 1 ) AS count,
        count(time) as timeCount,
        (CAST(IFNULL(ROUND(SUM(timeLong) / count(userId), 2), 0) as DECIMAL(10,2))) AS avgTime
        FROM
        user
        where 1=1
        <if test="time != null and time != ''"> and time = #{time}</if>
        <if test="userId!= null"> and userId= #{userId}</if>
        GROUP BY
        time,userId,age
）




 */
