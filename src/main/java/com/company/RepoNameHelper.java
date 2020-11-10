package com.company;

public class RepoNameHelper {
    private String orgName;
    private String repoName;

    public RepoNameHelper(String orgName, String repoName) {
        this.orgName = orgName;
        this.repoName = repoName;
    }

    public String getRepoName() {
        return this.orgName + "/" + this.repoName;
    }
}
