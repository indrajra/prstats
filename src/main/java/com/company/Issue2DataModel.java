package com.company;

import org.kohsuke.github.GHLabel;
import org.kohsuke.github.GHPullRequest;
import org.kohsuke.github.GHPullRequestReviewState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.HOURS;

public class Issue2DataModel {
    private static String reportOfTeam;
    private static UserTeamNameCache userTeamNameCache = UserTeamNameCache.getInstance();

    public void setReportOfTeam(String name) {
        reportOfTeam = name;
    }

    public DataModel transform(GHPullRequest pr) throws IOException {
        DataModel dataModel = new DataModel().setAuthor(User.getLoginName(pr.getUser()))
                .setClosedDate(pr.getClosedAt())
                .setId(pr.getNumber())
                .setnComments(pr.getCommentsCount())
                .setnReviewComments(pr.getReviewComments())
                .setnAdditions(pr.getAdditions())
                .setnDeletions(pr.getDeletions())
                .setnFiles(pr.getChangedFiles())
                .setCreatedDate(pr.getCreatedAt())
                .setStatus(pr.getState().name())
                .setTitle(pr.getTitle());

        dataModel.setAuthorTeam(userTeamNameCache.getTeamName(dataModel.getAuthor()));
        dataModel.setReportOfTeam(reportOfTeam);

        List<String> prLabels = pr.getLabels().stream()
                .map(GHLabel::getName)
                .collect(Collectors.toCollection(ArrayList::new));
        dataModel.setLabels(prLabels);

        if (null != pr.getClosedAt()) {
            dataModel.setnHoursToResolution(HOURS.between(pr.getCreatedAt().toInstant(), pr.getClosedAt().toInstant()));
        }

        pr.getRequestedReviewers().forEach(reviewer -> {
            try {
                dataModel.addReviewersRequested(User.getLoginName(reviewer));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        pr.getRequestedTeams().forEach(reviewingTeam -> {
            dataModel.addReviewerTeamsRequested(reviewingTeam.getName());
        });


        List<Date> reviewDates = new ArrayList<>();
        pr.listReviews().forEach(review -> {
            try {
                if (review.getState().compareTo(GHPullRequestReviewState.PENDING) != 0) {
                    // Add only effective people who reviewed.
                    dataModel.addReviewers(User.getLoginName(review.getUser()));
                    reviewDates.add(review.getSubmittedAt());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            dataModel.incrementReviews();
        });

        if (reviewDates.size() >= 1) {
            if (reviewDates.size() >= 2) {
                reviewDates.sort((d1, d2) -> d1.compareTo(d2));
            }
            dataModel.setFirstReviewAt(reviewDates.get(0));
            dataModel.setnHoursToFirstResponse(HOURS.between(dataModel.getCreatedDate().toInstant(), dataModel.getFirstReviewAt().toInstant()));
        }

        dataModel.setCompletionsAt(reviewDates);

        return dataModel;
    }
}
