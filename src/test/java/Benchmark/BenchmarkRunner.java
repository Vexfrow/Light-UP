package Benchmark;

import formalisation.Clause;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;

public class BenchmarkRunner {
    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void init(){
        Clause c = new Clause();

        c.disjonction(19);
        c.disjonction(9);
        c.disjonction(3);

        String s = c.toString();
    }


}