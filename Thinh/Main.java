public class Main {
    public static void main(String[] args) {
        // một ý tưởng để phát triển là tận dụng đa luồng trong Java
        TwitterBot bot = new TwitterBot();
        TwitterLogin login = new TwitterLogin(bot);
        TwitterDataCollector collector = new TwitterDataCollector(bot);
//        TwitterD
        String username = "caohuythinh@gmail.com";
        String user = "ThnhCaoHuy";
        String password = "mancity1st";

        // Đăng nhập vào Twitter
        login.login(username, user, password);

        TwitterBot bot1 = new TwitterBot();
        TwitterLogin login1 = new TwitterLogin(bot1);
        TwitterDataCollector collector1 = new TwitterDataCollector(bot);
//        TwitterD

        // Đăng nhập vào Twitter
        login1.login(username, user, password);

        // Tìm kiếm hashtag #blockchain
//        collector.searchHashtag("blockchain");

        // Đóng trình duyệt sau khi hoàn tất
        // bot.close();
        TwitterSimulation botSim = new TwitterSimulation(bot.getDriver(), bot.getWait());
//        botSim.searchUser("messi");
//        botSim.clickOnProfile(1);
//        botSim.getFollowersCount();

//        KOL user = new KOL("KevinDeBruyne");
//        Tweet obj = new Tweet("1835019601437945976", "KevinDeBruyne");
//        botSim.searchTweet(obj);
    }
}