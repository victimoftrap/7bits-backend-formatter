package it.sevenbits.formatter;

public class Main {
    public static void main(String[] args) {
        Formatter formatter = new Formatter();
        // System.out.println(formatter.format("aaa   {   x;  y;   aaa  {y;  aaa{    }}z;   }x;"));
        System.out.println(formatter.format("aaa   {   bb;   cc;   aaa   {   dd;     }   ee;}"));
    }
}
