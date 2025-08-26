package com.xuexi.dailytool.utils;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class PolicyLogBean {
    private String uuid;
    private Long id;
    private Long deviceId;
    private String date;
    private Date time;
    private Integer policyId;
    private String policyName;
    private String src;
    private String dst;
    private String protocol;
    private Integer srcPort;
    private Integer dstPort;
    private String srcZone;
    private String dstZone;
    private Integer action;
    private String keyType;
    private String vsys;
    private Integer vsysId;
    private Long frequency;
    private Date startTime;
    private Date endTime;
    private Long count;
    private Integer policyVersionId;
    private String deviceName;
    private String filterCondition;
    private String orderBy;
    private String orderMethod;
    private String unionTable;
    private String filterTableCondition;
    private String innerSelect;

    private String sqlTable;

    private Integer srcMaskLength = 32;

    private Integer dstMaskLength = 32;
}