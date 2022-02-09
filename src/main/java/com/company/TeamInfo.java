package com.company;

import org.kohsuke.github.GHTeam;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;

import java.io.IOException;
import java.util.*;

public class TeamInfo {
    private String teamName;
    private Set<String> teamMemberNames = new HashSet<>();
    private Set<GHUser> teamMembers = new HashSet<>();
    private static final List<String> teamsInterested = new ArrayList<>();

    public void init() {
        String[] temp = {"Team Liskov", "Turing", "Team Ohno", "Bacon", "Church", "Codd", "Hopper", "Clarke", "Bhabha", "Bose", "Hawking", "Kalam", "Euler", "Kapitsa", "Korolev", "Markov", "Raman", "Tesla"};
        teamsInterested.addAll(Arrays.asList(temp));
    }

    public TeamInfo(GitHub github, String orgName, String teamName) {
        init();
        if (null != orgName || null != teamName) {
            this.teamName = teamName;
            try {
                GHTeam ghTeam = github.getOrganization(orgName).getTeamByName(teamName);
                addTeamMembers(ghTeam);
            } catch (IOException e) {
                System.out.println("Can't find the team under the org");
                e.printStackTrace();
            }
        } else {
            System.out.println("No team input considered.");
        }
    }

    public void addTeamMembers(GHTeam ghTeam) {
        try {
            teamMembers = ghTeam.getMembers();

            teamMembers.forEach(member -> {
                try {
                    teamMemberNames.add(User.getLoginName(member));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadUsersIntoCache(GitHub gitHub, String orgName) {
        try {
            UserTeamNameCache userTeamNameCache = UserTeamNameCache.getInstance();
            gitHub.getOrganization(orgName).getTeams().forEach((name, team) -> {
                try {
                    if (teamsInterested.isEmpty() || teamsInterested.contains(name)) {
                        team.getMembers().forEach(member -> {
                            try {
                                String memberName = User.getLoginName(member);
                                userTeamNameCache.add(memberName, name);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                    } else {
                        System.out.println(team.getName() + " is not an interested team");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Set<String> getTeamMemberNames() {
        return teamMemberNames;
    }
}
