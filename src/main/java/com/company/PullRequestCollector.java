package com.company;

import org.kohsuke.github.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PullRequestCollector {
    private GitHub github;
    private String repoName;
    private String reportOfTeam;
    private List<DataModel> dataModelList = new ArrayList<>();
    GHRepository ghRepository;

    public PullRequestCollector(GitHub gh, String repo, String rptOfTeam) {
        github = gh;
        repoName = repo;
        reportOfTeam = rptOfTeam;
    }

    public List<DataModel> collect(String queryStr) throws IOException {
        if (null == ghRepository) {
            ghRepository = github.getRepository(repoName);
        }
        System.out.println(String.format("Fetching PRs satisfying query %s", queryStr));
        GHIssueSearchBuilder issueSearchBuilder = github.searchIssues().q(queryStr);
        PagedSearchIterable<GHIssue> issueResults = issueSearchBuilder.list();
        System.out.println(String.format("Result = %d", issueResults.getTotalCount()));

        populateDataModels(issueResults);
        return dataModelList;
    }

    private void populateDataModels(PagedSearchIterable<GHIssue> issueResults) {
        issueResults.forEach(issue -> {
            System.out.println(String.format("Start processing pull request %d", issue.getNumber()));
            try {
                GHPullRequest pr = ghRepository.getPullRequest(issue.getNumber());
                Issue2DataModel issue2DataModel = new Issue2DataModel();
                issue2DataModel.setReportOfTeam(reportOfTeam);

                DataModel dm = issue2DataModel.transform(pr);
                dataModelList.add(dm);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
