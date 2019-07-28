package edu.emagazine

import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import java.util.HashSet
import javax.ws.rs.ApplicationPath
import javax.ws.rs.core.Application

@ApplicationPath("api")
class EmagazineApplication : Application() {

    /**
     *
     * @return entry point http://localhost:8085/api/
     */
    override fun getSingletons(): Set<Any> {
        val set = HashSet<Any>()
        //DataBase connection
        val uri = MongoClientURI(
                "mongodb+srv://AdminEx:AdminEx@clusterex-cya5a.mongodb.net/test?retryWrites=true&w=majority")
        val mongoClient = MongoClient(uri)

        set.add(Emagazine(mongoClient))
        return set
    }
}

