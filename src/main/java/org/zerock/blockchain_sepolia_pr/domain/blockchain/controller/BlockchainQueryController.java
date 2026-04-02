package org.zerock.blockchain_sepolia_pr.domain.blockchain.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.blockchain_sepolia_pr.domain.blockchain.entity.BlockchainQueue;
import org.zerock.blockchain_sepolia_pr.domain.blockchain.entity.BlockchainTx;
import org.zerock.blockchain_sepolia_pr.domain.blockchain.repository.BlockchainQueueRepository;
import org.zerock.blockchain_sepolia_pr.domain.blockchain.repository.BlockchainTxRepository;

@RestController
@RequestMapping("/api/blockchain")
public class BlockchainQueryController {

    private final BlockchainQueueRepository blockchainQueueRepository;
    private final BlockchainTxRepository blockchainTxRepository;

    public BlockchainQueryController(BlockchainQueueRepository blockchainQueueRepository, BlockchainTxRepository blockchainTxRepository) {
        this.blockchainQueueRepository = blockchainQueueRepository;
        this.blockchainTxRepository = blockchainTxRepository;
    }

    @GetMapping("/queues/{queueId}")
    public BlockchainQueue getQueue(@PathVariable Long queueId) {
        return blockchainQueueRepository.findById(queueId)
                .orElseThrow(() -> new IllegalStateException("queue 없음"));
    }

    @GetMapping("/tx/latest-by-trade/{tradeId}")
    public BlockchainTx getLatestTxByTrade(@PathVariable Long tradeId) {
        return blockchainTxRepository.findTop1ByTradeIdOrderByBlockchainTxIdDesc(tradeId)
                .orElseThrow(() -> new IllegalStateException("tx 없음"));
    }
}


