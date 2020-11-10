package com.company;

import com.google.devtools.common.options.OptionsParser;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class Main {
    private static OptionsParser parser;
    private static CmdOptions options;
    private static TeamInfo teamInfo;

    public static void main(String[] args) throws IOException {
        ensureInputs(args);

        GitHub github = GitHubBuilder.fromPropertyFile().build();
        System.out.println("Hello...working with token of " + github.getMyself().getName());
        teamInfo = new TeamInfo(github, options.orgName, options.teamName);
        RepoNameHelper repoNameHelper = new RepoNameHelper(options.orgName, options.repoName);

        PullRequestCollector pullRequestCollector = new PullRequestCollector(github, repoNameHelper.getRepoName());

        String involvedTeamQuery = new QueryStringBuilder(options).getQueryInvolvedPulls(teamInfo);
        List<DataModel> dataModels = pullRequestCollector.collect(involvedTeamQuery);

        distinguishTeamPRs(dataModels);

        OutputGenerator outputGenerator = new OutputGenerator();
        outputGenerator.writeToFile(dataModels);
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
