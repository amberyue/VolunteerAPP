package utils;

import java.sql.Timestamp;

import bean.Request;

public class JsonUtils {

    public static <T> Object toBean(String function_id, String sessionId,T t) {

        Request r = new Request();
        Timestamp now = new Timestamp(System.currentTimeMillis());

        r.setFunction_id(function_id);
        r.setTimestamp(now);

        r.setData(t);
        r.setSession_id(sessionId);

        return r;
    }

}
