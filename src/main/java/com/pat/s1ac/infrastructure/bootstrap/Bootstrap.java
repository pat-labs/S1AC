package com.pat.s1ac.infrastructure.bootstrap;

import com.pat.s1ac.infrastructure.mymongo.MongoConfig;
import com.pat.s1ac.infrastructure.myrabbitmq.RabbitMQConfig;

public record Bootstrap(
    App app,
    MongoConfig mongoConfig,
    RabbitMQConfig rabbitMQConfig,
    Service service
) {

    public record App(String host, String version, String port) {}
    public record Service(
        String urlInvoiceDocumentTypeEnum,
        String urlCompanyBranchId,
        String urlPersonId,
        String urlProductId,
        String urlProductUnitEnum,
        String urlPaymentMethodEnum,
        String urlMoneyCurrencyEnum
    ) {}

    public static Bootstrap fromEnv() {
        App app = new App(
            System.getenv("APP_HOST"),
            System.getenv("APP_VERSION"),
            System.getenv("APP_PORT")
        );

        var mongoClient = new MongoConfig(
            System.getenv("MONGO_HOST"),
            System.getenv("MONGO_PORT"),
            System.getenv("MONGO_USER"),
            System.getenv("MONGO_PASSWORD"),
            System.getenv("MONGO_DB")
        );

        var rabbitMQClient = new RabbitMQConfig(
            System.getenv("RABBITMQ_HOST"),
            System.getenv("RABBITMQ_PORT"),
            System.getenv("RABBITMQ_USER"),
            System.getenv("RABBITMQ_PASSWORD")
        );

        Service service = new Service(
            System.getenv("SERVICE_URL_INVOICE_DOCUMENT_TYPE_ENUM"),
            System.getenv("SERVICE_URL_COMPANY_BRANCH_ID"),
            System.getenv("SERVICE_URL_PERSON_ID"),
            System.getenv("SERVICE_URL_PRODUCT_ID"),
            System.getenv("SERVICE_URL_PRODUCT_UNIT_ENUM"),
            System.getenv("SERVICE_URL_PAYMENT_METHOD_ENUM"),
            System.getenv("SERVICE_URL_MONEY_CURRENCY_ENUM")
        );

        return new Bootstrap(app, mongoClient, rabbitMQClient, service);
    }
}
