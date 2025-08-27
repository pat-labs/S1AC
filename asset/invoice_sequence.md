sequenceDiagram
participant Client
participant APIM
participant WebApp
participant Postgres

    Client->>APIM: Request token
    APIM-->>Client: Return token

    Client->>APIM: Call API with headers & params
    alt Invalid headers/params
        APIM-->>Client: 400 / 500 Error
    else Valid request
        APIM->>WebApp: Forward request
        alt WebApp error
            WebApp-->>APIM: 400 / 500 Error
            APIM-->>Client: 400 / 500 Error
        else WebApp processes data
            WebApp->>Postgres: Save data
            alt DB error
                Postgres-->>WebApp: 400 / 500 Error
                WebApp-->>APIM: 400 / 500 Error
                APIM-->>Client: 400 / 500 Error
            else Success
                Postgres-->>WebApp: Success
                WebApp-->>APIM: 200 OK + Data
                APIM-->>Client: 200 OK + Data
            end
        end
    end
