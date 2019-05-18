package leagueMapReduce.inputCreator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static class PairObj {
        public String first;
        public String second;

        public PairObj(String fst, String snd) {
            first = fst;
            second = snd;
        }

        public String getFirst() {
            return first;
        }

        public String getSecond() {
            return second;
        }
    }

    public static boolean isStringInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static String TurkishToEnglish(String kelime) {
        String mesaj = kelime;
        char[] oldValue = new char[]{'ö', 'Ö', 'ü', 'Ü', 'ç', 'Ç', 'İ', 'ı', 'Ğ', 'ğ', 'Ş', 'ş'};
        char[] newValue = new char[]{'o', 'O', 'u', 'U', 'c', 'C', 'I', 'i', 'G', 'g', 'S', 's'};
        for (int sayac = 0; sayac < oldValue.length; sayac++) {
            mesaj = mesaj.replace(oldValue[sayac], newValue[sayac]);
        }
        return mesaj;
    }

    public static int calculateResult(String kelime) {
        char f = kelime.charAt(0);
        char l = kelime.charAt(kelime.length() - 1);

        if (Integer.parseInt(String.valueOf(f)) > Integer.parseInt(String.valueOf(l))) {
            return 1;
        } else if (Integer.parseInt(String.valueOf(f)) < Integer.parseInt(String.valueOf(l))) {
            return 2;
        } else {
            return 0;
        }
    }

    public static void main(String[] args) {
        Document html = null;
        String oldTeamName = "empty";
        List<PairObj> pairList = new ArrayList<>();
        System.out.println("Welcome to MapReduce input creator. This MapReduce used for calculating the league data for Turkish Football.");
        System.out.println("This input creator gets the results from TFF website (from archieve) that has all results in 1 page");
        System.out.println("Enter the URL from TFF webpage archieve: ");
        Scanner scanner = new Scanner(System.in);
        String TFFUrl = scanner.nextLine();
        System.out.println("Some times there could be name changes in teams. So inorder to avoid them please indicate. Old name for the team will be used.");
        System.out.println("After you enter 'OK'  for oldTeamName the program begins.");
        System.out.println("Inputs will be created in Inputs.txt file");
        try {
            while ("OK".compareTo(oldTeamName) != 0) {
                System.out.println("Old name: ");
                oldTeamName = scanner.next();
                if ("OK".compareTo(oldTeamName) == 0) {
                    break;
                }
                System.out.println("New name: ");
                String newTeamName = scanner.next();
                PairObj pair = new PairObj(oldTeamName, newTeamName);
                pairList.add(pair);
            }
            for (PairObj p : pairList) {
                System.out.println(p.getFirst() + " - " + p.getSecond());
            }
            TFFUrl = TFFUrl.replace(" ", "");
            html = Jsoup.connect(TFFUrl).get();
            Elements table = html.select(".genelBorder > tbody > tr > td > table");
            PrintWriter writer = new PrintWriter("matchResults.txt", "UTF-8");
            for (Element element : table) {
                writer.println(element.select(".altCizgi > a").html());

            }
            writer.close();
            FileInputStream fstream = new FileInputStream("matchResults.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine1, strLine2, strLine3;
            int result = 0;
            PrintWriter writer1 = new PrintWriter("Inputs.txt", "UTF-8");
            while ((strLine1 = br.readLine()) != null &&
                    (strLine2 = br.readLine()) != null &&
                    (strLine3 = br.readLine()) != null) {
                strLine1 = TurkishToEnglish(strLine1);
                strLine1 = strLine1.split(" ")[0];
                strLine3 = TurkishToEnglish(strLine3);
                strLine3 = strLine3.split(" ")[0];
                for (int i = 0; i < pairList.size(); i++) {
                    if (strLine1.compareTo(pairList.get(i).getSecond()) == 0) {
                        strLine1 = pairList.get(i).getFirst();
                    }
                    if (strLine3.compareTo(pairList.get(i).getSecond()) == 0) {
                        strLine3 = pairList.get(i).getFirst();
                    }
                }
                result = calculateResult(strLine2);
                writer1.println(strLine1 + " " + strLine3 + " " + result);
            }
            fstream.close();
            writer1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
