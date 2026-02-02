package com.projeto.config;

import net.ravendb.client.documents.DocumentStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Essa classe serve para conectar o spring boot ao ravenDB
// Sempre que algo pedir acesso ao banco, vai usar essa conexão

@Configuration
public class RavenConfig {

    @Bean
    public DocumentStore documentStore() {
        DocumentStore store = new DocumentStore(
                new String[]{"http://localhost:8080"}, "MinhaBase"
        );
        store.initialize();
        return store;
    }
}
