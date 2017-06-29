package application;

import org.apache.jackrabbit.core.TransientRepository;

import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.NodeIterator;
import javax.jcr.Node;


public class App {
    public static void main(String[] args) throws RepositoryException {
        Repository repo = new TransientRepository();

        Session session = repo.login(new SimpleCredentials("admin", "admin".toCharArray()));
        Node root = session.getRootNode();

        NodeIterator iterator = root.getNodes();
        while (iterator.hasNext()){
            Node node = iterator.nextNode();
            System.out.println(node.getName());
        }

        session.logout();
    }
}
