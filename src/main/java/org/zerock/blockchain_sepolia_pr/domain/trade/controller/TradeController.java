package org.zerock.blockchain_sepolia_pr.domain.trade.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.blockchain_sepolia_pr.domain.trade.dto.TradeCreateRequest;
import org.zerock.blockchain_sepolia_pr.domain.trade.service.TradeCreateService;

import java.util.Map;

@RestController
@RequestMapping("/api/trades")
public class TradeController {

    private final TradeCreateService tradeCreateService;


    public TradeController(TradeCreateService tradeCreateService) {
        this.tradeCreateService = tradeCreateService;
    }

    @PostMapping("/test")
    public Map<String, Object> createTestTrade (@Valid @RequestBody TradeCreateRequest request) {
        Long tradeId = tradeCreateService.create(request);
        return Map.of (
                "message", "trade + queue 생성 완료",
                "tradeId", tradeId
        );

    }
}
