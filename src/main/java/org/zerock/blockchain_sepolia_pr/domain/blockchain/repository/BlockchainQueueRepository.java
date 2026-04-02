package org.zerock.blockchain_sepolia_pr.domain.blockchain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.blockchain_sepolia_pr.domain.blockchain.entity.BlockchainQueue;
import org.zerock.blockchain_sepolia_pr.domain.blockchain.entity.BlockchainQueueStatus;

import java.util.Optional;

public interface BlockchainQueueRepository extends JpaRepository<BlockchainQueue, Long> {
    Optional<BlockchainQueue> findTop1ByStatusOrderByQueueIdAsc(BlockchainQueueStatus status);
}
