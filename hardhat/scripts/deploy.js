async function main() {
    const TradeRecorder = await ethers.getContractFactory("TradeRecorder");
    const tradeRecorder = await TradeRecorder.deploy();

    await tradeRecorder.waitForDeployment();

    console.log("TradeRecorder deployed to:", await tradeRecorder.getAddress());
}

main().catch((error) => {
    console.error(error);
    process.exitCode = 1;
});