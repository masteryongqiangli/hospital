package system.core.util;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.ScrollableResults;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

import net.sf.json.JSONObject;

public class CriteriaPageUtil {
	public static <T> JSONObject getPageJson(Criteria criteria, Map<String, String> parms) {
		JSONObject jsonObject = new JSONObject();
		int total = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
		criteria.setProjection(null);
		jsonObject = PageUtil.getPagePrams(parms);
		criteria.setFirstResult(jsonObject.getInt("firstresult"));
		if (jsonObject.getInt("firstresult") + jsonObject.getInt("rows") > total) {
			criteria.setMaxResults(total - jsonObject.getInt("firstresult"));
		} else {
			criteria.setMaxResults(jsonObject.getInt("rows"));
		}
		if (parms.get("order")!=null) {
			String[] orders = parms.get("order").split(",");
			for (int i = 0; i < orders.length; i += 2) {
				String prop = orders[i];
				switch (orders[i+ 1] ) {
				case "asc":
					criteria.addOrder(Order.asc(prop));
					break;
				case "desc":
					criteria.addOrder(Order.desc(prop));
					break;

				default:
					break;
				}
			}
		}
		
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		List<T> list = criteria.list();
		jsonObject.put("total", total);
		jsonObject.put("rows", JSONHelper.parseListtToJSONArray(list));
		return jsonObject;
	}

	public static <T> JSONObject getPageJson(SQLQuery query, Map<String, String> parms) {
		JSONObject jsonObject = new JSONObject();
		ScrollableResults scrollableResults = query.scroll();
		scrollableResults.last();
		int total;
		if (scrollableResults.getRowNumber() >= 0) {
			total = scrollableResults.getRowNumber() + 1;
		} else {
			total = 0;
		}
		jsonObject = PageUtil.getPagePrams(parms);
		query.setFirstResult(jsonObject.getInt("firstresult"));

		int endPage = total / jsonObject.getInt("rows") + ((total % jsonObject.getInt("rows")) > 0 ? 1 : 1);

		// query.setMaxResults((endPage - jsonObject.getInt("page") + 1) *
		// jsonObject.getInt("rows"));
		query.setMaxResults(jsonObject.getInt("rows"));
		List<T> list = query.list();
		jsonObject.put("total", total);
		jsonObject.put("rows", JSONHelper.parseListtToJSONArray(list));
		return jsonObject;
	}
}
