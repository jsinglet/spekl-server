package util;

import org.eclipse.egit.github.core.service.TeamService;

import java.io.IOException;

/**
 * Created by jls on 7/22/2015.
 */
public class V3TeamService extends TeamService {

    public void addMember(int id, String user) throws IOException {
        if(user == null) {
            throw new IllegalArgumentException("User cannot be null");
        } else if(user.length() == 0) {
            throw new IllegalArgumentException("User cannot be empty");
        } else {
            StringBuilder uri = new StringBuilder("/teams");
            uri.append('/').append(id);
            uri.append("/memberships");
            uri.append('/').append(user);
            this.client.put(uri.toString());
        }
    }
}
