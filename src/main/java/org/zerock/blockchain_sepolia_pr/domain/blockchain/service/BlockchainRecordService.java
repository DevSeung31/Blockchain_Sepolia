package org.zerock.blockchain_sepolia_pr.domain.blockchain.service;

import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;
import org.zerock.blockchain_sepolia_pr.domain.common.config.BlockchainProperties;
import org.zerock.blockchain_sepolia_pr.domain.trade.dto.TradeQueuePayload;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;


@Service
public class BlockchainRecordService {

    private final Web3j web3j;
    private final Credentials credentials;
    private final BlockchainProperties properties;


    public BlockchainRecordService(Web3j web3j, Credentials credentials, BlockchainProperties properties) {
        this.web3j = web3j;
        this.credentials = credentials;
        this.properties = properties;
    }



    public BlockchainRecordResult recordTrade(TradeQueuePayload payload) {
        try {
            Function function = new Function(
                    "recordTrade",
                    List.of(
                            new Uint256(BigInteger.valueOf(payload.getTradeId())),
                            new Address(payload.getBuyerWalletAddress()),
                            new Address(payload.getSellerWalletAddress()),
                            new Uint256(BigInteger.valueOf(payload.getTokenId())),
                            new Uint256(BigInteger.valueOf(payload.getPrice())),
                            new Uint256(BigInteger.valueOf(payload.getQuantity()))
                    ),
                    Collections.emptyList()
            );

            String encodedFunction = FunctionEncoder.encode(function);

            EthGasPrice ethGasPrice = web3j.ethGasPrice().send();
            BigInteger gasPrice = ethGasPrice.getGasPrice();

            RawTransactionManager txManager = new RawTransactionManager(web3j, credentials);

            EthSendTransaction ethSendTransaction = txManager.sendTransaction(
                    gasPrice,
                    BigInteger.valueOf(properties.getGasLimit()),
                    properties.getContractAddress(),
                    encodedFunction,
                    BigInteger.ZERO
            );

            if (ethSendTransaction.hasError()) {
                throw new IllegalStateException("tx 전송 실패: " + ethSendTransaction.getError());
            }

            String txHash = ethSendTransaction.getTransactionHash();

            PollingTransactionReceiptProcessor receiptProcessor =
                    new PollingTransactionReceiptProcessor(web3j, 3000, 20);

            TransactionReceipt receipt = receiptProcessor.waitForTransactionReceipt(txHash);

            if (!receipt.isStatusOK()) {
                throw new IllegalStateException("tx receopt status 실패, txHash=" + txHash);
            }

            return new BlockchainRecordResult(
                    txHash,
                    receipt.getBlockNumber().longValue(),
                    receipt.getGasUsed().longValue()
            );

        } catch (Exception e) {
            throw new IllegalStateException("블록체인 기록 실패", e);
        }
        }
    public static class BlockchainRecordResult {
        private final String txHash;
        private final Long blockNumber;
        private final Long gasUsed;
        public BlockchainRecordResult(String txHash, Long blockNumber, Long gasUsed) {
            this.txHash = txHash;
            this.blockNumber = blockNumber;
            this.gasUsed = gasUsed;
        }

        public String getTxHash() {
            return txHash;
        }

        public Long getBlockNumber() {
            return blockNumber;
        }

        public Long getGasUsed() {
            return gasUsed;
        }
    }
}
