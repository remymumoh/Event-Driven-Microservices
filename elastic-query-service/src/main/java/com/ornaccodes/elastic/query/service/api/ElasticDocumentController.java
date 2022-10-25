package com.ornaccodes.elastic.query.service.api;

import com.ornaccodes.elastic.query.service.business.ElasticQueryService;
import com.ornaccodes.elastic.query.service.model.ElasticQueryServiceRequestModel;
import com.ornaccodes.elastic.query.service.model.ElasticQueryServiceResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@RequestMapping(value = "/documents")
public class ElasticDocumentController {
    private static final Logger LOG = LoggerFactory.getLogger(ElasticDocumentController.class);

    private final ElasticQueryService elasticQueryService;

    public ElasticDocumentController(ElasticQueryService queryService) {
        this.elasticQueryService = queryService;
    }

    @GetMapping("/")
    public @ResponseBody
    ResponseEntity<List<ElasticQueryServiceResponseModel>> getAllDocuments(){
        List<ElasticQueryServiceResponseModel> response = elasticQueryService.getAllDocuments();
        LOG.info("Elasticsearch returned {} of documents", response.size());
        return  ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public @ResponseBody
    ResponseEntity<ElasticQueryServiceResponseModel> getDocumentById(@PathVariable @NotEmpty  String id){
        ElasticQueryServiceResponseModel elasticQueryServiceResponseModel = elasticQueryService.getDocumentById(id);
        LOG.info("Elasticsearch returned document with id {}", id);
        return ResponseEntity.ok(elasticQueryServiceResponseModel);
    }

    @PostMapping("/get-document-by-text")
    public @ResponseBody
    ResponseEntity<List<ElasticQueryServiceResponseModel>> getDocumentByText(@RequestBody ElasticQueryServiceRequestModel elasticQueryServiceRequestModel){
        List<ElasticQueryServiceResponseModel> response = elasticQueryService.getDocumentByText(elasticQueryServiceRequestModel.getText());
        LOG.info("Elasticsearch returned document with id {}", response.size());
        return ResponseEntity.ok(response);
    }
}
