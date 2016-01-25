class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?(.$format)?"{ constraints { // apply constraints here
			} }

		"/ping"{
			controller = "ping"
			action = "ping"
		}
		"/getLocation"{
			controller = "osm"
			action = "getLocation"
		}
		"/"(view:"/index")
		"500"(view:'/error')
	}
}
