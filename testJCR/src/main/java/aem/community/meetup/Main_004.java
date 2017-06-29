package aem.community.meetup;

import application.SecondHop;
import org.apache.jackrabbit.api.JackrabbitSession;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.jackrabbit.oak.Oak;
import org.apache.jackrabbit.oak.jcr.Jcr;
import org.apache.jackrabbit.oak.segment.SegmentNodeStore;
import org.apache.jackrabbit.oak.segment.SegmentNodeStoreBuilders;
import org.apache.jackrabbit.oak.segment.file.FileStore;
import org.apache.jackrabbit.oak.segment.file.FileStoreBuilder;
import org.apache.jackrabbit.oak.segment.file.InvalidFileStoreVersionException;

import javax.jcr.*;
import java.io.IOException;

public class Main_004 {
    public static void main(String[] args) {
        FileStoreBuilder fsb = FileStoreBuilder.fileStoreBuilder(Util.directory);
        Session session = null;

        try(FileStore fileStore = fsb.build()){
            SegmentNodeStore nodeStore = SegmentNodeStoreBuilders.builder(fileStore).build();
            Repository repository = new Jcr(new Oak(nodeStore)).createRepository();
            session = repository.login(Util.getAdminCreds());

            JackrabbitSession jackrabbitSession = (JackrabbitSession) session;
            UserManager userManager = jackrabbitSession.getUserManager();
            userManager.createUser("username1", "username1");
            userManager.createUser("username2", "username2");
            jackrabbitSession.save();

            /*Node root = session.getRootNode();
            SecondHop.iterateNodes(root);
            session.save();*/

        } catch (InvalidFileStoreVersionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LoginException e) {
            e.printStackTrace();
        } catch (RepositoryException e) {
            e.printStackTrace();
        } finally {
            if(session!=null){
                session.logout();
            }

        }
    }
}
