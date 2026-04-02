package org.zerock.blockchain_sepolia_pr.domain.blockchain.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "blockchain_queue")
public class BlockchainQueue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long queueId;

    @Column(nullable = false)
    private Long tradeId;

    @Column(nullable = false, length = 50)
    private String eventType;

    @Lob
    @Column(nullable = false)
    private String payloadJson;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false, columnDefinition = "blockchain_queue_status")
    private BlockchainQueueStatus status;

    @Column(nullable = false)
    private Integer retryCount;

    @Lob
    private String lastErrorMessage;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    protected BlockchainQueue() {
    }

    public static BlockchainQueue create(Long tradeId, String eventType, String payloadJson) {
        BlockchainQueue queue = new BlockchainQueue();
        queue.tradeId = tradeId;
        queue.eventType = eventType;
        queue.payloadJson = payloadJson;
        queue.status = BlockchainQueueStatus.PENDING;
        queue.retryCount = 0;
        queue.createdAt = LocalDateTime.now();
        queue.updatedAt = LocalDateTime.now();
        return queue;
    }

    public void markProcessing() {
        this.status = BlockchainQueueStatus.PROCESSING;
        this.updatedAt = LocalDateTime.now();
    }

    public void markSuccess() {
        this.status = BlockchainQueueStatus.SUCCESS;
        this.updatedAt = LocalDateTime.now();
    }

    public void markFail(String errorMessage) {
        this.status = BlockchainQueueStatus.FAIL;
        this.retryCount += 1;
        this.lastErrorMessage = errorMessage;
        this.updatedAt = LocalDateTime.now();
    }

    public Long getQueueId() {
        return queueId;
    }

    public Long getTradeId() {
        return tradeId;
    }

    public String getEventType() {
        return eventType;
    }

    public String getPayloadJson() {
        return payloadJson;
    }

    public BlockchainQueueStatus getStatus() {
        return status;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public String getLastErrorMessage() {
        return lastErrorMessage;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
