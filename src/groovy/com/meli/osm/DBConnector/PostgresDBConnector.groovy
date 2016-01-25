package com.meli.osm.DBConnector;

import java.sql.*;

import javax.swing.JOptionPane;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.meli.osm.location.entities.*;

/**
 * This class connects to Open Street Map DB to get specific information for points.
 * 
 * @author fabianbertetto
 *
 */
public class PostgresDBConnector {

	/**
	 * Complete URL to reach OSM DB. 
	 */
	private final String dbURL ="jdbc:postgresql://localhost/gis";
	/**
	 * User to access to OSM DB.
	 */
	private final String user ="oraweb";
	/**
	 * Password to access to OSM DB.
	 */
	private final String pass = "oraweb";
	/**
	 * Connection to DB.
	 */
	private Connection connection;

	/**
	 * This method implements a singleton pattern and ensure that only one instance of DB Connection is in use.
	 * 
	 * @return DB Connection
	 * @throws SQLException is there is some configuration problems.
	 */
	public Connection getConnection() throws SQLException {
		if (connection == null || connection.isClosed()) {
			Properties properties = new Properties();
			def driver= Class.forName("org.postgresql.Driver", false, this.class.classLoader).newInstance();
			properties.setProperty('user',user);
			properties.setProperty('password',pass);
			connection = driver.connect(dbURL,properties);
		}
		return connection;
	}

	/**
	 * Gets information of a point given the coordinates.
	 * 
	 * @param longitude of the point.
	 * @param latitude of the point.
	 * @return a json that represents a PointLocation object.
	 */
	public String getLocation(final String lon, final String lat){
		PointLocation point = new PointLocation();
		HashMap<String,String> stateInfo = getStateInfo(lon, lat);
		HashMap<String,String> addressInfo = getAddressInfo(lon, lat);
		HashMap<String,String> cityInfo = getCityInfo(lon, lat);
		point.setCountry(stateInfo.get("country"));
		point.setState(stateInfo.get("state"));
		point.setCity(cityInfo.get("city"));
		point.setZipCode(cityInfo.get("zipcode"));
		point.setStreetName(addressInfo.get("streetname"));
		point.setStreetNumber(addressInfo.get("streetnumber"));
		if(point.getCity() == null && addressInfo.get("city") != null) {
			City city = new City();
			city.setName(addressInfo.get("city"))
			point.setCity(city);
		}
		if(point.getZipCode() == null && addressInfo.get("zipcode") != null) {
			point.setZipCode(addressInfo.get("zipcode"));
		}

		String result = marshalPoint(point);
		connection.close();
		return result;
	}

	/**
	 * This method gets the address information of a point also will try to get city and zip code.
	 * 
	 * @param longitude of the point.
	 * @param latitude of the point.
	 * @return a map containing street name and street number.
	 * @throws SQLException if there is a problem with the query.
	 */
	private Map<String, String> getAddressInfo(final String lon, final String lat) throws SQLException {
		//TODO move this query to a configuration file.
		String addressQuery = "select distinct address.tags->'addr:city' as city, address.tags->'addr:postcode' as zipcode, address.tags->'addr:street' as streetname, address.\"addr:housenumber\" as streetnumber from planet_osm_point address where address.way && ST_Buffer(st_transform(st_setsrid(st_makepoint($lon,$lat),4326),3857), 50) and (address.tags->'addr:street') is not null limit 1;";
		HashMap<String, String> map = new HashMap();
		ResultSet rs = getConnection().createStatement().executeQuery(addressQuery);
		while (rs.next()) {
			map.put("streetname", rs.getString("streetname"));
			map.put("streetnumber", rs.getString("streetnumber"));
			map.put("city", rs.getString("city"));
			map.put("zipcode", rs.getString("zipcode"));

		}
		return map;
	}

	/**
	 * This method gets the state information of a point.
	 * 
	 * @param longitude of the point.
	 * @param latitude of the point.
	 * @return a map containing country and state.
	 * @throws SQLException if there is a problem with the query.
	 */
	private Map<String, String> getStateInfo(final String lon, final String lat) throws SQLException {
		//TODO move this query to a configuration file.
		String stateQuery = "select distinct state.tags->'is_in:country' as country, state.name as state from planet_osm_polygon state where st_within(st_transform(st_setsrid(st_makepoint($lon, $lat),4326),3857),st_setsrid(state.way,3857)) and state.boundary='administrative' and state.admin_level='4';";
		HashMap<String, String> map = new HashMap();
		ResultSet rs = getConnection().createStatement().executeQuery(stateQuery);
		State state = new State();
		Country country = new Country();
		while (rs.next()) {
			country.setName(rs.getString("country"));
			state.setName(rs.getString("state"));
		}
		map.put("country", country);
		map.put("state", state);
		return map;
	}

	/**
	 * This method gets the city information of a point.
	 *
	 * @param longitude of the point.
	 * @param latitude of the point.
	 * @return a map containing city and zipcode.
	 * @throws SQLException if there is a problem with the query.
	 */
	private Map<String, String> getCityInfo(final String lon, final String lat) {
		String stateQuery = "select distinct city.name as city, city.tags->'addr:postcode' as zipCode, city.tags->'postal_code' as postalCode from planet_osm_polygon city where st_within(st_transform(st_setsrid(st_makepoint($lon, $lat),4326),3857),st_setsrid(city.way,3857)) and city.boundary='administrative' and city.admin_level='8';";
		HashMap<String, String> map = new HashMap();
		ResultSet rs = getConnection().createStatement().executeQuery(stateQuery);
		City city;
		String zipcode;
		while (rs.next()) {
			city = new City();
			city.setName(rs.getString("city"));
			if (rs.getString("zipcode") == null) {
				zipcode = rs.getString("postalCode");
			}
			else {
				zipcode = rs.getString("zipcode");
			}
		}
		map.put("city", city);
		map.put("zipcode", zipcode);
		return map;
	}

	/**
	 * This method Marshals an object to a json structure. 
	 * 
	 * @param PointLocation point.
	 * @return json that represents the object.
	 */
	public String marshalPoint(PointLocation point) {
		GsonBuilder gb = new GsonBuilder();
		gb.serializeNulls();
		Gson gson = gb.create();
		String json = gson.toJson(point);
		return json;

	}
}
