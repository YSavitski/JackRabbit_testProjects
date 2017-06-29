package application;

import org.apache.jackrabbit.commons.JcrUtils;

import javax.jcr.*;

public class FourHop {

    public static final String PATH_REPOSITORY = System.getProperty("user.dir")+"\\jackrabbit";

    public static void main(String[] args) throws RepositoryException {
        Repository repository = JcrUtils.getRepository(PATH_REPOSITORY);
        Session session = repository.login(new SimpleCredentials("admin", "admin".toCharArray()));

        try{

            Node node = session.getRootNode();

            SecondHop.iterateNodes(node);

            session.save();
        } finally {
            session.logout();
        }

    }
}
