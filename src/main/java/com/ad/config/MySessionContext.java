package com.ad.config;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Create By  @林俊杰
 * 2020/8/22 20:15
 *
 * @version 1.0
 */
public class MySessionContext {
    private static Map<String, HttpSession> sessionHashMap = new HashMap<>();
    public static synchronized void addSession(HttpSession session) {
        if (session != null) {
            sessionHashMap.put(session.getId(), session);
        }
    }

    public static synchronized void removeSession(HttpSession session) {
        if (session != null) {
            sessionHashMap.remove(session.getId());
        }
    }

    public static synchronized HttpSession getSession(String sessionId) {
        return sessionHashMap.get(sessionId);
    }
}
