package com.yonyou.nc.codevalidator.plugin.domain.mm.code.afterrule;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.stmt.BlockStmt;
import japa.parser.ast.stmt.Statement;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yonyou.nc.codevalidator.plugin.domain.mm.util.MMMapUtil;
import com.yonyou.nc.codevalidator.plugin.domain.mm.util.MapList;
import com.yonyou.nc.codevalidator.resparser.JavaResourceQuery;
import com.yonyou.nc.codevalidator.resparser.defaultrule.AbstractJavaQueryRuleDefinition;
import com.yonyou.nc.codevalidator.resparser.executeresult.ResourceRuleExecuteResult;
import com.yonyou.nc.codevalidator.resparser.resource.JavaClassResource;
import com.yonyou.nc.codevalidator.rule.IRuleExecuteContext;
import com.yonyou.nc.codevalidator.rule.IRuleExecuteResult;
import com.yonyou.nc.codevalidator.rule.annotation.CatalogEnum;
import com.yonyou.nc.codevalidator.rule.annotation.ExecuteLayer;
import com.yonyou.nc.codevalidator.rule.annotation.ExecutePeriod;
import com.yonyou.nc.codevalidator.rule.annotation.RuleDefinition;
import com.yonyou.nc.codevalidator.rule.annotation.SubCatalogEnum;
import com.yonyou.nc.codevalidator.rule.except.RuleBaseException;
import com.yonyou.nc.codevalidator.sdk.code.JavaResPrivilege;
import com.yonyou.nc.codevalidator.sdk.log.Logger;
import com.yonyou.nc.codevalidator.sdk.rule.ClassLoaderUtilsFactory;
import com.yonyou.nc.codevalidator.sdk.rule.RuleClassLoadException;

/**
 * ��������Ƿ�����˵��ݺŵ�Ωһ�Լ��
 * 
 * @author lijbe
 * @since V1.0
 * @version 1.0.0.0
 */
@RuleDefinition(executeLayer = ExecuteLayer.BUSICOMP, executePeriod = ExecutePeriod.COMPILE, catalog = CatalogEnum.JAVACODE, subCatalog = SubCatalogEnum.JC_CODECRITERION, description = "��������Ƿ�����˵��ݺŵ�Ωһ�Լ�� �����ݿ�ѡ��", relatedIssueId = "272", coder = "lijbe", solution = "ͨ���ؼ��ַ�*InsertBPȡbs���µ�*InsertBP.java�ļ�;���ж�BP�Ƿ������˹ؼ��ַ�nc.bs.pubapp.pub.rule.BillCodeCheckRule;ע�����Ӻ����ķ�����ΪaddAfterRule.")
public class TestCase00272 extends AbstractJavaQueryRuleDefinition {

    @Override
    protected JavaResourceQuery getJavaResourceQuery(IRuleExecuteContext ruleExecContext) throws RuleBaseException {

        JavaResourceQuery javaResourceQuery = new JavaResourceQuery();
        javaResourceQuery.setBusinessComponent(ruleExecContext.getBusinessComponent());
        javaResourceQuery.setResPrivilege(JavaResPrivilege.PRIVATE);

        return javaResourceQuery;
    }

    @Override
    protected IRuleExecuteResult processJavaRules(IRuleExecuteContext ruleExecContext, List<JavaClassResource> resources)
            throws RuleBaseException {
        ResourceRuleExecuteResult result = new ResourceRuleExecuteResult();
        MapList<String, JavaClassResource> javaClazzResMapList = new MapList<String, JavaClassResource>();
        /*
         * �ҳ�һ������������InsertBP��β��java�ļ���������java�ļ�����������̵�BP��ΪĿ���ļ���
         * ������ɢ������������DmoPushInsertBP��DmoTranstypeInsertBP���������Ŀ���ļ���DmoInsertBP
         * ����ͨ������������Ҳ����˵�
         */
        for (final JavaClassResource javaClassResource : resources) {

            String className = javaClassResource.getJavaCodeClassName();

            if (className.endsWith("InsertBP")) {
                String key = className.substring(0, className.indexOf("bp") + 2);
                javaClazzResMapList.put(key, javaClassResource);
            }
        }
        if (MMMapUtil.isEmpty(javaClazzResMapList)) {
            result.addResultElement(ruleExecContext.getBusinessComponent().getBusinessComp(),
                    "û���ҵ���InsertBP��β��java�ļ����������BP�����Ƿ���InsertBP��β. \n");
            return result;
        }
        Set<String> keys = javaClazzResMapList.keySet();
        for (String key : keys) {

            List<JavaClassResource> javaClassResList = javaClazzResMapList.get(key);
            if (javaClassResList.size() == 1) {
                // ���
                this.check(javaClassResList.get(0), ruleExecContext, result);
            }
            else {
                this.check(this.findOptimalClassResource(javaClassResList), ruleExecContext, result);
            }
        }

        return result;
    }

    /**
     * �ҵ����ŵ�Ŀ���ļ�
     * 
     * @param javaClassResList
     * @return
     */
    private JavaClassResource findOptimalClassResource(List<JavaClassResource> javaClassResList) {
        List<String> clazzNameList = new ArrayList<String>();
        Map<String, JavaClassResource> resourceMap = new HashMap<String, JavaClassResource>();
        for (JavaClassResource javaClassResource : javaClassResList) {
            resourceMap.put(javaClassResource.getJavaCodeClassName(), javaClassResource);
            clazzNameList.add(javaClassResource.getJavaCodeClassName());
        }
        Collections.sort(clazzNameList, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.length() - s2.length();
            }
        });
        return resourceMap.get(clazzNameList.get(0));
    }

    private void check(final JavaClassResource javaClassResource, IRuleExecuteContext ruleExecContext,
            ResourceRuleExecuteResult result) {
        try {
            String className = javaClassResource.getJavaCodeClassName();

            Class<?> loadClass;

            loadClass =
                    ClassLoaderUtilsFactory.getClassLoaderUtils().loadClass(
                            ruleExecContext.getBusinessComponent().getProjectName(), className);

            Method[] methods = loadClass.getDeclaredMethods();
            final List<String> methodNames = new ArrayList<String>();
            for (Method method : methods) {
                if (method.getName().contains("addAfterRule")) {
                    methodNames.add(method.getName());
                }

            }

            CompilationUnit compilationUnit = JavaParser.parse(new File(javaClassResource.getResourcePath()));

            final StringBuilder noteBuilder = new StringBuilder();
            VoidVisitorAdapter<Void> visitor = new VoidVisitorAdapter<Void>() {
                @Override
                public void visit(MethodDeclaration n, Void v) {
                    if (methodNames.contains(n.getName())) {
                        boolean isBillCodeCheck = false;
                        // �õ������壬��{}�е�����
                        BlockStmt body = n.getBody();
                        if (body == null) {
                            this.appendMsg(n.getName());
                            return;
                        }
                        // �õ��������ڵ�����
                        List<Statement> stmts = body.getStmts();
                        if (stmts == null) {
                            this.appendMsg(n.getName());
                            return;
                        }

                        for (Statement stmt : stmts) {
                            if (stmt.toString().contains("BillCodeCheckRule")) {
                                isBillCodeCheck = true;
                                break;
                            }
                        }
                        if (!isBillCodeCheck) {
                            this.appendMsg(n.getName());
                        }
                    }
                }

                private void appendMsg(String methodName) {
                    noteBuilder.append(String.format(
                            "������%s��û�����ӵ��ݺ�Ψһ��У�����nc.bs.pubapp.pub.rule.BillCodeCheckRule����\n",
                            javaClassResource.getJavaCodeClassName() + "." + methodName));
                }
            };

            visitor.visit(compilationUnit, null);

            if (noteBuilder.toString().length() > 0) {
                result.addResultElement(javaClassResource.getJavaCodeClassName(), noteBuilder.toString());
            }
        }
        catch (RuleClassLoadException e) {
            Logger.error(e.getMessage(), e);
        }
        catch (ParseException e) {
            Logger.error(e.getMessage(), e);
        }
        catch (IOException e) {
            Logger.error(e.getMessage(), e);
        }
    }
}