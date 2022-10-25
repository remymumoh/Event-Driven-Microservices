package com.ornaccodes.elastic.query.client.service.impl;

import com.ornaccodes.config.ElasticConfigData;
import com.ornaccodes.config.ElasticQueryConfigData;
import com.ornaccodes.elastic.model.index.impl.TwitterIndexModel;
import com.ornaccodes.elastic.query.client.exception.ElasticQueryClientException;
import com.ornaccodes.elastic.query.client.service.ElasticQueryClient;
import com.ornaccodes.elastic.query.client.util.ElasticQueryUtil;
import org.elasticsearch.index.query.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TwitterElasticQueryClient implements ElasticQueryClient<TwitterIndexModel> {
    private static final Logger LOG = LoggerFactory.getLogger(TwitterElasticQueryClient.class);

    private final ElasticConfigData elasticConfigData;
    private final ElasticQueryConfigData elasticQueryConfigData;
    private final ElasticsearchOperations elasticsearchOperations;
    private final ElasticQueryUtil<TwitterIndexModel> elasticQueryUtil;


    public TwitterElasticQueryClient(ElasticConfigData configData,
                                     ElasticQueryConfigData queryConfigData,
                                     ElasticsearchOperations searchOperations,
                                     ElasticQueryUtil<TwitterIndexModel> queryUtil) {
        this.elasticConfigData = configData;
        this.elasticQueryConfigData = queryConfigData;
        this.elasticsearchOperations = searchOperations;
        this.elasticQueryUtil = queryUtil;
    }


    @Override
    public TwitterIndexModel getIndexModelById(String id) {
        Query query = elasticQueryUtil.getSearchQueryById(id);
        SearchHit<TwitterIndexModel> searchResult = elasticsearchOperations.searchOne(query, TwitterIndexModel.class,
                IndexCoordinates.of(elasticConfigData.getIndexName()));
        if(searchResult == null){
            LOG.error("No Document found t elasticsearch with id {}", id);
            throw new ElasticQueryClientException("No Document found at elasticsearch with id " + id);
        }
        LOG.info("Document with id {} retrieved successfully", searchResult.getId());
        return searchResult.getContent();
    }

    @Override
    public List<TwitterIndexModel> getIndexModelByText(String text) {
        Query query = elasticQueryUtil.getSearchQueryByFieldText(elasticQueryConfigData.getTextField(), text);
        SearchHits<TwitterIndexModel> searchResult = elasticsearchOperations.search(query, TwitterIndexModel.class,
                IndexCoordinates.of(elasticConfigData.getIndexName()));
        LOG.info("{} of documents with text {} retrieved successfully", searchResult.getTotalHits(), text);
        return searchResult.get().map(SearchHit::getContent).collect(Collectors.toList());
    }

    @Override
    public List<TwitterIndexModel> getAllIndexModels() {
        Query query = elasticQueryUtil.getSearchQueryForAll();
        SearchHits<TwitterIndexModel> searchResult = elasticsearchOperations.search(query, TwitterIndexModel.class,
                IndexCoordinates.of(elasticConfigData.getIndexName()));
        LOG.info("{} of documents with text {} retrieved successfully", searchResult.getTotalHits());
        return searchResult.get().map(SearchHit::getContent).collect(Collectors.toList());
    }
}
