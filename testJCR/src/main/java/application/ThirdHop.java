package application;

import org.apache.jackrabbit.commons.JcrUtils;

import javax.jcr.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ThirdHop {
    public static void main(String[] args) throws RepositoryException {
        Repository repository = JcrUtils.getRepository();
        Session session = repository.login(new SimpleCredentials("admin", "admin".toCharArray()));

        try{
            Node root = session.getRootNode();

            //import the XML file unless already imported
            if(!root.hasNode("importxml")){
                System.out.print("importing xml...");

                Node node = root.addNode("importxml");

                try(FileInputStream fis = new FileInputStream(new File(System.getProperty("user.dir")+"/test.xml"))){
                    session.importXML(node.getPath(), fis, ImportUUIDBehavior.IMPORT_UUID_CREATE_NEW);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                session.save();
                System.out.println("done.");
            }

            dump(root.getNode("importxml"));

        } finally {
            session.logout();
        }

    }

    private static void dump(Node node) throws RepositoryException {
        System.out.println(node.getPath());

        if(node.getName().equals("jcr:system")){
            return;
        }

        PropertyIterator properties = node.getProperties();
        while (properties.hasNext()){
            Property property = properties.nextProperty();
            if(property.getDefinition().isMultiple()){
                Value[] values = property.getValues();
                for(int i=0; i<values.length; i++){
                    System.out.println(property.getPath() + " = " + values[i].getString());
                }
            } else {
                System.out.println(property.getPath() + " = " + property.getString());
            }
        }

        NodeIterator nodes = node.getNodes();
        while (nodes.hasNext()){
            dump(nodes.nextNode());
        }
    }
}
