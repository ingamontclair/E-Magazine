package edu.emagazine;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.DBObject;
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

@Path("emagazine")
public class Emagazine {
  public static final int pageSize = 10;
  private MongoClient mongoClient;
  public Emagazine(MongoClient mongoClient) {
    this.mongoClient = mongoClient;
  }
  @Path("articlescount")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getCount() throws Exception {
    // main
    MongoDatabase database = mongoClient.getDatabase("magazines");
    MongoCollection col = database.getCollection("articles");
    long result = col.countDocuments();
    return Response.status(200).entity(result).build();
  }

  @Path("articles/{page_num}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getArticles(@PathParam("page_num") Integer pageNumber) throws Exception {
    // main
    System.out.println("tut");
    MongoDatabase database = mongoClient.getDatabase("magazines");
    MongoCollection col = database.getCollection("articles");
    List<String> result = pagination(col, pageNumber, pageSize);
    System.out.println(result);
    return Response.status(200).entity(result).type(MediaType.TEXT_PLAIN).build();
  }

  @Path("article/{article_id}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getArticleContent(@PathParam("article_id") String article_id) throws Exception {
    // main
    MongoDatabase database = mongoClient.getDatabase("magazines");
    MongoCollection col = database.getCollection("articles");

    return Response.status(200).entity(findDocumentById(article_id, col)).build();
  }

  /* collection pagination */
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


  public String findDocumentById(String id, MongoCollection col) {

    BasicDBObject query=new BasicDBObject("_id",new ObjectId(id));


    Document doc =  (Document)col.find(query).first();
    return doc.toJson();
  }
}

