package com.zheng.poker.texas.controller;


import com.zheng.poker.texas.model.*;
import com.zheng.poker.texas.model.cards.Card;
import com.zheng.poker.texas.model.gameproperties.GameProperties;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class GameHandController {
    private final HandPowerRanker handPowerRanker;
    private final GameProperties gameProperties;

    public GameHandController(
                              final HandPowerRanker handPowerRanker,
                              final GameProperties gameProperties) {
        this.handPowerRanker = handPowerRanker;
        this.gameProperties = gameProperties;
    }

    public void play(Game game) {
        GameHand gameHand = createGameHand(game);
        gameHand.sendSeatInfoMsg();
        Boolean haveWinner = false;
        while (!gameHand.getBettingRoundName().equals(
                BettingRoundName.POST_RIVER)
                && !haveWinner) {
            haveWinner = playRound(gameHand);
        }

        if (!haveWinner) {
            System.out.println("game#"+game.gameHandsCount());
            showDown(gameHand);
        }
    }

    private GameHand createGameHand(Game game) {
        GameHand gameHand = new GameHand(game.getPlayers());
        game.addGameHand(gameHand);
        return gameHand;
    }

    protected Boolean playRound(GameHand gameHand) {
        gameHand.nextRound();
        int toPlay = gameHand.getPlayersCount();
        if (gameHand.getBettingRoundName().equals(BettingRoundName.PRE_FLOP)) {
            takeBlinds(gameHand);
            toPlay--; // Big blinds don't have to call on himself if no raise :)
        }

        int turn = 1;
        int numberOfPlayersAtBeginningOfRound = gameHand.getPlayersCount();
        while (toPlay > 0) {
            Player player = gameHand.getNextPlayer();
            BettingMoney bettingMoney=player.decide(gameHand);
            System.out.println(player.toString()+" "+bettingMoney.getDecision());

//            // We can't raise at second turn
//            if (turn > numberOfPlayersAtBeginningOfRound
//                    && bettingDecision.equals(BettingDecision.RAISE)) {
//                bettingDecision = BettingDecision.CALL;
//            }

            // After a raise, every active players after the raiser must play
            if (bettingMoney.getDecision().equals(BettingDecision.RAISE) || bettingMoney.getDecision().equals(BettingDecision.ALL_IN)) {
                toPlay = gameHand.getPlayersCount() ;
            }

            applyDecision(gameHand, player, bettingMoney);
            turn++;
            toPlay--;
        }

        // Check if we have a winner
        if (gameHand.getPlayersCount() == 1) {
            Player winner = gameHand.getCurrentPlayer();
            winner.addJetton(gameHand.getTotalBets());
            return true;
        }
        return false;
    }

    private void takeBlinds(GameHand gameHand) {
        Player smallBlindPlayer = gameHand.getNextPlayer();
        Player bigBlindPlayer = gameHand.getNextPlayer();


        gameHand.getCurrentBettingRound().placeBet(smallBlindPlayer,
                gameProperties.getSmallBlind(),"blind");
        gameHand.getCurrentBettingRound().placeBet(bigBlindPlayer,
                gameProperties.getBigBlind(),"blind");
        StringBuilder msg=new StringBuilder();
        msg.append("blind/\n");
        msg.append(smallBlindPlayer.getId()+":"+gameProperties.getSmallBlind()+"\n");
        msg.append(bigBlindPlayer.getId()+":"+gameProperties.getBigBlind()+"\n");
        msg.append("/blind\n");
        for(Player player:gameHand.getPlayers())
            player.sendMsg(msg.toString());

    }

    private void applyDecision(GameHand gameHand, Player player, BettingMoney bettingMoney) {
//        double handStrength = handStrengthEvaluator.evaluate(player.getHoleCards(), gameHand.getSharedCards(),
//                gameHand.getPlayersCount());
       gameHand.applyDecision(player, bettingMoney);
//
//        BettingRound bettingRound = gameHand.getCurrentBettingRound();
//        logger.log(player + ": " + bettingDecision + " "
//                + bettingRound.getBetForPlayer(player) + "$");
    }

    private List<Player> getWinners(GameHand gameHand,List<Player> players) {
        Deque<Player> activePlayers = gameHand.getPlayers();
        activePlayers.retainAll(players);
        List<Card> sharedCards = gameHand.getSharedCards();

        HandPower bestHandPower = null;
        List<Player> winners = new ArrayList<Player>();
        for (Player player : activePlayers) {
            List<Card> mergeCards = new ArrayList<Card>(player.getHoleCards());
            mergeCards.addAll(sharedCards);
            HandPower handPower = handPowerRanker.rank(mergeCards);

            //logger.log(player + ": " + handPower);

            if (bestHandPower == null || handPower.compareTo(bestHandPower) > 0) {
                winners.clear();
                winners.add(player);
                bestHandPower = handPower;
            } else if (handPower.equals(bestHandPower)) {
                winners.add(player);
            }
        }
       // statisticsController.storeWinners(winners);
        return winners;
    }

    protected void showDown(GameHand gameHand) {
       // logger.log("--- Showdown");
        List<Pot> pots=Pot.getAllPot(gameHand.getAllPlayerBet());
        System.out.println(pots);
        for(Pot pot:pots){
            List<Player> winners=getWinners(gameHand,pot.getPlayers());
            for(Player winner:winners){
                winner.addJetton(pot.getMoney()/winners.size());
            }
        }
        // Showdown
//        List<Player> winners = getWinners(gameHand);
//
//        // Gains
//        int gain = gameHand.getTotalBets() / winners.size();
//        int modulo = gameHand.getTotalBets() % winners.size();
//        for (Player winner : winners) {
//            int gainAndModulo = gain;
//            if (modulo > 0) {
//                gainAndModulo += modulo;
//            }
//            winner.addJetton(gainAndModulo);
//            System.out.println("WINNER: " + winner + ": WIN! +" + gainAndModulo + "$");
//
//            modulo--;
//        }

        // Opponent modeling
        //opponentModeler.save(gameHand);
    }
}
