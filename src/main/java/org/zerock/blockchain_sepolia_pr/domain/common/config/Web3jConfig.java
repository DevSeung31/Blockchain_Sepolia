package org.zerock.blockchain_sepolia_pr.domain.common.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Configuration
public class Web3jConfig {

    @Bean
    public Web3j web3j (BlockchainProperties properties) {
        return Web3j.build(new HttpService(properties.getRpcUrl()));
    }

    @Bean
    public Credentials credentials(BlockchainProperties properties) {
        return Credentials.create(properties.getPrivateKey());
    }
}
