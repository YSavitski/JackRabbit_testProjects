package application;

import org.apache.jackrabbit.commons.JcrUtils;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import javax.jcr.*;

public class SecondHop {
    public static void main(String[] args) throws RepositoryException {
        Repository repository = JcrUtils.getRepository();
        Session session = repository.login(new SimpleCredentials("admin", "admin".toCharArray()));

        try{
            Node root = session.getRootNode();
            System.out.println("root node: " + root.getIdentifier() + " " + root.getName() + " " + root.getPath());

            // Store content
            Node hello = root.addNode("hello");
            Node world = root.addNode("world");

            hello.setProperty("long property", 10L);

            world.setProperty("message", "Hello, World!");
            world.setProperty("new_message", "Hello, Yauheni");

            Node childHello = hello.addNode("childNode");
            childHello.setProperty("child", "child node");



            iterateNodes(root);
            //root.getNode("hello").remove();

            session.save();


        } finally {
            session.logout();
        }
    }


    public static void printNodeInfo(Node node) throws RepositoryException {
        System.out.println("Name: " + node.getName());
        System.out.println("Path: " + node.getPath());

        PropertyIterator propertyIterator = node.getProperties();

        while (propertyIterator.hasNext()){
            System.out.println("Property: " + propertyIterator.nextProperty());
        }
        System.out.println("=================================\n\n");
    }

    public static void iterateNodes(Node inputNode) throws RepositoryException {
        NodeIterator nodes = inputNode.getNodes();
        if(!(inputNode.getPath().contains("jcr:") || inputNode.getPath().contains("rep:"))){
            printNodeInfo(inputNode);
        }


        while (nodes.hasNext()){
            iterateNodes(nodes.nextNode());
        }

    }
}
