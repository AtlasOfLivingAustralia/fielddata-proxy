class UrlMappings {

	static mappings = {
		"/$portal/$controller/$action?/$id?"{
			constraints {

			}
		}

        "/$portal/$controller/$id?/"(action:"getWithoutSpecies") {
            constraints {
                id matches: /\d+/
                controller matches: /survey/
            }
        }

        "/$controller/$action?/" {

        }


		"/"(controller:"surveyWorkflow")
		"500"(view:'/error')
	}
}
