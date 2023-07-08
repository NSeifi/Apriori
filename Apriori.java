import java.awt.*;
import java.lang.reflect.Array;
import java.util.*;
import java.io.*;
import java.lang.*;

public class Apriori
{
    public static Integer tNum;
    public static Integer minSupport;
    public static String address;

    public static void main(String [] args) throws FileNotFoundException {
        Scanner sc =new Scanner(System.in);
        address=sc.nextLine();
        minSupport=sc.nextInt();
        ArrayList<ArrayList<String>> itemList1=new ArrayList<ArrayList<String>>();
        itemList1= readFile();
        itemList1.remove(0);
        itemList1=minSup(itemList1);
        System.out.println(itemList1 +" item main");
        ArrayList<ArrayList<String>> ck = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> lk = new ArrayList<ArrayList<String>>();
        int k=1;
        while(true)
        {
            k++;
            if (k==2)
            {
                ck = geneItemSet(itemList1,k);
                ck = countItemSet(ck,k);
                if(!ck.isEmpty())
                    lk=ck;
                else
                {
                    System.out.println(" The largest frequent itemset is: "+itemList1);
                    break;
                };
            }//if
            else
                {
                    ck = geneItemSet(lk,k);
                    //ck = pruneItemSet(ck,lk);
                    ck = countItemSet(ck,k);
                    if(!ck.isEmpty())
                        lk=ck;
                    else
                    {
                        System.out.println(" The largest frequent itemset is: "+lk);
                        break;
                    };
                }//else
         }

    }//main

    private static ArrayList<ArrayList<String>> readFile()  throws FileNotFoundException {
       ArrayList<ArrayList<String>> itemSet = new ArrayList<ArrayList<String>>();
        ArrayList<String> transaction;
        File file = new File(address);
        Scanner sc = new Scanner(file);
        int flag=0;
        while (sc.hasNextLine()) {
            transaction = new ArrayList<String>();
            String str = sc.nextLine();
            String[] arr = str.split(" ");
           if (itemSet.isEmpty()) {
                transaction.add(arr[0]);
                itemSet.add(transaction);
            }//if
            else{
                for (int j = 2; j < arr.length; j++) {
                    flag=0;
                    transaction = new ArrayList<String>();
                    for (int i = 1; i < itemSet.size(); i++)
                    {
                        if (itemSet.get(i).get(0).equals((arr[j]))) {
                            transaction.add(itemSet.get(i).get(0));
                            transaction.add((Integer.parseInt(itemSet.get(i).get(1)) + 1)+"");
                            itemSet.set(i, transaction);
                            flag = 1;
                        }
                    }
                    if(flag==0) {
                        transaction.add(arr[j]);
                        transaction.add("1");
                        itemSet.add(transaction);
                        flag=0;
                    }
                }//for
             }//else
        }//while
        return itemSet;
    }//readFile

    private static ArrayList<ArrayList<String>> minSup(ArrayList<ArrayList<String>> itemSet)
    {
        ArrayList<ArrayList<String>> itemR= new ArrayList<ArrayList<String>>();
        ArrayList<String> item = new ArrayList<String>();
        for(int i=0; i< itemSet.size();i++) {
            item =new ArrayList<String>();
            if (Integer.parseInt(itemSet.get(i).get(1)) >= minSupport)
            {
            item.add(itemSet.get(i).get(0));
            item.add(itemSet.get(i).get(1));
            itemR.add(item);
            }
        }
        return itemR;
    }//minSup

    private static ArrayList<ArrayList<String>> geneItemSet(ArrayList<ArrayList<String>> itemList1, int k)
    {
        ArrayList<ArrayList<String>> cList = new ArrayList<ArrayList<String>>();
        ArrayList<String> items =new ArrayList<String>();

            for(int i=0; i<itemList1.size();i++) {
                for (int j=i+1; j<itemList1.size();j++)
                {
                    items = new ArrayList<String>();
                    Set<String> st1= new HashSet<String>(Arrays.asList(itemList1.get(i).get(0).split(",")));
                    Set<String> st2= new HashSet<String>(Arrays.asList(itemList1.get(j).get(0).split(",")));
                    st1.addAll(st2);

                    if (st1.size()==k)
                    {
                        String joined = String.join(",", st1);
                        items.add(joined);
                        items.add("0");
                        if (!cList.contains(items))
                            cList.add(items);
                    }
                }//for
            }

            return cList;
    }//genItemSet

    private static ArrayList<ArrayList<String>> countItemSet(ArrayList<ArrayList<String>> ck , int k )
            throws FileNotFoundException {
        ArrayList<String> transaction;
        File file = new File(address);
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            transaction = new ArrayList<String>();
            String str = sc.nextLine();
            Set<String> trans = new HashSet<>();
            String[] arr = str.split(" ");
            for (int i=2; i< arr.length ; i++)
                trans.add(arr[i]);
            if (trans.size() > 1) {
                for (int i = 0; i < ck.size(); i++) {
                    transaction = new ArrayList<String>();
                    Set<String> items = new HashSet<String>(Arrays.asList(ck.get(i).get(0).split(",")));
                    if (trans.containsAll(items)) {
                        transaction.add(ck.get(i).get(0));
                        transaction.add((Integer.parseInt(ck.get(i).get(1)) + 1) + "");
                        ck.set(i, transaction);
                    }
                }//for
            }//while
        }
        ck=minSup(ck);
      return ck;
    }//countItemSet
}//class
