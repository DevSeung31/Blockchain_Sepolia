package org.zerock.blockchain_sepolia_pr.domain.trade.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TradeCreateRequest {

    @NotNull
    private Long buyerMemberId;

    @NotNull
    private Long sellerMemberId;

    @NotBlank
    private String buyerWalletAddress;

    @NotBlank
    private String sellerWalletAddress;

    @NotNull
    private Long tokenId;

    @NotNull
    private Long price;

    @NotNull
    private Long quantity;

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

}
