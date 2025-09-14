package com.pat.s1ac.infrastructure.mymongo;

public record MongoConfig(String host, String port, String user, String password, String database) {
}
