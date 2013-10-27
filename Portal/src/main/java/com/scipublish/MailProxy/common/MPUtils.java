package com.scipublish.MailProxy.common;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 强
 * Date: 13-10-27
 * Time: 下午4:33
 * To change this template use File | Settings | File Templates.
 */
public class MPUtils {

    private static final String VARIABLE_PREFIX = "variable.";

    public static List<String> parseVariables(String originalContent){
        List<String> variables = new ArrayList<String>();
        Integer length = originalContent.length();

        Integer vStart = 0;
        for (Integer i = 0; i < length; i++){
            char c = originalContent.charAt(i);
            if (c == '%'){
                Integer vLen = i - vStart;
                if (vLen <= (VARIABLE_PREFIX.length() + 1)){
                    vStart = i;
                    continue;
                }

                String prefix = originalContent.substring(vStart + 1, vStart + 1 + VARIABLE_PREFIX.length());
                if (StringUtils.equalsIgnoreCase(VARIABLE_PREFIX, prefix)){
                    String variable = originalContent.substring(vStart + 1 + VARIABLE_PREFIX.length(), i);
                    variables.add(variable);

                }
                vStart = i;
            }
        }
        return variables;
    }
}
