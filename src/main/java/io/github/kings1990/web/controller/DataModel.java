package io.github.kings1990.web.controller;

import io.github.kings1990.rap2.generator.config.ResponseResultData;
import io.github.kings1990.rap2.generator.config.ResponseResultType;
import io.github.kings1990.rap2.generator.config.Summary;
import lombok.Data;

import java.util.List;

@Data
public class DataModel {
    private String sid;
    private String sig;
    private String interfaceUrl;
    
    /**
     * 仓库id
     */
    private Integer repositoryId;
    /**
     * Body类型:FORM_DATA
     */
    private Summary.BodyOption bodyOption;

    /**
     * 参数形式:BODY_PARAMS,QUERY_PARAMS
     */
    private Summary.RequestParamsType requestParamsType;

    /**
     * 响应result类型
     */
    private ResponseResultType responseResultType;
    /**
     * result结果类型
     */
    private ResponseResultData responseResultData;
    
    
    private List<Rap2Data> requestData;
    private List<Rap2Data> responseData;
}


