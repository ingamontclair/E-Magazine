package edu.emagazine

import com.mongodb.BasicDBObject
import com.mongodb.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoCursor
import com.mongodb.client.MongoDatabase
import org.bson.Document
import org.bson.types.ObjectId

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import java.util.ArrayList

/**
 * APIs for Data Service provider
 */
@Path("emagazine")
class Emagazine(private val mongoClient: MongoClient) {

    /**
     *
     * @return the total number of documents in a collection
     * @throws Exception
     */
    val count: Response
        @Path("articlescount")
        @GET
        @Produces(MediaType.APPLICATION_JSON)
        @Throws(Exception::class)
        get() {

            val database = mongoClient.getDatabase("magazines")
            val col = database.getCollection("articles")
            val result = col.countDocuments()
            return Response.status(200).entity(result).build()
        }

    /**
     *
     * @return all available documents in a collection
     * @throws Exception
     */
    val allArticles: Response
        @Path("articles")
        @GET
        @Produces(MediaType.APPLICATION_JSON)
        @Throws(Exception::class)
        get() {

            val database = mongoClient.getDatabase("magazines")
            val col = database.getCollection("articles")
            val list = ArrayList<String>()
            col.find().iterator().use { cur ->
                while (cur.hasNext()) {
                    val doc = cur.next()
                    list.add(doc.toJson())
                }
            }
            print(list)
            return Response.status(200).entity(list).type(MediaType.TEXT_PLAIN).build()
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
    @Throws(Exception::class)
    fun getArticles(@PathParam("page_num") pageNumber: Int?): Response {
        val database = mongoClient.getDatabase("magazines")
        val col = database.getCollection("articles")
        val result = pagination(col, pageNumber!!, pageSize)
        return Response.status(200).entity(result).type(MediaType.TEXT_PLAIN).build()
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
    @Throws(Exception::class)
    fun getArticleContent(@PathParam("article_id") article_id: String): Response {
        val database = mongoClient.getDatabase("magazines")
        val col = database.getCollection("articles")
        return Response.status(200).entity(findDocumentById(article_id, col)).build()
    }

    /**
     *
     * @param article is a document from a collection
     * @param pageNumber is a current page from navigation
     * @param pageSize is predefined 10 doc/page number
     * @return
     */
    fun pagination(article: MongoCollection<Document>, pageNumber: Int, pageSize: Int): List<String> {
        val result = ArrayList<String>()
        try {
            val cursor = article.find().skip(pageSize * (pageNumber - 1)).limit(pageSize).iterator()
            while (cursor.hasNext()) {
                val art = cursor.next()
                result.add(art.toJson())
            }
            cursor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return result
    }

    /**
     *
     * @param col is a collection where we are looking for a chosen article
     * @return a content of the article
     */
    fun findDocumentById(id: String, col: MongoCollection<*>): String {
        val query = BasicDBObject("_id", ObjectId(id))
        val doc = col.find(query).first() as Document
        return doc.toJson()
    }

    companion object {
        val pageSize = 10
    }
}

