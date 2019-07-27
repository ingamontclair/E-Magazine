package edu.emagazine;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("api")
public class EmagazineApplication extends Application {

  public EmagazineApplication() {
  }

  /**
   *
   * @return entry point http://localhost:8085/api/
   */
  @Override
  public Set<Object> getSingletons() {
    HashSet<Object> set = new HashSet<Object>();
    //DataBase connection
    MongoClientURI uri = new MongoClientURI(
      "mongodb+srv://AdminEx:AdminEx@clusterex-cya5a.mongodb.net/test?retryWrites=true&w=majority");
    MongoClient mongoClient = new MongoClient(uri);

    set.add(new Emagazine(mongoClient));
    return set;
  }
}

