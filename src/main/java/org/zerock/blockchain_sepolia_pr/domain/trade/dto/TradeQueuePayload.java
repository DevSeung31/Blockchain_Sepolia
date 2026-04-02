package org.zerock.blockchain_sepolia_pr.domain.trade.dto;

public class TradeQueuePayload {

    private Long tradeId;
    private String buyerWalletAddress;
    private String sellerWalletAddress;
    private Long tokenId;
    private Long price;
    private Long quantity;

    public TradeQueuePayload() {
    }

    public TradeQueuePayload(
            Long tradeId,
            String buyerWalletAddress,
            String sellerWalletAddress,
            Long tokenId,
            Long price,
            Long quantity
    ) {
        this.tradeId = tradeId;
        this.buyerWalletAddress = buyerWalletAddress;
        this.sellerWalletAddress = sellerWalletAddress;
        this.tokenId = tokenId;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getTradeId() {
        return tradeId;
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

}
