package osm

import com.meli.osm.DBConnector.PostgresDBConnector

/**
 * This class is a controller that will handle petitions related to Open Street Map functionality.
 * 
 * @author fabianbertetto
 *
 */
class OsmController {

	/**
	 * This method gets reverse geocoding information given some coordinates.
	 * @return a json that represents the point.
	 */
	def getLocation() {
		String lon = params.lon;
		String lat = params.lat;
		def connector = new PostgresDBConnector();
		render connector.getLocation(lon, lat);
	}
}
