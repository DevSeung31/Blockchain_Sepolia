package org.zerock.blockchain_sepolia_pr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.zerock.blockchain_sepolia_pr.domain.common.config.BlockchainProperties;

@SpringBootApplication
@EnableConfigurationProperties(BlockchainProperties.class)
public class BlockChainSepoliaPrApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlockChainSepoliaPrApplication.class, args);
    }

}
