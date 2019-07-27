package edu.emagazine;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * APIs for Data Service provider
 */
@Path("emagazine")
public class Emagazine {
  public static final int pageSize = 10;
  private MongoClient mongoClient;
  public Emagazine(MongoClient mongoClient) {
    this.mongoClient = mongoClient;
  }

  /**
   *
   * @return the total number of documents in a collection
   * @throws Exception
   */
  @Path("articlescount")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getCount() throws Exception {

    MongoDatabase database = mongoClient.getDatabase("magazines");
    MongoCollection col = database.getCollection("articles");
    long result = col.countDocuments();
    return Response.status(200).entity(result).build();
  }

  /**
   *
   * @param pageNumber gets page from navigation on the web page
   * @return 10 documents on the page
   * @throws Exception
   */
  @Path("articles/{page_num}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getArticles(@PathParam("page_num") Integer pageNumber) throws Exception {
    MongoDatabase database = mongoClient.getDatabase("magazines");
    MongoCollection col = database.getCollection("articles");
    List<String> result = pagination(col, pageNumber, pageSize);
    return Response.status(200).entity(result).type(MediaType.TEXT_PLAIN).build();
  }

  /**
   *
   * @return all available documents in a collection
   * @throws Exception
   */
  @Path("articles")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllArticles() throws Exception {

    MongoDatabase database = mongoClient.getDatabase("magazines");
    MongoCollection<Document> col = database.getCollection("articles");
    List<String> list = new ArrayList<String>();
    try (MongoCursor<Document> cur = col.find().iterator()) {
      while (cur.hasNext()) {
        Document doc = cur.next();
        list.add(doc.toJson());
      }
    }
    System.out.print(list);
    return Response.status(200).entity(list).type(MediaType.TEXT_PLAIN).build();
  }

  /**
   *
   * @param article_id is an article from the list
   * @return a content of chosen article
   * @throws Exception
   */
  @Path("article/{article_id}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getArticleContent(@PathParam("article_id") String article_id) throws Exception {
    MongoDatabase database = mongoClient.getDatabase("magazines");
    MongoCollection col = database.getCollection("articles");
    return Response.status(200).entity(findDocumentById(article_id, col)).build();
  }

  /**
   *
   * @param article is a document from a collection
   * @param pageNumber is a current page from navigation
   * @param pageSize is predefined 10 doc/page number
   * @return
   */
  public List<String> pagination(MongoCollection <Document> article, int pageNumber, int pageSize) {
    ArrayList<String> result = new ArrayList<>();
    try {
      MongoCursor<Document> cursor = article.find().skip(pageSize * (pageNumber - 1)).limit(pageSize).iterator();
      while (cursor.hasNext()) {
        Document art = cursor.next();
        result.add(art.toJson());
      }
      cursor.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   *
   * @param col is a collection where we are looking for a chosen article
   * @return a content of the article
   */
  public String findDocumentById(String id, MongoCollection col) {
    BasicDBObject query=new BasicDBObject("_id",new ObjectId(id));
    Document doc =  (Document)col.find(query).first();
    return doc.toJson();
  }
}

