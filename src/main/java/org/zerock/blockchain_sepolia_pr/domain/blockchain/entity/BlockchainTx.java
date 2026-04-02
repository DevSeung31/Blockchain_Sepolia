package org.zerock.blockchain_sepolia_pr.domain.blockchain.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "blockchain_tx")
public class BlockchainTx {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blockchainTxId;

    @Column(nullable = false)
    private Long queueId;
    @Column(nullable = false)
    private Long tradeId;
    @Column(nullable = false, length = 100)
    private String txHash;

    private Long blockNumber;

    @Column(nullable = false, length = 100)
    private String contractAddress;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false, columnDefinition = "blockchain_tx_status")
    private BlockchainTxStatus txStatus;

    private Long gasUsed;

    private LocalDateTime recordedAt;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    protected BlockchainTx() {
    }

    public static BlockchainTx success(
            Long queueId,
            Long tradeId,
            String txHash,
            Long blockNumber,
            String contractAddress,
            Long gasUsed
    ) {
      BlockchainTx tx = new BlockchainTx();
      tx.queueId = queueId;
      tx.tradeId = tradeId;
      tx.txHash = txHash;
      tx.blockNumber = blockNumber;
      tx.contractAddress = contractAddress;
      tx.txStatus = BlockchainTxStatus.SUCCESS;
      tx.gasUsed = gasUsed;
      tx.recordedAt = LocalDateTime.now();
      tx.createdAt = LocalDateTime.now();
      return tx;
    }

    public static BlockchainTx fail(
            Long queueId,
            Long tradeId,
            String txHash,
            String contractAddress
    ) {
        BlockchainTx tx = new BlockchainTx();
        tx.queueId = queueId;
        tx.tradeId = tradeId;
        tx.txHash = txHash;
        tx.contractAddress = contractAddress;
        tx.txStatus = BlockchainTxStatus.FAIL;
        tx.createdAt = LocalDateTime.now();
        return tx;
    }

    public Long getBlockchainTxId() {
        return blockchainTxId;
    }

    public Long getQueueId() {
        return queueId;
    }

    public Long getTradeId() {
        return tradeId;
    }

    public String getTxHash() {
        return txHash;
    }

    public Long getBlockNumber() {
        return blockNumber;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public BlockchainTxStatus getTxStatus() {
        return txStatus;
    }

    public Long getGasUsed() {
        return gasUsed;
    }

    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
