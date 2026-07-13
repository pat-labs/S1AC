package com.pat.s1ac.infrastructure.bootstrap.injector;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.pat.s1ac.application.use_case.InvoiceUseCase;
import com.pat.s1ac.domain.validator.AuditValidator;
import com.pat.s1ac.domain.validator.InvoiceItemValidator;
import com.pat.s1ac.domain.validator.InvoicePaymentDetailValidator;
import com.pat.s1ac.domain.validator.InvoiceValidator;
import com.pat.s1ac.infrastructure.bootstrap.Bootstrap;
import com.pat.s1ac.infrastructure.bootstrap.Env;
import com.pat.s1ac.infrastructure.mygrpc.service.CompanyService;
import com.pat.s1ac.infrastructure.mygrpc.service.PersonService;
import com.pat.s1ac.infrastructure.mygrpc.service.ProductService;
import com.pat.s1ac.infrastructure.mymongo.MyMongo;
import com.pat.s1ac.infrastructure.mymongo.use_case.InvoiceMongoRead;
import com.pat.s1ac.infrastructure.mymongo.use_case.InvoiceMongoWrite;
import com.pat.s1ac.infrastructure.myrabbitmq.MyRabbitmq;
import com.pat.s1ac.infrastructure.myrabbitmq.use_case.InvoiceRabbitMQ;

import com.google.inject.Singleton;

public class AppInjector extends AbstractModule {
    @Override
    protected void configure() {
        // Direct bindings if needed
        bind(PersonService.class).in(Singleton.class);
        bind(CompanyService.class).in(Singleton.class);
        bind(ProductService.class).in(Singleton.class);
    }

    @Provides
    @Singleton
    Bootstrap provideBootstrap() throws Exception {
        return Env.fromFile(".env");
    }

    @Provides
    @Singleton
    MyMongo provideMongo(Bootstrap bootstrap) {
        return new MyMongo(bootstrap.mongoConfig());
    }

    @Provides
    @Singleton
    MyRabbitmq provideRabbitmq(Bootstrap bootstrap) {
        return new MyRabbitmq(bootstrap.rabbitMQConfig());
    }

    @Provides
    @Singleton
    InvoiceMongoWrite provideInvoiceMongoWrite(MyMongo mongo) {
        return new InvoiceMongoWrite(mongo);
    }

    @Provides
    @Singleton
    InvoiceMongoRead provideInvoiceMongoRead(MyMongo mongo) {
        return new InvoiceMongoRead(mongo);
    }

    @Provides
    @Singleton
    InvoiceRabbitMQ provideInvoiceRabbitMQ(MyRabbitmq rabbitmq) {
        return new InvoiceRabbitMQ(rabbitmq);
    }

    @Provides
    @Singleton
    AuditValidator provideAuditValidator(PersonService personService) {
        return new AuditValidator(personService);
    }

    @Provides
    @Singleton
    InvoiceItemValidator provideInvoiceItemValidator(ProductService productService) {
        return new InvoiceItemValidator(productService);
    }

    @Provides
    @Singleton
    InvoicePaymentDetailValidator provideInvoicePaymentDetailValidator(InvoiceMongoRead invoiceMongoRead) {
        return new InvoicePaymentDetailValidator(invoiceMongoRead);
    }

    @Provides
    @Singleton
    InvoiceValidator provideInvoiceValidator(InvoiceMongoRead invoiceMongoRead,
                                             CompanyService companyService,
                                             PersonService personService) {
        return new InvoiceValidator(invoiceMongoRead, companyService, personService);
    }

    @Provides
    @Singleton
    InvoiceUseCase provideInvoiceUseCase(AuditValidator auditValidator,
                                         InvoiceValidator invoiceValidator,
                                         InvoiceMongoWrite invoiceMongoWrite,
                                         InvoiceRabbitMQ invoiceRabbitMQ) {
        return new InvoiceUseCase(auditValidator, invoiceValidator, invoiceMongoWrite, invoiceRabbitMQ);
    }
}
