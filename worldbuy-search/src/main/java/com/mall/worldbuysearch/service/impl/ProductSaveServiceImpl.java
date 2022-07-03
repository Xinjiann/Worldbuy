package com.mall.worldbuysearch.service.impl;

import com.alibaba.fastjson.JSON;
import com.mall.common.to.es.SkuEsModel;
import com.mall.worldbuysearch.config.MallElasticSearchConfig;
import com.mall.worldbuysearch.constant.EsConstant;
import com.mall.worldbuysearch.service.ProductSaveService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Title: ProductSaveServiceImpl</p>
 * Description：
 * date：2020/6/8 21:16
 */
@Slf4j
@Service
public class ProductSaveServiceImpl implements ProductSaveService {

	@Resource
	private RestHighLevelClient client;

	/**
	 * save data to ES
	 * BulkRequest bulkRequest, RequestOptions options
	 */
	@Override
	public boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException {
		// 1.create a index product
		BulkRequest bulkRequest = new BulkRequest();
		// 2.set request
		for (SkuEsModel esModel : skuEsModels) {
			IndexRequest indexRequest = new IndexRequest(EsConstant.PRODUCT_INDEX);
			// set index id
			indexRequest.id(esModel.getSkuId().toString());
			String jsonString = JSON.toJSONString(esModel);
			indexRequest.source(jsonString, XContentType.JSON);
			bulkRequest.add(indexRequest);
		}
		BulkResponse bulk = client.bulk(bulkRequest, MallElasticSearchConfig.COMMON_OPTIONS);
		// TODO wether error
		boolean hasFailures = bulk.hasFailures();
		if(hasFailures){
			List<String> collect = Arrays.stream(bulk.getItems()).map(item -> item.getId()).collect(Collectors.toList());
			log.error("goods on error：{}",collect);
		}
		return hasFailures;
	}
}
