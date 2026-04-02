package org.zerock.blockchain_sepolia_pr.domain.trade.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.zerock.blockchain_sepolia_pr.domain.blockchain.entity.BlockchainQueue;
import org.zerock.blockchain_sepolia_pr.domain.blockchain.repository.BlockchainQueueRepository;
import org.zerock.blockchain_sepolia_pr.domain.trade.dto.TradeCreateRequest;
import org.zerock.blockchain_sepolia_pr.domain.trade.dto.TradeQueuePayload;
import org.zerock.blockchain_sepolia_pr.domain.trade.entity.Trade;
import org.zerock.blockchain_sepolia_pr.domain.trade.repository.TradeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TradeCreateService {

    private final TradeRepository tradeRepository;
    private final BlockchainQueueRepository blockchainQueueRepository;
    private final ObjectMapper objectMapper;

    public TradeCreateService (
            TradeRepository tradeRepository,
            BlockchainQueueRepository blockchainQueueRepository,
            ObjectMapper objectMapper
    ) {
        this.tradeRepository = tradeRepository;
        this.blockchainQueueRepository = blockchainQueueRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public Long create(TradeCreateRequest request) {
        Trade trade = Trade.create(
                request.getBuyerMemberId(),
                request.getSellerMemberId(),
                request.getBuyerWalletAddress(),
                request.getSellerWalletAddress(),
                request.getTokenId(),
                request.getPrice(),
                request.getQuantity()
        );

        Trade savedTrade = tradeRepository.save(trade);

        TradeQueuePayload payload = new TradeQueuePayload(
                savedTrade.getTradeId(),
                savedTrade.getBuyerWalletAddress(),
                savedTrade.getSellerWalletAddress(),
                savedTrade.getTokenId(),
                savedTrade.getPrice(),
                savedTrade.getQuantity()
        );

        String payloadJson = toJson(payload);

        BlockchainQueue queue = BlockchainQueue.create (
                savedTrade.getTradeId(),
                "TRADE_SETTLEMENT",
                payloadJson
        );

        blockchainQueueRepository.save(queue);

        return savedTrade.getTradeId();
    }

    private String toJson(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("payload json 생성실패", e);
        }
    }
}
