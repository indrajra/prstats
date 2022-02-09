package com.company;

import java.util.HashMap;
import java.util.Map;

public class UserTeamNameCache {
    private Map<String, String> personTeamMap = new HashMap<>();
    private static UserTeamNameCache theObj = null;
    private static final Boolean lockFlag = new Boolean(false);

    public static UserTeamNameCache getInstance() {
        if (null == theObj) {
            synchronized (lockFlag) {
                if (null == theObj) {
                    theObj = new UserTeamNameCache();
                }
            }
        }

        return theObj;
    }

    public void add(String personName, String teamName) {
        personTeamMap.put(personName, teamName);
    }

    public String getTeamName(String personName) {
        return personTeamMap.getOrDefault(personName, null);
    }
}
