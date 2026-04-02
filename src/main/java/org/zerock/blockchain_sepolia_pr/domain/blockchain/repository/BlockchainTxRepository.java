package org.zerock.blockchain_sepolia_pr.domain.blockchain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.blockchain_sepolia_pr.domain.blockchain.entity.BlockchainTx;

import java.util.Optional;

public interface BlockchainTxRepository extends JpaRepository<BlockchainTx, Long> {
    Optional<BlockchainTx> findTop1ByTradeIdOrderByBlockchainTxIdDesc(Long tradeId);
}
