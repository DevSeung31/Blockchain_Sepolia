package org.zerock.blockchain_sepolia_pr.domain.trade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.blockchain_sepolia_pr.domain.trade.entity.Trade;

public interface TradeRepository extends JpaRepository<Trade, Long> {
}
