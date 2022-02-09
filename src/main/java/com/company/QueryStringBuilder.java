package com.company;

import java.util.Iterator;

public class QueryStringBuilder {
    private CmdOptions options;
    private QueryString queryString = new QueryString();
    private String repoName;

    public QueryStringBuilder(CmdOptions optionsInput) {
        options = optionsInput;
    }

    public String getQueryForPRsCreatedByTeam(TeamInfo teamInfo) {
        return fetchCreatedPullsQueryString(teamInfo);
    }

    public String getQueryForPRsSupportedByTeam(TeamInfo teamInfo) { return fetchSupportedPullsQueryString(teamInfo); }

    public String getQueryForPRsMentionsOfTeam(TeamInfo teamInfo) { return fetchMentionedPullsQueryString(teamInfo); }

    public String getQueryInvolvedPulls(TeamInfo teamInfo) { return fetchInvolvedPullsQueryString(teamInfo); }

    private String getRepoName() {
        if (null == repoName) {
            RepoNameHelper repoNameHelper = new RepoNameHelper(options.orgName, options.repoName);
            repoName = repoNameHelper.getRepoName();
        }
        return repoName;
    }

    private String fetchInvolvedPullsQueryString(TeamInfo teamInfo) {
        String result = null;
        if (!options.afterDate.isEmpty() &&
                !options.beforeDate.isEmpty()) {
            result = queryString.getCreatedPullsInDateRange(getRepoName(), options.afterDate, options.beforeDate);
        } else {
            result = queryString.getCreatedPullsAfter(getRepoName(), options.afterDate);
        }

        StringBuilder stringBuilder = new StringBuilder();
        Iterator<String> loginNames = teamInfo.getTeamMemberNames().iterator();
        while (loginNames.hasNext()) {
            String authorName = loginNames.next();
            stringBuilder.append("involves:");
            stringBuilder.append(authorName);
            stringBuilder.append(" ");
        }
        return result + " " + stringBuilder.toString();
    }

    private String fetchCreatedPullsQueryString(TeamInfo teamInfo) {
        String result = null;
        if (!options.afterDate.isEmpty() &&
                !options.beforeDate.isEmpty()) {
            result = queryString.getCreatedPullsInDateRange(getRepoName(), options.afterDate, options.beforeDate);
        } else {
            result = queryString.getCreatedPullsAfter(getRepoName(), options.afterDate);
        }

        StringBuilder stringBuilder = new StringBuilder();
        Iterator<String> loginNames = teamInfo.getTeamMemberNames().iterator();
        while (loginNames.hasNext()) {
            String authorName = loginNames.next();
            stringBuilder.append("author:");
            stringBuilder.append(authorName);
            stringBuilder.append(" ");
        }
        return result + " " + stringBuilder.toString();
    }

    private String fetchSupportedPullsQueryString(TeamInfo teamInfo) {
        String result = null;
        if (!options.afterDate.isEmpty() &&
                !options.beforeDate.isEmpty()) {
            result = queryString.getCreatedPullsInDateRange(getRepoName(), options.afterDate, options.beforeDate);
        } else {
            result = queryString.getCreatedPullsAfter(getRepoName(), options.afterDate);
        }

        StringBuilder stringBuilder = new StringBuilder();
        Iterator<String> loginNames = teamInfo.getTeamMemberNames().iterator();
        while (loginNames.hasNext()) {
            String teamMemberName = loginNames.next();
            stringBuilder.append("assignee:");
            stringBuilder.append(teamMemberName);

            stringBuilder.append(" ");
        }
        return result + " " + stringBuilder.toString();
    }

    private String fetchMentionedPullsQueryString(TeamInfo teamInfo) {
        String result = null;
        if (!options.afterDate.isEmpty() &&
                !options.beforeDate.isEmpty()) {
            result = queryString.getCreatedPullsInDateRange(getRepoName(), options.afterDate, options.beforeDate);
        } else {
            result = queryString.getCreatedPullsAfter(getRepoName(), options.afterDate);
        }

        StringBuilder stringBuilder = new StringBuilder();
        Iterator<String> loginNames = teamInfo.getTeamMemberNames().iterator();
        while (loginNames.hasNext()) {
            String teamMemberName = loginNames.next();
            stringBuilder.append("mentions:");
            stringBuilder.append(teamMemberName);

            stringBuilder.append(" ");
        }
        return result + " " + stringBuilder.toString();
    }

    public String getOpenIssues() {
        return queryString.getOpenCodeHygieneIssues();
    }

    private class QueryString {
        public String getCreatedPullsAfter(String repoName, String after) {
            return String.format("type:pr repo:%s created:>=%s", repoName, after);
        }

        public String getCreatedPullsInDateRange(String repoName, String afterDate, String beforeDate) {
            return String.format("type:pr repo:%s created:%s..%s", repoName, afterDate, beforeDate);
        }

        public String getOpenCodeHygieneIssues() {
            return String.format("is:issue is:open label:team-turing label:code-hygiene");
        }
    }
}
