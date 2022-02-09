package com.company;

import com.google.devtools.common.options.OptionsParser;
import org.kohsuke.github.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class Main {
    private static OptionsParser parser;
    private static CmdOptions options;
    private static TeamInfo teamInfo;
    private static UserTeamNameCache userTeamNameCache;

    public static void main(String[] args) throws IOException {
        ensureInputs(args);

        GitHub github = GitHubBuilder.fromPropertyFile().build();
        System.out.println("Hello...working with token of " + github.getMyself().getName());
        teamInfo = new TeamInfo(github, options.orgName, options.teamName);
        TeamInfo.loadUsersIntoCache(github, options.orgName);

        RepoNameHelper repoNameHelper = new RepoNameHelper(options.orgName, options.repoName);

        PullRequestCollector pullRequestCollector = new PullRequestCollector(github, repoNameHelper.getRepoName(), options.teamName);

        QueryStringBuilder queryStringBuilder = new QueryStringBuilder(options);
        String involvedTeamQuery = queryStringBuilder.getQueryInvolvedPulls(teamInfo);
        List<DataModel> dataModels = pullRequestCollector.collect(involvedTeamQuery);

//        String openIssues = queryStringBuilder.getOpenIssues();
//        GHIssueSearchBuilder issueSearchBuilder = github.searchIssues().q(openIssues);;
//        PagedSearchIterable<GHIssue> issueResults = issueSearchBuilder.list();
//        issueResults.forEach(iss -> {
//            System.out.println(iss.getNumber() + ",");
//        });

        distinguishTeamPRs(dataModels);

        OutputGenerator outputGenerator = new OutputGenerator();
        outputGenerator.writeToFile(options.friendlyName, dataModels);
    }

    public static String getReportBuilderTeamName() {
        return options.teamName;
    }

    private static void distinguishTeamPRs(List<DataModel> dataModels) {
        dataModels.forEach(dataModel -> {
            boolean reviewedForOthers = !(teamInfo.getTeamMemberNames().contains(dataModel.getAuthor()));
            dataModel.setReviewedForOtherTeam(reviewedForOthers);

            if (!reviewedForOthers) {
                // Look if this teams' PRs were reviewed by other teams.
                boolean reviewedByOthers = !(teamInfo.getTeamMemberNames().containsAll(dataModel.getReviewers()));
                dataModel.setReviewedByOtherTeam(reviewedByOthers);
            }
        });
    }

    private static void ensureInputs(String[] args) {
        parser = OptionsParser.newOptionsParser(CmdOptions.class);
        parser.parseAndExitUponError(args);
        options = parser.getOptions(CmdOptions.class);
        if (options.repoName.isEmpty() ||
                options.teamName.isEmpty() ||
                options.afterDate.isEmpty()) {
            printUsage();
            System.exit(1);
        }
    }

    private static void printUsage() {
        System.out.println("Usage: java -jar server.jar OPTIONS");
        // TODO: System.out.println("Outputs a csv file with the following ")
        System.out.println(parser.describeOptions(Collections.<String, String>emptyMap(),
                OptionsParser.HelpVerbosity.LONG));
    }
}
