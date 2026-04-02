package org.zerock.blockchain_sepolia_pr.domain.trade.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "trade")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tradeId;

    @Column(nullable = false)
    private Long buyerMemberId;

    @Column(nullable = false)
    private Long sellerMemberId;

    @Column(nullable = false, length = 42)
    private String buyerWalletAddress;

    @Column(nullable = false, length = 42)
    private String sellerWalletAddress;

    @Column(nullable = false)
    private Long tokenId;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false)
    private Long tradeAmount;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "trade_status", nullable = false, columnDefinition = "trade_status")
    private TradeStatus tradeStatus;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    protected Trade() {
    }

    public static Trade create(
            Long buyerMemberId,
            Long sellerMemberId,
            String buyerWalletAddress,
            String sellerWalletAddress,
            Long tokenId,
            Long price,
            Long quantity
    ) {
        Trade trade = new Trade();
        trade.buyerMemberId = buyerMemberId;
        trade.sellerMemberId = sellerMemberId;
        trade.buyerWalletAddress = buyerWalletAddress;
        trade.sellerWalletAddress = sellerWalletAddress;
        trade.tokenId = tokenId;
        trade.price = price;
        trade.quantity = quantity;
        trade.tradeAmount = price * quantity;
        trade.tradeStatus = TradeStatus.CREATED;
        trade.createdAt = LocalDateTime.now();
        trade.updatedAt = LocalDateTime.now();
        return trade;
    }

    public void markOnChainProcessing() {
        this.tradeStatus = TradeStatus.ONCHAIN_PROCESSING;
        this.updatedAt = LocalDateTime.now();
    }

    public void markOnChainRecorded() {
        this.tradeStatus = TradeStatus.ONCHAIN_RECORDED;
        this.updatedAt = LocalDateTime.now();
    }

    public void markOnChainFail() {
        this.tradeStatus = TradeStatus.ONCHAIN_FAIL;
        this.updatedAt = LocalDateTime.now();
    }

    public Long getTradeId() {
        return tradeId;
    }

    public Long getBuyerMemberId() {
        return buyerMemberId;
    }

    public Long getSellerMemberId() {
        return sellerMemberId;
    }

    public String getBuyerWalletAddress() {
        return buyerWalletAddress;
    }

    public String getSellerWalletAddress() {
        return sellerWalletAddress;
    }

    public Long getTokenId() {
        return tokenId;
    }

    public Long getPrice() {
        return price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public Long getTradeAmount() {
        return tradeAmount;
    }

    public TradeStatus getTradeStatus() {
        return tradeStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

}
