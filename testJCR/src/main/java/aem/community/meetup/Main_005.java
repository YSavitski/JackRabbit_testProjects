package aem.community.meetup;

import org.apache.jackrabbit.oak.Oak;
import org.apache.jackrabbit.oak.jcr.Jcr;
import org.apache.jackrabbit.oak.segment.SegmentNodeStore;
import org.apache.jackrabbit.oak.segment.SegmentNodeStoreBuilders;
import org.apache.jackrabbit.oak.segment.file.FileStore;
import org.apache.jackrabbit.oak.segment.file.FileStoreBuilder;
import org.apache.jackrabbit.oak.segment.file.InvalidFileStoreVersionException;

import javax.jcr.*;
import java.io.IOException;

public class Main_005 {
    public static void main(String[] args) {
        FileStoreBuilder fsb = FileStoreBuilder.fileStoreBuilder(Util.directory);
        Session session = null;

        try(FileStore fileStore = fsb.build()){

            SegmentNodeStore nodeStore = SegmentNodeStoreBuilders.builder(fileStore).build();
            Repository repository = new Jcr(new Oak(nodeStore)).createRepository();

            for (Credentials credentials : Util.CREDS){
                session = repository.login(credentials);
                Util.checkPermissions(session, "/");
                Util.checkPermissions(session, "/hello");
                Util.checkPermissions(session, "/hello/world");
                session.save();
            }
        } catch (InvalidFileStoreVersionException | IOException e) {
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
