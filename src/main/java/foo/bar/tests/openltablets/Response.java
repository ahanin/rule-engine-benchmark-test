package foo.bar.tests.openltablets;

import java.util.HashMap;
import java.util.Map;

public class Response {

    public Response() { }

    protected String result;

    public String getResult() {
        return result;
    }

    public void setResult(final String s) {
        result = s;
    }

    Map map = new HashMap();

    public Map getMap() {
        return map;
    }

}
