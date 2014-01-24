package com.yonyou.nc.codevalidator.sdk.dbcreate;

import java.util.List;

/**
 * Լ������ӿڡ���������Լ����
 * <ol>
 * 	<li>����Լ��</li>
 * 	<li>���Լ��</li>
 * 	<li>Ψһ��Լ��</li>
 * </ol>
 * 
 * @author PH
 */
public interface IConstraint {
	
	public String getName();
	
	public ITable getTable();
	
	public List<IColumn> getColumns();

}