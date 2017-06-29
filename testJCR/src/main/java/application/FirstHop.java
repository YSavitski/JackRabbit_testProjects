package application;

import org.apache.jackrabbit.commons.JcrUtils;

import javax.jcr.GuestCredentials;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

public class FirstHop {
    public static void main(String[] args) throws RepositoryException {
        Repository repository = JcrUtils.getRepository();

        Session session = repository.login(new GuestCredentials());

        try {
            String user = session.getUserID();
            String name = repository.getDescriptor(Repository.REP_NAME_DESC);
            String version = repository.getDescriptor(Repository.REP_VERSION_DESC);

            System.out.println("Logged is as " + user + " to a " + name + " repository. Version: " + version);
        } finally {
            session.logout();
        }
    }
}
