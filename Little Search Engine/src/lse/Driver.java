package lse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args)
            throws FileNotFoundException
    {
        LittleSearchEngine engine = new LittleSearchEngine();
        engine.makeIndex("docs.txt", "WowCh1.txt");

        engine.print();
        engine.mergeKeywords(engine.loadKeywordsFromDocument("WowCh1.txt"));

        engine.print();
    }
}