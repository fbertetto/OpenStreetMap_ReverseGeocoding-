OSM Geocoding
=

This API implements an Open Street Maps reverse geocoding solution.

Use the API
____

Get reverse geocoding for a point
-

* URL

**/getLocation?lon={longitude}&lat={latitude}**

* Method

"GET"

* URL PARAMETERS

This method require the coordinates of the point to reverse geocode. This parameters must be specified on the url using query strings.

Required:

    * lon: Longitude of the point.

    * lat: Latitude of the point.

* SUCCESS RESPONSE

Code: 200

This will return a list of suggestions with the following structure.

    {"country":{"name":"Country"},"state":{"name":"State"},"city":{"name":"City"},"zip_code":zip_code,"street_name":street,"street_number":number}

if any of the fields on the response doesn't have and associated value, it will contain null. 

----

* NOTES

For adding new maps please read carefully the meli scripts.

* Questions?

Ask fabianbertetto@gmail.com

Pull requests are welcome


