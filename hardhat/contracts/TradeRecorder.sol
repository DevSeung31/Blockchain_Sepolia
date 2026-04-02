// SPDX-License-Identifier: MIT
pragma solidity ^0.8.28;

contract TradeRecorder {
    address public owner;

    struct TradeRecord {
        uint256 tradeId;
        address buyer;
        address seller;
        uint256 tokenId;
        uint256 price;
        uint256 quantity;
        uint256 recordedAt;
    }

    mapping(uint256 => TradeRecord) public trades;

    event TradeRecorded(
        uint256 indexed tradeId,
        address indexed buyer,
        address indexed seller,
        uint256 tokenId,
        uint256 price,
        uint256 quantity,
        uint256 recordedAt
    );

    modifier onlyOwner() {
        require(msg.sender == owner, "only owner");
        _;
    }

    constructor() {
        owner = msg.sender;
    }

    function recordTrade(
        uint256 tradeId,
        address buyer,
        address seller,
        uint256 tokenId,
        uint256 price,
        uint256 quantity
    ) external onlyOwner {
        trades[tradeId] = TradeRecord({
            tradeId: tradeId,
            buyer: buyer,
            seller: seller,
            tokenId: tokenId,
            price: price,
            quantity: quantity,
            recordedAt: block.timestamp
        });

        emit TradeRecorded(
            tradeId,
            buyer,
            seller,
            tokenId,
            price,
            quantity,
            block.timestamp
        );
    }
}