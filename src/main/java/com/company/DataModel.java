package com.company;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.ALWAYS)
@JsonPropertyOrder
public class DataModel {
    private long id;
    private String status;

    private String author;
    @JsonSerialize(contentUsing = JsonDateSerializer.class)
    private Date createdDate;
    @JsonSerialize(contentUsing = JsonDateSerializer.class)
    private Date firstReviewAt;
    @JsonSerialize(contentUsing = JsonDateSerializer.class)
    private Date closedDate;
    private long nHoursToResolution;
    private long nHoursToFirstResponse;
    private int nReviews;
    private boolean isReviewedByOtherTeam;
    private boolean isReviewedForOtherTeam;
    private List<String> labels = new ArrayList<>();

    private int nReviewComments;
    private int nComments;
    private int nFiles;
    private int nAdditions;
    private int nDeletions;
    private List<String> reviewers = new ArrayList<>();

    @JsonSerialize(contentUsing = JsonDateSerializer.class)
    private List<Date> completionsAt = new ArrayList<>();

    private List<String> reviewerTeamsRequested = new ArrayList<>();
    private List<String> reviewersUnanswered = new ArrayList<>();
    private String title;

    public long getnHoursToFirstResponse() {
        return nHoursToFirstResponse;
    }

    public void setnHoursToFirstResponse(long nHoursToFirstResponse) {
        this.nHoursToFirstResponse = nHoursToFirstResponse;
    }

    public boolean isReviewedForOtherTeam() {
        return isReviewedForOtherTeam;
    }

    public void setReviewedForOtherTeam(boolean reviewedForOtherTeam) {
        isReviewedForOtherTeam = reviewedForOtherTeam;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public boolean isReviewedByOtherTeam() {
        return isReviewedByOtherTeam;
    }

    public void setReviewedByOtherTeam(boolean reviewedByOtherTeam) {
        isReviewedByOtherTeam = reviewedByOtherTeam;
    }

    public long getnHoursToResolution() {
        return nHoursToResolution;
    }

    public void setnHoursToResolution(long nHoursToResolution) {
        this.nHoursToResolution = nHoursToResolution;
    }

    public DataModel setnReviewComments(int nReviewComments) {
        this.nReviewComments = nReviewComments;
        return this;
    }

    public int incrementReviewComments() {
        return ++this.nReviews;
    }

    public int getnReviews() {
        return nReviews;
    }

    public DataModel setnReviews(int nReviews) {
        this.nReviews = nReviews;
        return this;
    }

    public int incrementReviews() {
        return ++this.nReviews;
    }

    public long getId() {
        return id;
    }

    public DataModel setId(long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public DataModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public DataModel setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public DataModel setAuthor(String author) {
        this.author = author;
        return this;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public DataModel setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public Date getFirstReviewAt() {
        return firstReviewAt;
    }

    public DataModel setFirstReviewAt(Date firstReviewAt) {
        this.firstReviewAt = firstReviewAt;
        return this;
    }

    public Date getClosedDate() {
        return closedDate;
    }

    public DataModel setClosedDate(Date closedDate) {
        this.closedDate = closedDate;
        return this;
    }

    public List<String> getReviewers() {
        return reviewers;
    }

    public DataModel setReviewers(List<String> reviewers) {
        this.reviewers = reviewers;
        return this;
    }

    public void addReviewers(String name) {
        this.reviewers.add(name);
    }

    public List<Date> getCompletionsAt() {
        return completionsAt;
    }

    public DataModel setCompletionsAt(List<Date> completionsAt) {
        this.completionsAt = completionsAt;
        return this;
    }

    public DataModel addCompletionsAt(Date completedAt) {
        this.completionsAt.add(completedAt);
        return this;
    }

    public List<String> getReviewersUnanswered() {
        return reviewersUnanswered;
    }

    public DataModel setReviewersUnanswered(List<String> reviewersUnanswered) {
        this.reviewersUnanswered = reviewersUnanswered;
        return this;
    }

    public void addReviewersRequested(String name) {
        this.reviewersUnanswered.add(name);
    }

    public List<String> getReviewerTeamsRequested() {
        return reviewerTeamsRequested;
    }

    public DataModel setReviewerTeamsRequested(List<String> reviewersRequested) {
        this.reviewerTeamsRequested = reviewersRequested;
        return this;
    }

    public void addReviewerTeamsRequested(String name) {
        this.reviewerTeamsRequested.add(name);
    }

    public int getnComments() {
        return nComments;
    }

    public DataModel setnComments(int nComments) {
        this.nComments = nComments;
        return this;
    }

    public int getnFiles() {
        return nFiles;
    }

    public DataModel setnFiles(int nFiles) {
        this.nFiles = nFiles;
        return this;
    }

    public long getnAdditions() {
        return nAdditions;
    }

    public DataModel setnAdditions(int nChanges) {
        this.nAdditions = nChanges;
        return this;
    }
    public long getnDeletions() {
        return nDeletions;
    }

    public DataModel setnDeletions(int nChanges) {
        this.nDeletions = nChanges;
        return this;
    }
}
