import java.lang.reflect.Array;
import java.util.*;
import java.io.*;

public class Apriori2 {
public static ArrayList<ArrayList<String>> transactions;
public static Integer num_trans =0;
public static Integer minSup; //baadan taghir bede!!!!!!!!

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(System.in);
        HashMap<String , HashSet<String>> items =new HashMap<String, HashSet<String>>();
        HashMap<String , HashSet<String>> finalItem =new HashMap<String, HashSet<String>>();
        String address=scan.next();
        minSup=scan.nextInt();
        readFile(address);
        System.out.println(transactions.size());
        items=itemsList1(transactions);
        finalItem=items;
        int i=1;

        System.out.println(items);
        System.out.println(transactions);
    }//main

    // list of 1-items
    private static HashMap<String , HashSet<String>> itemsList1 (ArrayList<ArrayList<String>> input) {
        HashMap<String, HashSet<String>> itemList= new HashMap<String, HashSet<String>>();
        HashSet<String> itemSet;
        int x=0;
        for (ArrayList<String> transaction : input) {
            for (int i=1; i<transaction.size();i++) {
                itemSet=new HashSet<String>();
                if (itemList.containsKey(transaction.get(i)))
                { itemSet.add(transaction.get(0));
                    (itemList.get(transaction.get(i))).add(String.valueOf(itemSet));
                }
                else
                    {
                        itemSet.add(transaction.get(0));
                        itemList.put(transaction.get(i),itemSet);
                    }//else

            }//inner for
            x++;
        }//outer for

        for (String item: itemList.keySet()) {
            if (itemList.get(item).size() < minSup)
                itemList.remove(item);
        }
        return itemList;
    }

    private static void readFile(String st)  throws FileNotFoundException {
        transactions = new ArrayList<ArrayList<String>>();
        ArrayList<String> transaction;
        File file = new File(st);
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            transaction = new ArrayList<String>();
            String str = sc.nextLine();
            String [] arr = str.split(" ");
            for (int j = 0; j < arr.length; j++)
                transaction.add(arr[j]);

            transactions.add(transaction);
        }//while
        //num_trans =Integer.parseInt(transactions.get(1).get(1));

    }

}/* class */



