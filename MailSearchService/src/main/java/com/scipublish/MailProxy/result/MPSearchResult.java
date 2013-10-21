package com.scipublish.MailProxy.result;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-10-19
 * Time: PM10:02
 * com.scipublish.MailProxy.result
 * MailProxy
 */
public class MPSearchResult {
    private long total;
    private int page;
    private int from;
    private List<Map> resultList;
    private long elapse;

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Map> getResultList() {
        return resultList;
    }

    public void setResultList(List<Map> resultList) {
        this.resultList = resultList;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getElapse() {
        return elapse;
    }

    public void setElapse(long elapse) {
        this.elapse = elapse;
    }

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("total:").append(total).append(",page:").append(page);
        stringBuilder.append("\n");

        if (resultList != null){
            for(int i = 0; i < resultList.size(); i++) {
                stringBuilder.append("<<<----------------------------\n");
                stringBuilder.append("record: " + i);
                stringBuilder.append("\n");
                Map map = resultList.get(i);
                Iterator it = map.entrySet().iterator();
                while(it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    stringBuilder.append((entry.getKey() != null ? entry.getKey().toString() : "") + ": " + (entry.getValue() != null ? entry.getValue().toString() : "empty"));
                    stringBuilder.append("\n");
                }
                stringBuilder.append("---------------------------->>>\n");
            }
        }

        return stringBuilder.toString();
    }
}
