package org.zerock.blockchain_sepolia_pr.domain.blockchain.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.blockchain_sepolia_pr.domain.blockchain.service.BlockchainQueueProcessService;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/blockchain")
public class BlockchainAdminController {

    private final BlockchainQueueProcessService blockchainQueueProcessService;


    public BlockchainAdminController(BlockchainQueueProcessService blockchainQueueProcessService) {
        this.blockchainQueueProcessService = blockchainQueueProcessService;
    }

    @PostMapping("/process-next")
    public Map<String, Object> processNext() {
        Long blockchainTxId = blockchainQueueProcessService.processNext();
        return Map.of (
                "message", "queue 처리 완료",
                "blockchainTxId", blockchainTxId
        );
    }
}
