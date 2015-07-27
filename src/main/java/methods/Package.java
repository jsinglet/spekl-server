package methods;

import exceptions.ProjectExistsException;
import exceptions.RepositoryNotFoundException;
import exceptions.TeamNotFoundException;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.OrganizationService;
import org.eclipse.egit.github.core.service.TeamService;
import util.APIResult;
import util.GitHubUtil;
import verily.lang.*;

import java.io.IOException;

public class Package{

     public static final APIResult create(String project, String username) throws IOException {

         GitHubUtil gh = new GitHubUtil();

         try {
             gh.createNewProject(project, username);
         } catch (ProjectExistsException e) {
             e.printStackTrace();
             return new APIResult(true, e.getMessage());
         } catch (TeamNotFoundException e) {
             e.printStackTrace();
             return new APIResult(true, e.getMessage());
         } catch (RepositoryNotFoundException e) {
             e.printStackTrace();
             return new APIResult(true, e.getMessage());
         }

         return new APIResult("OK");

     }

}
