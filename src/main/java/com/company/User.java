package com.company;

import org.kohsuke.github.GHUser;

import java.io.IOException;

public class User {
    public static String getLoginName(GHUser ghUser) throws IOException {
        return ghUser.getLogin();
    }

    public static String getTeamName(GHUser ghUser) throws IOException {
        return UserTeamNameCache.getInstance().getTeamName(getLoginName(ghUser));
    }
}
