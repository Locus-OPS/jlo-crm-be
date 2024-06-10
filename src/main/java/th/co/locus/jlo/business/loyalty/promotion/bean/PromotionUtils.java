package th.co.locus.jlo.business.loyalty.promotion.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import th.co.locus.jlo.business.loyalty.program.bean.ProgramAttributeModelBean;

public class PromotionUtils {
	public static final String ACTION_TYPE_UPDATE = "Update";
	
	public static final String COND_EQUALS = "=";
	public static final String COND_NOT_EQUALS = "<>";
	public static final String COND_IS_EMPTY = "IS_EMPTY";
	public static final String COND_IS_NOT_EMPTY = "IS_NOT_EMPTY";
	public static final String COND_STARTS_WITH = "STARTS_WITH";
	public static final String COND_ENDS_WITH = "ENDS_WITH";
	public static final String COND_CONTAINS = "CONTAINS";
	public static final String COND_NOT_CONTAIN = "NOT_CONTAIN";
	public static final String COND_UNDER = "UNDER";
	
	public static final String FN_ENGINE_CHECK_ITEM_IN_CATEGORY = "ENGINE_CHECK_ITEM_IN_CATEGORY";
	public static final String LOY_RECEIPT_ITEM_TABLE = "LOY_RECEIPT_ITEM";
	public static final String PRODUCT_CATEGORY_COLUMN = "CATEGORY_NAME";
		
	private static final String PIPE = "|";
	private static final String MAIN_TABLE = "LOY_TXN";
	private static final String MAIN_TABLE_WHERE = " WHERE LOY_TXN.TXN_ID = :TXN_ID ";
	private static final String PROMOTION_TABLE = "LOY_PROMOTION_ATTRIBUTE_VALUE";
	private static final String PROMOTION_COLUMN_NAME = "ATTR_NAME";
	private static final String PROMOTION_COLUMN_VALUE = "ATTR_VALUE";

	private static List<TableMappingBean> tableMappingList = new ArrayList<>();
	private static List<TableRefMappingBean> tableRefMappingList = new ArrayList<>();
	private static Map<String, String> conditionMap = new HashMap<>();
	
	static {
		tableMappingList.add(new TableMappingBean("LOY_TXN", "MEMBER_ID", "LOY_MEMBER", "MEMBER_ID"));
		tableMappingList.add(new TableMappingBean("LOY_TXN", "RECEIPT_ID", "LOY_RECEIPT", "RECEIPT_ID"));
		tableMappingList.add(new TableMappingBean("LOY_TXN", "RECEIPT_ID", "LOY_RECEIPT_ITEM", "RECEIPT_ID"));
		tableMappingList.add(new TableMappingBean("LOY_TXN", "STORE_SHOP_ID", "SPWG_SHOP", "SHOP_ID", "LOY_TXN.STORE_SHOP_TYPE = 'T'"));
		tableMappingList.add(new TableMappingBean("LOY_TXN", "MEMBER_ID", "LOY_PROMOTION_ATTRIBUTE_VALUE", "MEMBER_ID"));
		tableMappingList.add(new TableMappingBean("LOY_TXN", "PROGRAM_ID|MEMBER_ID", "LOY_MEM_POINT_BALANCE", "PROGRAM_ID|MEMBER_ID"));
		tableMappingList.add(new TableMappingBean("LOY_TXN", null, "LOY_PROMOTION", null, "LOY_PROMOTION.PROMOTION_ID = :PROMOTION_ID"));
		tableMappingList.add(new TableMappingBean("LOY_TXN", null, "LOY_PROMOTION_RULES", null, "LOY_PROMOTION_RULES.RULE_ID = :RULE_ID"));
		tableMappingList.add(new TableMappingBean("LOY_MEMBER", "ACCOUNT_NO", "SPWG_CUSTOMER", "ACCOUNT_NO"));
		
		tableRefMappingList.add(new TableRefMappingBean("SPWG_CUSTOMER", "LOY_MEMBER"));
		
		conditionMap.put("=", "=");
		conditionMap.put("<>", "<>");
		conditionMap.put(">", ">");
		conditionMap.put(">=", ">=");
		conditionMap.put("<", "<");
		conditionMap.put("<=", "<=");
		conditionMap.put("IS_EMPTY", "IS NULL");
		conditionMap.put("IS_NOT_EMPTY", "IS NOT NULL");
		conditionMap.put("STARTS_WITH", "LIKE");
		conditionMap.put("ENDS_WITH", "LIKE");
		conditionMap.put("CONTAINS", "IN");
		conditionMap.put("NOT_CONTAIN", "NOT IN");
	}
	
	public static String getCriteriaExpression(RuleCriteriaModelBean bean, ProgramAttributeModelBean src, ProgramAttributeModelBean dsc) {
		String left = String.format("[%s.%s]", src.getAttrGroupName(), src.getAttrName());
		
		if (COND_IS_EMPTY.equals(bean.getSrcCondition()) || COND_IS_NOT_EMPTY.equals(bean.getSrcCondition())) {
			return String.format("%s %s", left, bean.getSrcCondition());
		}
		
		if ("V".equals(bean.getCompareToOv())) {
			return String.format("%s %s %s", left, bean.getSrcCondition(), bean.getDscValue());
		} else {
			String right = String.format("[%s.%s]", dsc.getAttrGroupName(), dsc.getAttrName());
			if (StringUtils.isNotEmpty(bean.getDscOperator()) && StringUtils.isNotEmpty(bean.getDscValue())) {
				return String.format("%s %s %s %s %s", left, bean.getSrcCondition(), right, bean.getDscOperator(), bean.getDscValue());
			} else {
				return String.format("%s %s %s", left, bean.getSrcCondition(), right);
			}
		}
	}
	
	public static ActionQueryBean getActionQueryExp(String actionType, RuleActionModelBean action) {		
		ActionQueryBean bean = new ActionQueryBean();
		if (ACTION_TYPE_UPDATE.equals(actionType)) {
			bean.setActionExp(getActionExp(action));
			bean.setActionQuery(getActionQuery(action));			
		} else {
			if (StringUtils.isNotEmpty(action.getWithObject()) && StringUtils.isNotEmpty(action.getWithField())) {
				bean.setActionQuery(getActionQueryPoint(action));						
				bean.setActionExp(getActionExpPoint(action));
			} else {
				bean.setActionExp(getActionExpPointValue(action));
			}
		}
		return bean;
	}
	
	private static String getActionQueryPoint(RuleActionModelBean action) {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotEmpty(action.getWithOperator()) && StringUtils.isNotEmpty(action.getWithValue())) {
			sb.append(String.format("SELECT %s.%s %s %s ", action.getWithObject(), action.getWithField(), action.getWithOperator(), action.getWithValue()));
		} else {
			sb.append(String.format("SELECT %s.%s ", action.getWithObject(), action.getWithField()));			
		}
		sb.append(String.format("FROM %s ", MAIN_TABLE));
		if (!MAIN_TABLE.equals(action.getWithObject())) {
			sb.append(checkRefJoinTable(sb, action.getWithObject()));
			sb.append(getJoinClause(sb, MAIN_TABLE, action.getWithObject(), 0));
		}
		sb.append(MAIN_TABLE_WHERE);
		return sb.toString().trim();
	}
	
	private static String getActionExpPointValue(RuleActionModelBean action) {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("[%s] %s %s", action.getPointTypeName(), action.getPointOperator(), action.getPointValue()));
		return sb.toString().trim();
	}
	
	private static String getActionExpPoint(RuleActionModelBean action) {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotEmpty(action.getWithOperator()) && StringUtils.isNotEmpty(action.getWithValue())) {
			sb.append(String.format("SELECT [%s.%s] %s %s", action.getWithGroup(), action.getWithName(), action.getWithOperator(), action.getWithValue()));
		} else {
			sb.append(String.format("SELECT [%s.%s]", action.getWithGroup(), action.getWithName()));			
		}
		return sb.toString().trim();
	}
	
	private static String getActionQuery(RuleActionModelBean action) {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("UPDATE %s SET", action.getUpdObject()));
		
		String updateFieldName = action.getUpdField();
		String withFieldName = action.getWithField();
		if (PROMOTION_TABLE.equals(action.getUpdObject())) {
			updateFieldName = PROMOTION_COLUMN_VALUE;
		}
		sb.append(String.format(" %s.%s = ", action.getUpdObject(), updateFieldName));
		
		String withValue = action.getWithValue();
		if (StringUtils.isNotEmpty(withValue) && "Text".equals(action.getUpdDataTypeId())) {
			withValue = "'" + withValue + "'";
		}
		
		if (StringUtils.isNotEmpty(action.getWithObject()) && StringUtils.isNotEmpty(action.getWithField())) {
			if ("=".equals(action.getUpdOperator())) {
				if ("Date".equals(action.getUpdDataTypeId())) {
					String field = "";
					int index = withFieldName.indexOf("(");
					field = withFieldName.substring(0, index + 1) + action.getWithObject() + "." + withFieldName.substring(index + 1);
					sb.append(String.format("%s %s %s", field, action.getWithOperator(), withValue));						
				} else {					
					sb.append(String.format("%s.%s %s %s", action.getWithObject(), withFieldName, action.getWithOperator(), withValue));								
				}
			} else {
				if (StringUtils.isNotEmpty(action.getWithOperator())) {
					sb.append(String.format("%s.%s %s (%s.%s %s %s)", action.getUpdObject(), updateFieldName, action.getUpdOperator(), action.getWithObject(), withFieldName, action.getWithOperator(), withValue));
				} else {
					sb.append(String.format("%s.%s %s %s.%s ", action.getUpdObject(), updateFieldName, action.getUpdOperator(), action.getWithObject(), withFieldName));					
				}
			}
		} else {
			if ("=".equals(action.getUpdOperator())) {
				sb.append(withValue);			
			} else {
				sb.append(String.format("%s.%s %s %s", action.getUpdObject(), updateFieldName, action.getUpdOperator(), withValue));
			}
		}
		sb.append(" FROM ").append(MAIN_TABLE);
		if (!MAIN_TABLE.equals(action.getUpdObject())) {
			sb.append(checkRefJoinTable(sb, action.getUpdObject()));
			sb.append(getJoinClause(sb, MAIN_TABLE, action.getUpdObject(), 0));			
		}
		if (StringUtils.isNotEmpty(action.getWithObject())) {
			sb.append(checkRefJoinTable(sb, action.getWithObject()));
			sb.append(getJoinClause(sb, MAIN_TABLE, action.getWithObject(), 0));						
		}
		sb.append(MAIN_TABLE_WHERE);
		if (PROMOTION_TABLE.equals(action.getUpdObject())) {
			sb.append(String.format(" AND %s.%s = '%s'", PROMOTION_TABLE, PROMOTION_COLUMN_NAME, action.getUpdField()));
		}
		if (PROMOTION_TABLE.equals(action.getWithObject())) {
			sb.append(String.format(" AND %s.%s = '%s'", PROMOTION_TABLE, PROMOTION_COLUMN_NAME, action.getWithField()));
		}
		return sb.toString().trim();
	}
	
	private static String getActionExp(RuleActionModelBean action) {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("UPDATE [%s] SET [%s.%s] = ", action.getUpdGroup(), action.getUpdGroup(), action.getUpdName()));
		if (StringUtils.isNotEmpty(action.getWithGroup()) && StringUtils.isNotEmpty(action.getWithName())) {
			if ("=".equals(action.getUpdOperator())) {
				sb.append(String.format("[%s.%s] %s %s", action.getWithGroup(), action.getWithName(), action.getWithOperator(), action.getWithValue()));								
			} else {
				if (StringUtils.isNotEmpty(action.getWithOperator())) {
					sb.append(String.format("[%s.%s] %s ([%s.%s] %s %s)", action.getUpdGroup(), action.getUpdName(), action.getUpdOperator(), action.getWithGroup(), action.getWithName(), action.getWithOperator(), action.getWithValue()));
				} else {
					sb.append(String.format("[%s.%s] %s [%s.%s] ", action.getUpdGroup(), action.getUpdName(), action.getUpdOperator(), action.getWithGroup(), action.getWithName()));					
				}
			}
		} else {			
			if ("=".equals(action.getUpdOperator())) {
				sb.append(action.getWithValue());			
			} else {
				sb.append(String.format("[%s.%s] %s %s", action.getUpdGroup(), action.getUpdName(), action.getUpdOperator(), action.getWithValue()));
			}
					
		}
		return sb.toString().trim();
	}
	
	public static RuleQueryBean getRuleQueryJoin(List<RuleCriteriaModelBean> criteriaList) {
		RuleQueryBean result = new RuleQueryBean();
		StringBuilder ruleQuery = new StringBuilder();
		StringBuilder ruleJoin = new StringBuilder();
		Set<String> tmp = new HashSet<>();
		tmp.add(MAIN_TABLE);
		int promotionAttrCount = 0;
		for (RuleCriteriaModelBean criteria : criteriaList) {
			ruleQuery.append(getRuleQuery(criteria, promotionAttrCount));
			if (!tmp.contains(criteria.getSrcObjectName()) && StringUtils.isNotEmpty(criteria.getSrcObjectName())) {
				ruleJoin.append(checkRefJoinTable(ruleJoin, criteria.getSrcObjectName()));
				ruleJoin.append(getJoinClause(ruleJoin, MAIN_TABLE, criteria.getSrcObjectName(), promotionAttrCount));
				ruleJoin.append(checkSpecialJoinCase(criteria));
				if (!PROMOTION_TABLE.equals(criteria.getSrcObjectName())) {					
					tmp.add(criteria.getSrcObjectName());
				}
			}
			if (!tmp.contains(criteria.getDscObjectName()) && StringUtils.isNotEmpty(criteria.getDscObjectName())) {
				ruleJoin.append(checkRefJoinTable(ruleJoin, criteria.getDscObjectName()));
				ruleJoin.append(getJoinClause(ruleJoin, MAIN_TABLE, criteria.getDscObjectName(), promotionAttrCount));
				if (!PROMOTION_TABLE.equals(criteria.getDscObjectName())) {					
					tmp.add(criteria.getDscObjectName());
				}
			}
			if (PROMOTION_TABLE.equals(criteria.getSrcObjectName())) {
				promotionAttrCount++;
			}
		}
		result.setRuleQuery(ruleQuery.toString().trim());
		result.setRuleJoin(ruleJoin.toString().trim());
		return result;
	}	
	
	private static String getRuleQuery(RuleCriteriaModelBean criteria, int promotionAttrCount) {
		String condition = conditionMap.get(criteria.getSrcCondition());				
		String left = "";
		
		String alias = (promotionAttrCount > 0 ? PROMOTION_TABLE + promotionAttrCount : PROMOTION_TABLE);
		if (PROMOTION_TABLE.equals(criteria.getSrcObjectName())) {
			left = String.format("%s.%s = '%s' AND %s.%s", alias, PROMOTION_COLUMN_NAME, criteria.getSrcFieldName(), alias, PROMOTION_COLUMN_VALUE);			
		} else {
			if ("Date".equals(criteria.getSrcDataType())) {
				String field = criteria.getSrcFieldName();
				int index = field.indexOf("(");
				left = field.substring(0, index + 1) + criteria.getSrcObjectName() + "." + field.substring(index + 1);
			} else {
				left = String.format("%s.%s", criteria.getSrcObjectName(), criteria.getSrcFieldName());							
			}
		}
		
		if (COND_IS_EMPTY.equals(criteria.getSrcCondition()) || COND_IS_NOT_EMPTY.equals(criteria.getSrcCondition())) {
			return String.format(" AND %s.%s %s", criteria.getSrcObjectName(), criteria.getSrcFieldName(), condition);
		}
		
		if ("V".equals(criteria.getCompareToOv())) {
			if (criteria.getDscValue().indexOf(",") > 0) {
				return createExpMultiValue(criteria, condition, left, promotionAttrCount);								
			} else {
				return createExpSingleValue(criteria, condition, left, promotionAttrCount);				
			}
		} else {
			String right = "";
			if (PROMOTION_TABLE.equals(criteria.getDscObjectName())) {
				right = String.format("%s.%s", alias, PROMOTION_COLUMN_VALUE);			
			} else {
				if ("Date".equals(criteria.getDscDataType())) {
					String field = criteria.getDscFieldName();
					int index = field.indexOf("(");
					right = field.substring(0, index + 1) + criteria.getDscObjectName() + "." + field.substring(index + 1);
				} else {
					right = String.format("%s.%s", criteria.getDscObjectName(), criteria.getDscFieldName());									
				}
			}
			
			String tmp = String.format(" AND (%s %s %s", left, condition, right);
			if (StringUtils.isNotEmpty(criteria.getDscOperator()) && StringUtils.isNotEmpty(criteria.getDscValue())) {
				tmp = tmp + String.format(" %s %s", criteria.getDscOperator(), criteria.getDscValue());
			}
			if (PROMOTION_TABLE.equals(criteria.getDscObjectName())) {
				tmp = tmp + String.format(" AND %s.%s = '%s' OR %s.%s IS NULL", alias, PROMOTION_COLUMN_NAME, criteria.getDscFieldName(), alias, PROMOTION_COLUMN_VALUE);
			} else if (PROMOTION_TABLE.equals(criteria.getSrcObjectName())) {
				tmp = tmp + String.format(" OR %s.%s IS NULL", alias, PROMOTION_COLUMN_VALUE);
			}
			return tmp + ")";
		}
	}

	private static String createExpMultiValue(RuleCriteriaModelBean criteria, String condition, String left, int promotionAttrCount) {
		if (COND_UNDER.equals(criteria.getSrcCondition())) {
			return "";
		}
		
		String alias = (promotionAttrCount > 0 ? PROMOTION_TABLE + promotionAttrCount : PROMOTION_TABLE);
		String exp = "";
		switch (criteria.getSrcCondition()) {
			case COND_CONTAINS:
			case COND_NOT_CONTAIN:
				String dscValue = criteria.getDscValue();					
				if ("Text".equals(criteria.getSrcDataType())) {
					dscValue = String.format("'%s'", String.join("','", criteria.getDscValue().split(",")));
				} else if ("Number".equals(criteria.getSrcDataType())) {
					dscValue = String.format("%s", String.join(",", criteria.getDscValue().split(",")));
				}
				dscValue = "(" + dscValue + ")";
				exp = String.format("%s %s %s", left, condition, dscValue);
			break;
			case COND_EQUALS:
			case COND_NOT_EQUALS:
			case COND_STARTS_WITH:
			case COND_ENDS_WITH:
				StringBuilder sb = new StringBuilder();
				String[] vals = criteria.getDscValue().split(",");
				for (int i=0 ; i < vals.length; i++) {
					String val = vals[i];
					if (i > 0) {
						sb.append(" OR ");
					}
					if (COND_STARTS_WITH.equals(criteria.getSrcCondition())) {
						sb.append(String.format("%s %s '%s'", left, condition, val + "%"));						
					} else if (COND_ENDS_WITH.equals(criteria.getSrcCondition())) {
						sb.append(String.format("%s %s '%s'", left, condition, "%" + val));												
					} else if (COND_EQUALS.equals(criteria.getSrcCondition())  || COND_NOT_EQUALS.equals(criteria.getSrcCondition())) {
						sb.append(String.format("%s %s '%s'", left, condition, val));												
					}
				}			
				exp = sb.toString();
			break;
			case COND_IS_EMPTY:
			case COND_IS_NOT_EMPTY:
				exp = String.format("%s %s", left, condition);
			break;
		}

		if (PROMOTION_TABLE.equals(criteria.getSrcObjectName())) {
			return String.format(" AND (%s OR %s.%s IS NULL)", exp, alias, PROMOTION_COLUMN_VALUE);
		} else {				
			return String.format(" AND (%s)", exp);
		}
	}
	
	private static String createExpSingleValue(RuleCriteriaModelBean criteria, String condition, String left, int promotionAttrCount) {
		if (COND_UNDER.equals(criteria.getSrcCondition())) {
			return "";
		}
		
		String alias = (promotionAttrCount > 0 ? PROMOTION_TABLE + promotionAttrCount : PROMOTION_TABLE);
		String dscValue = criteria.getDscValue();			
		if ("Text".equals(criteria.getSrcDataType())) {
			dscValue = String.format("'%s'", String.join("','", criteria.getDscValue().split(",")));
		} else if ("Number".equals(criteria.getSrcDataType())) {
			dscValue = String.format("%s", String.join(",", criteria.getDscValue().split(",")));
		}
		
		if (COND_CONTAINS.equals(criteria.getSrcCondition()) || COND_NOT_CONTAIN.equals(criteria.getSrcCondition())) {
			dscValue = "(" + dscValue + ")";
		} else if (COND_STARTS_WITH.equals(criteria.getSrcCondition())) {
			dscValue = dscValue.substring(0, dscValue.length() - 1) + "%'";
		} else if (COND_ENDS_WITH.equals(criteria.getSrcCondition())) {
			dscValue = "'%" + dscValue.substring(1, dscValue.length());
		}
		if (PROMOTION_TABLE.equals(criteria.getSrcObjectName())) {
			return String.format(" AND (%s %s %s OR %s.%s IS NULL)", left, condition, dscValue, alias, PROMOTION_COLUMN_VALUE);
		} else {				
			return String.format(" AND (%s %s %s)", left, condition, dscValue);
		}
	}
	
	private static String getJoinClause(StringBuilder join, String srcObjectName, String dscObjectName, int promotionAttrCount) {
		if (!PROMOTION_TABLE.equals(dscObjectName) && join.indexOf(String.format("LEFT JOIN %s", dscObjectName)) > 0) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder();		
		Optional<TableMappingBean> src = tableMappingList.stream().filter(m -> m.getSourceTable().equals(srcObjectName) && m.getDestinationTable().equals(dscObjectName)).findFirst();
		if (src.isPresent()) {
			String alias = dscObjectName;
			if (PROMOTION_TABLE.equals(dscObjectName)) {
				alias = (promotionAttrCount > 0 ? PROMOTION_TABLE + promotionAttrCount : PROMOTION_TABLE);				
			}
			if (StringUtils.isNotEmpty(src.get().getSourceKey()) && StringUtils.isNotEmpty(src.get().getDestinationKey())) {
				String[] sourceKeys = src.get().getSourceKey().split(Pattern.quote(PIPE));
				String[] destinationKeys = src.get().getDestinationKey().split(Pattern.quote(PIPE));
				
				for (int i=0 ; i < sourceKeys.length ; i++) {
					if (i > 0) {
						sb.append(" AND");
					}
					String sourceAlias = PROMOTION_TABLE.equals(src.get().getSourceTable()) ? alias : src.get().getSourceTable();
					String descAlias = PROMOTION_TABLE.equals(src.get().getDestinationTable()) ? alias : src.get().getDestinationTable();
					sb.append(String.format(" %s.%s = %s.%s", sourceAlias, sourceKeys[i], descAlias, destinationKeys[i]));				
				}
				if (PROMOTION_TABLE.equals(src.get().getDestinationTable())) {
					sb.append(" AND " + alias + ".PROMOTION_ID = :PROMOTION_ID");
				}
			} 
			
			if (StringUtils.isNotEmpty(src.get().getAddition())) {
				if (StringUtils.isNotEmpty(sb)) {
					sb.append(" AND ");
				}
				sb.append(src.get().getAddition());
			}			
			return String.format(" LEFT JOIN %s %s ON %s ", dscObjectName, alias, sb.toString());	
		} else {
			return "";
		}
	}
	
	private static String checkRefJoinTable(StringBuilder join, String objectName) {
		StringBuilder sb = new StringBuilder();
		Optional<TableRefMappingBean> ref = tableRefMappingList.stream().filter(m -> m.getSourceTable().equals(objectName)).findFirst();
		if (ref.isPresent()) {
			Optional<TableMappingBean> src = tableMappingList.stream().filter(m -> m.getSourceTable().equals(MAIN_TABLE) && m.getDestinationTable().equals(ref.get().getRefTable())).findFirst();
			if (src.isPresent()) {
				sb.append(getJoinClause(join, MAIN_TABLE, ref.get().getRefTable(), 0));	
			}
			Optional<TableMappingBean> src2 = tableMappingList.stream().filter(m -> m.getSourceTable().equals(ref.get().getRefTable()) && m.getDestinationTable().equals(objectName)).findFirst();
			if (src2.isPresent()) {
				sb.append(getJoinClause(join, ref.get().getRefTable(), objectName, 0));	
			}
		}
		
		return sb.toString();
	}
	
	private static String checkSpecialJoinCase(RuleCriteriaModelBean criteria) {		
		String objectName = criteria.getSrcObjectName();
		String attrName = criteria.getSrcFieldName();
		String condition = criteria.getSrcCondition();
		String value = criteria.getDscValue();
		if (LOY_RECEIPT_ITEM_TABLE.equals(objectName) && PRODUCT_CATEGORY_COLUMN.equals(attrName) && COND_UNDER.equals(condition)) {
			return String.format(" INNER JOIN %s('%s') CHK ON CHK.ITEM_CODE = %s.ITEM_CODE ", FN_ENGINE_CHECK_ITEM_IN_CATEGORY, value, LOY_RECEIPT_ITEM_TABLE);
		}
		return "";
	}
}
