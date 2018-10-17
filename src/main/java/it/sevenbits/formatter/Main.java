package it.sevenbits.formatter;

public class Main {
    public static void main(String[] args) {
        Formatter formatter = new Formatter();
        System.out.println(formatter.format("aaa{          bb;  cc;dd; ee;  fff   {ggg {    }}}"));
    }
}
