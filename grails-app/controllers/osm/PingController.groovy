package osm
import com.meli.osm.DBConnector.PostgresDBConnector
import grails.rest.RestfulController

/**
 * This class is just to test correct functionality of the server.
 * 
 * @author fabianbertetto
 *
 */
class PingController extends RestfulController {
	def ping = { render "pong" }
}
