C4Context
title Component Diagram

Container_Boundary(apim, "APIM BS") {
Component(endpoint, "Endpoint", "REST API", "Handles client requests")
Component(tokenAuth, "Token Authorization", "Auth Module", "Validates tokens")
}

Container_Boundary(webapp, "WebApp") {
Component(getEnv, "Get Environment", "Config Module", "Loads environment variables")
Component(funcEndpoint, "Function Endpoint", "Service Logic", "Processes business logic")
ComponentDb(db, "Database", "Postgres", "Stores application data")
}

Container_Boundary(services, "Services") {
Component(serv, "Servicio 1", "GET data", "GET data from external services")
}

Rel(endpoint, tokenAuth, "Uses for authentication")
Rel(endpoint, getEnv, "Fetches configuration")
Rel(endpoint, funcEndpoint, "Delegates request processing")
Rel(funcEndpoint, db, "Reads/Writes")

Rel(endpoint, funcEndpoint, "Routes requests to WebApp")
Rel(funcEndpoint, serv, "Calls external service (GET data)")
Rel(serv, funcEndpoint, "Returns service data")
