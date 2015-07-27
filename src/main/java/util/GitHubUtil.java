package util;

import exceptions.ProjectExistsException;
import exceptions.RepositoryNotFoundException;
import exceptions.TeamNotFoundException;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.Team;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jls on 7/22/2015.
 */
public class GitHubUtil {
    public static final String ORG = "Spekl";



    public boolean projectExists(String projectName) throws IOException {
        // first see if it exists
        List<Repository> existingRepos = getSpeklRepos();
        List<Team>       existingTeams = getSpeklTeams();

        for(Repository r : existingRepos){
            if(r.getName().equalsIgnoreCase(projectName))
                return true;
        }

        for(Team t : existingTeams){
            if(t.getName().equalsIgnoreCase(projectName)){
                return true;
            }
        }

        return false;
    }
    public void createNewProject(String projectName, String author) throws IOException, ProjectExistsException, TeamNotFoundException, RepositoryNotFoundException {

        if(projectExists(projectName)){
            throw new ProjectExistsException("Project already exists");
        }

        // try to create it

        //
        // Step 1, create the repository
        //
        createRepo(projectName);

        //
        // Step 2, create a new team and invite the user
        //
        createTeam(projectName, projectName, author);
    }


    public void createTeam(String projectName, String teamName, String admin) throws IOException, TeamNotFoundException, RepositoryNotFoundException {


        TeamService ts = new V3TeamService();
        authorize(ts);

        Team team = new Team();
        team.setName(teamName);
        team.setPermission("admin");

        ts.createTeam(ORG, team);

        ts.addMember(getTeamByName(teamName).getId(), admin);

        ts.addRepository(getTeamByName(teamName).getId(), getRepo(projectName));

    }

    public Team getTeamByName(String team) throws IOException, TeamNotFoundException {
        for(Team t : getSpeklTeams()){
            if(t.getName().equalsIgnoreCase(team))
                return t;
        }
        throw new TeamNotFoundException("Created team could not be found.");
    }

    public User getUser(String username) throws IOException {
        UserService users = new UserService();
        authorize(users);

        return users.getUser(username);
    }

    public Repository getRepo(String repo) throws IOException, RepositoryNotFoundException {
        RepositoryService rs = new RepositoryService();
        authorize(rs);

        for(Repository r : rs.getOrgRepositories(ORG)){
            if(r.getName().equalsIgnoreCase(repo)){
                return r;
            }
        }
        throw new RepositoryNotFoundException("Repo not found after creation");
    }

    public void createRepo(String projectName) throws IOException {
        RepositoryService r = new RepositoryService();

        authorize(r);
        Repository repo = new Repository();
        repo.setName(projectName);
        r.createRepository(ORG, repo);

    }

    public void authorize(GitHubService s){
        s.getClient().setOAuth2Token(System.getenv("GH_API_KEY"));
    }

    //
    // doesn't need authentication
    //
    public List<Repository> getSpeklRepos() throws IOException {
        return new RepositoryService().getOrgRepositories(ORG);
    }

    public List<Team> getSpeklTeams() throws IOException {
        TeamService ts = new TeamService();
        authorize(ts);

        return ts.getTeams(ORG);
    }

}
