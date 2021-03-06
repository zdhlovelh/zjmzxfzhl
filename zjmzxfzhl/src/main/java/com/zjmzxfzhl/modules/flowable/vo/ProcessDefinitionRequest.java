package com.zjmzxfzhl.modules.flowable.vo;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 庄金明
 * @date 2020年3月24日
 */
@Getter
@Setter
public class ProcessDefinitionRequest {
	private String processDefinitionId;
	private boolean includeProcessInstances = false;
	private Date date;
}
