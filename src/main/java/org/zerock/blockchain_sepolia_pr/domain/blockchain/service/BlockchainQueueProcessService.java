package org.zerock.blockchain_sepolia_pr.domain.blockchain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import org.zerock.blockchain_sepolia_pr.domain.blockchain.entity.BlockchainQueue;
import org.zerock.blockchain_sepolia_pr.domain.blockchain.entity.BlockchainQueueStatus;
import org.zerock.blockchain_sepolia_pr.domain.blockchain.entity.BlockchainTx;
import org.zerock.blockchain_sepolia_pr.domain.blockchain.repository.BlockchainQueueRepository;
import org.zerock.blockchain_sepolia_pr.domain.blockchain.repository.BlockchainTxRepository;
import org.zerock.blockchain_sepolia_pr.domain.common.config.BlockchainProperties;
import org.zerock.blockchain_sepolia_pr.domain.trade.dto.TradeQueuePayload;
import org.zerock.blockchain_sepolia_pr.domain.trade.entity.Trade;
import org.zerock.blockchain_sepolia_pr.domain.trade.repository.TradeRepository;


@Service
public class BlockchainQueueProcessService {

    private final BlockchainQueueRepository blockchainQueueRepository;
    private final BlockchainTxRepository blockchainTxRepository;
    private final TradeRepository tradeRepository;
    private final BlockchainRecordService blockchainRecordService;
    private final ObjectMapper objectMapper;
    private final BlockchainProperties properties;

    public BlockchainQueueProcessService(BlockchainQueueRepository blockchainQueueRepository, BlockchainTxRepository blockchainTxRepository, TradeRepository tradeRepository, BlockchainRecordService blockchainRecordService, ObjectMapper objectMapper, BlockchainProperties properties) {
        this.blockchainQueueRepository = blockchainQueueRepository;
        this.blockchainTxRepository = blockchainTxRepository;
        this.tradeRepository = tradeRepository;
        this.blockchainRecordService = blockchainRecordService;
        this.objectMapper = objectMapper;
        this.properties = properties;
    }

    @Transactional
    public Long processNext() {
        BlockchainQueue queue = blockchainQueueRepository
                .findTop1ByStatusOrderByQueueIdAsc(BlockchainQueueStatus.PENDING)
                .orElseThrow(() -> new IllegalStateException("처리할 PENDING queue가 없습니다."));

        Trade trade = tradeRepository.findById(queue.getTradeId())
                .orElseThrow(() -> new IllegalStateException("trade를 찾을 수 없습니다. id= " + queue.getTradeId()));

        try {
            queue.markProcessing();
            trade.markOnChainProcessing();

            TradeQueuePayload payload = objectMapper.readValue(queue.getPayloadJson(), TradeQueuePayload.class);

            BlockchainRecordService.BlockchainRecordResult result =
                    blockchainRecordService.recordTrade(payload);

            BlockchainTx blockchainTx = BlockchainTx.success(
                    queue.getQueueId(),
                    trade.getTradeId(),
                    result.getTxHash(),
                    result.getBlockNumber(),
                    properties.getContractAddress(),
                    result.getGasUsed()
            );

            blockchainTxRepository.save(blockchainTx);

            queue.markSuccess();
            trade.markOnChainRecorded();

            return blockchainTx.getBlockchainTxId();

        } catch (Exception e) {

            queue.markFail(e.getMessage());
            trade.markOnChainFail();
            throw new IllegalStateException("queue 처리 실패", e);

        }
    }
}
