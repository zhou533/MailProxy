package com.scipublish.MailProxy;

import com.scipublish.MailProxy.common.MPUtils;
import org.junit.Test;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 强
 * Date: 13-10-27
 * Time: 下午7:28
 * To change this template use File | Settings | File Templates.
 */
public class UtilsTest {


    @Test
    public void testParseVariables() throws Exception {
        List<String> variables = MPUtils.parseVariables("j%variable.asd%sdhan孩子fsdfs%variable.dffrtew%sfgggf%d%%%\"");
        for (int i = 0; i < variables.size(); i++){
            System.out.println(variables.get(i));
        }
    }
}
