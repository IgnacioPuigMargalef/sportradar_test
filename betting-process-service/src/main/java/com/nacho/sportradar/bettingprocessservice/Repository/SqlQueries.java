package com.nacho.sportradar.bettingprocessservice.Repository;

public class SqlQueries {

    public final static String CREATE_BET = "INSERT INTO bets (id, amount, odds, client, event, market, selection, status, betting_result, review_check) " +
            "VALUES (:id, :amount, :odds, :client, :event, :market, :selection, :status, :bettingResult, :reviewCheck)";

    public final static String GET_TOTAL_AMOUNT = "select SUM(AMOUNT) \n" +
            "from public.bets b \n" +
            "where b.review_check like 'NO' and b.status not like 'VOID'";

    public final static String GET_TOTAL_RESULTS = "select SUM(cast(betting_result as numeric)) \n" +
            "from public.bets b \n" +
            "where b.review_check like 'NO' and b.status not like 'VOID'";

    public static String GET_TOP_5_PROFIT_CLIENTS = "select client, sum(cast(betting_result as numeric)) as totalProfit \n" +
            "from bets b \n" +
            "where b.review_check = 'NO' \n" +
            "  and b.status != 'VOID' \n" +
            "group by client \n" +
            "order by totalProfit desc \n" +
            "limit 5";

    public static String GET_TOP_5_LOSSES_CLIENTS = "select client, sum(cast(betting_result as numeric)) as totalProfit\n" +
            "from bets b\n" +
            "where b.review_check = 'NO' \n" +
            "  and b.status != 'VOID'\n" +
            "group by client\n" +
            "order by totalProfit asc\n" +
            "limit 5";

}
