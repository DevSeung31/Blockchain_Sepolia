package org.zerock.blockchain_sepolia_pr.domain.trade.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.blockchain_sepolia_pr.domain.trade.entity.Trade;
import org.zerock.blockchain_sepolia_pr.domain.trade.repository.TradeRepository;

@RestController
@RequestMapping("/api/trades")
public class TradeQueryController {

    private final TradeRepository tradeRepository;


    public TradeQueryController(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    @GetMapping("/{tradeId}")
    public Trade getTrade(@PathVariable Long tradeId) {
        return tradeRepository.findById(tradeId)
                .orElseThrow(() -> new IllegalStateException("trade 없음"));
    }
}
