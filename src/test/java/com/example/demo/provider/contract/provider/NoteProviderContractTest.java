package com.example.demo.provider.contract.provider;


import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Consumer;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import com.example.demo.provider.ProviderApplication;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.context.ConfigurableWebApplicationContext;

import static java.lang.System.getenv;
import static java.lang.System.setProperty;
import static java.util.Optional.ofNullable;


@Provider("note_provider")
@Consumer("note_consumer")
@PactFolder("pacts")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@Tag("contract")
public class NoteProviderContractTest {

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @BeforeEach
    void before(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", port));
    }


    @LocalServerPort
    private int port;


    @BeforeAll
    static void enablePublishingPact() {
        setProperty("pact.verifier.publishResults", "true");
        ofNullable(getenv("BRANCH_NAME"))
                .ifPresent(branchName -> setProperty("pact.provider.tag", branchName));
        ofNullable(getenv("GIT_COMMIT"))
                .ifPresent(commitID -> setProperty("pact.provider.version", commitID));
    }

    @State("GET nota 200")
    public void getNota200() {
    }


}
