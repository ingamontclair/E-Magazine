import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import edu.emagazine.Emagazine;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.List;

public class Tester {

  private MongoClient mongoClient = null;

  private MongoClient getMongoClient() {
    if (mongoClient != null)
      return mongoClient;

    MongoClientURI uri = new MongoClientURI(
      "mongodb+srv://AdminEx:AdminEx@clusterex-cya5a.mongodb.net/test?retryWrites=true&w=majority");

    mongoClient = new MongoClient(uri);
    return mongoClient;
  }

  /**
   * Test method for Database connection
   */
  @Test
  public void testMangoConnection () {

    MongoClient mongoClient = getMongoClient();
    MongoDatabase database = mongoClient.getDatabase("magazines");
    Assert.assertNotNull("Could not get magazine collection", database);
  }

  /**
   *
   * Test method for Total Number of Documents
   */
  @Test
  public void testCountArticles() throws Exception {

    MongoClient mongoClient = getMongoClient();
    Emagazine emagazine = new Emagazine(mongoClient);
    Response count = emagazine.getCount();
    System.out.println("count = " + count.getEntity());
    Assert.assertTrue("No articles found", (long) count.getEntity() > 0);
    Assert.assertTrue("Articles collection should be setup wih at least 50 HTML articles", (long) count.getEntity() > 50 );
  }

  /**
   *
   * Test method for getting articles with a page number
   */
  @Test
  public void testGetArticles() throws Exception {

    MongoClient mongoClient = getMongoClient();
    Emagazine emagazine = new Emagazine(mongoClient);
    //test 1st page
    Response articlePage = emagazine.getArticles(1);
    Assert.assertTrue("The first page should have 10 articles" , ((List) articlePage.getEntity()).size() == 10);

  }

}

