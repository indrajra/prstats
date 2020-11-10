package com.company;

import org.kohsuke.github.GHTeam;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class TeamInfo {
    private String teamName;
    private Set<String> teamMemberNames = new HashSet<>();
    private Set<GHUser> teamMembers = new HashSet<>();

    public TeamInfo(GitHub github, String orgName, String teamName) {
        if (null != orgName || null != teamName) {
            this.teamName = teamName;
            try {
                GHTeam ghTeam = github.getOrganization(orgName).getTeamByName(teamName);
                teamMembers = ghTeam.getMembers();
                teamMembers.forEach(member -> {
                    try {
                        teamMemberNames.add(User.getLoginName(member));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } catch (IOException e) {
                System.out.println("Can't find the team under the org");
                e.printStackTrace();
            }
        } else {
            System.out.println("No team input considered.");
        }
    }

    public Set<String> getTeamMemberNames() {
        return teamMemberNames;
    }

//    public boolean isSameAsTeam(List<String> teamNames) {
//        return (null != teamName && null != teamNames && teamNames.contains(teamName));
//    }
//
//    public boolean isMember(String name) {
//        return teamMemberNames.isEmpty() || (null != name && teamMemberNames.contains(name));
//    }
//
//    public boolean isMember(List<String> names) {
//        return teamMemberNames.isEmpty() || (null != names && !Collections.disjoint(names, teamMemberNames));
//    }
}
