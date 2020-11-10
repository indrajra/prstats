package com.company;

import org.kohsuke.github.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class PullRequestCollector {
    private GitHub github;
    private String repoName;
    private List<DataModel> dataModelList = new ArrayList<>();
    //private HashSet<Integer> prNumbers = new HashSet<>();
    GHRepository ghRepository;

    public PullRequestCollector(GitHub gh, String repo) {
        github = gh;
        repoName = repo;
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
                //if (!prNumbers.contains(issue.getNumber())) {
                    GHPullRequest pr = ghRepository.getPullRequest(issue.getNumber());
                    DataModel dm = Issue2DataModel.transform(pr);

                    //prNumbers.add(issue.getNumber());
                    dataModelList.add(dm);
                //}
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
