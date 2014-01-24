package com.yonyou.nc.codevalidator.persistence.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "rv_rule_result")
public class RuleExecuteResultVO implements Serializable, IEntityIdentifier {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4751443932627358807L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private String resultId;
	/**
	 * ִ�е�ԪID
	 */
	@ManyToOne(cascade = CascadeType.MERGE, optional = false)
	private ExecuteUnitVO executeUnit;
	/**
	 * ����ID
	 */
	@ManyToOne(cascade = CascadeType.MERGE, optional = false)
	private RuleDefinitionVO ruleDefinition;
	/**
	 * ִ�м�¼ID
	 */
	@ManyToOne(cascade = CascadeType.MERGE, optional = false)
	private ExecuteRecordVO executeRecord;
	/**
	 * �������
	 */
	@Column(name = "spec_params")
	private String specParams;
	/**
	 * �Ƿ�ͨ��
	 */
	@Column(name = "passflag")
	private String passFlag;

	@Column(name = "executelevel")
	private String executeLevel;

	/**
	 * ���н��
	 */
	@Column(name = "result")
	private String result;

	/**
	 * ���н����ϸ��Ϣ
	 */
	@Column(name = "reulst_detail")
	@Lob
	private String resultDetail;

	public String getSpecParams() {
		return specParams;
	}

	public void setSpecParams(String specParams) {
		this.specParams = specParams;
	}

	public String getPassFlag() {
		return passFlag;
	}

	public void setPassFlag(String passFlag) {
		this.passFlag = passFlag;
	}

	public String getResultId() {
		return resultId;
	}

	public void setResultId(String resultId) {
		this.resultId = resultId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getResultDetail() {
		return resultDetail;
	}

	public void setResultDetail(String resultDetail) {
		this.resultDetail = resultDetail;
	}

	public ExecuteUnitVO getExecuteUnit() {
		return executeUnit;
	}

	public void setExecuteUnit(ExecuteUnitVO executeUnit) {
		this.executeUnit = executeUnit;
	}

	public RuleDefinitionVO getRuleDefinition() {
		return ruleDefinition;
	}

	public void setRuleDefinition(RuleDefinitionVO ruleDefinition) {
		this.ruleDefinition = ruleDefinition;
	}

	public ExecuteRecordVO getExecuteRecord() {
		return executeRecord;
	}

	public void setExecuteRecord(ExecuteRecordVO executeRecord) {
		this.executeRecord = executeRecord;
	}

	public String getExecuteLevel() {
		return executeLevel;
	}

	public void setExecuteLevel(String executeLevel) {
		this.executeLevel = executeLevel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((resultId == null) ? 0 : resultId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RuleExecuteResultVO other = (RuleExecuteResultVO) obj;
		if (resultId == null) {
			if (other.resultId != null)
				return false;
		} else if (!resultId.equals(other.resultId))
			return false;
		return true;
	}

	@Override
	public String getId() {
		return resultId;
	}

}