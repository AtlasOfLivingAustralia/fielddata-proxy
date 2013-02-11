class UrlMappings {

	static mappings = {
		"/$portal/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

        "/$controller/$action?/" {

        }

		"/"(controller:"surveyWorkflow")
		"500"(view:'/error')
	}
}
