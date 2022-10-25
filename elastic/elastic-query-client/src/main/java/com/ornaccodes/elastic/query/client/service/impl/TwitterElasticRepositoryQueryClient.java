package com.ornaccodes.elastic.query.client.service.impl;

import com.ornaccodes.common.util.CollectionsUtil;
import com.ornaccodes.elastic.model.index.impl.TwitterIndexModel;
import com.ornaccodes.elastic.query.client.exception.ElasticQueryClientException;
import com.ornaccodes.elastic.query.client.repository.TwitterElasticsearchQueryRepository;
import com.ornaccodes.elastic.query.client.service.ElasticQueryClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Primary
@Service
public class TwitterElasticRepositoryQueryClient implements ElasticQueryClient<TwitterIndexModel> {
    
    private static final Logger LOG  = LoggerFactory.getLogger(TwitterElasticRepositoryQueryClient.class);
    private final TwitterElasticsearchQueryRepository twitterElasticsearchQueryRepository;

    public TwitterElasticRepositoryQueryClient(TwitterElasticsearchQueryRepository repository) {
        this.twitterElasticsearchQueryRepository = repository;
    }

    @Override
    public TwitterIndexModel getIndexModelById(String id) {
        Optional<TwitterIndexModel> searchResult = twitterElasticsearchQueryRepository.findById(id);
        LOG.info("Document with id {} retrieved successfuly",
                searchResult.orElseThrow(()->
            new ElasticQueryClientException("N document found at elasticsearch with id" + id)).getId());
        return searchResult.get();
    }

    @Override
    public List<TwitterIndexModel> getIndexModelByText(String text) {
        List<TwitterIndexModel> searchResult = twitterElasticsearchQueryRepository.findByText(text);
        LOG.info("{} of document with text {} retrieved successfuly", searchResult.size(), text);
        return searchResult;
    }

    @Override
    public List<TwitterIndexModel> getAllIndexModels() {
        List<TwitterIndexModel> searchResult =
                CollectionsUtil.getInstance().getListFromIterable(twitterElasticsearchQueryRepository.findAll());
        LOG.info("{} number of document retrieved successfuly", searchResult.size());
        return searchResult;
    }
}
