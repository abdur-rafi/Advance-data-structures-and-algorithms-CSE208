import Graph.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
public class Main {


    public static void rbTreeTest(){
        RedBlackTree<Integer> rbTree = new RedBlackTree<Integer>();

        Random rand = new Random(1);
        ArrayList<Integer> arr = new ArrayList<>();
        for(int i = 0; i < 15; ++i){
            int n = rand.nextInt(20);
            arr.add(n);
            rbTree.addNode(n);

        }

        Collections.shuffle(arr);
        for(Integer i : arr){
            TreePrinter.print(rbTree.getRoot());
            rbTree.deleteVal(i);

        }
    }

    public static void graphTest(){
        IntegerAdder adder = new IntegerAdder();
        var graph = new Graph<Integer>(6);
        graph.addEdge(new Edge<Integer>(5, 0, 0), false);
        graph.addEdge(new Edge<Integer>(5, 2, 0), false);
        graph.addEdge(new Edge<Integer>(2, 3, 0), false);
        graph.addEdge(new Edge<Integer>(1, 3, 0), false);
        graph.addEdge(new Edge<Integer>(4, 1, 0), false);
        graph.addEdge(new Edge<Integer>(4, 0, 0), false);
        graph.setMapTest();
    }

    public static void bipartiteTest(){

        var graph = new Graph<Integer>(7);
        graph.addEdge(new Edge<Integer>(0, 4, 0), true);
        graph.addEdge(new Edge<Integer>(1, 5, 0), true);
        graph.addEdge(new Edge<Integer>(2, 5, 0), true);
        graph.addEdge(new Edge<Integer>(2, 4, 0), true);
        graph.addEdge(new Edge<Integer>(3, 6, 0), true);

//        System.out.println(graph.bipartite());

//        graph.inputFromConsole();
//        File file = new File("src/test.txt");
//        System.out.println(file.exists());
        graph.inputFromFile("src/test.txt");
        System.out.println(graph.bfs(0));
        graph.writeToFile("outputTest.txt");
    }

    public static SinglePairShortestPath inputFromFile(){
        var graph = new SinglePairShortestPath();
        String filePath = "src/inputBellman.txt";
        File file = new File(filePath);
        try (
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferReader = new BufferedReader(fileReader);
                Scanner scanner = new Scanner(bufferReader)
        ) {
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            for(int i = 0; i < n; ++i) graph.addNode();
            for(int i = 0; i < m; ++i){
                int u = scanner.nextInt();
                int v = scanner.nextInt();
                double w = scanner.nextDouble();
                graph.addEdge(new Edge<>(u, v, w), true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return graph;
    }

    static class graphSourceEnd{
        SinglePairShortestPath sp;
        int source;
        int end;

        public graphSourceEnd(SinglePairShortestPath sp, int source, int end) {
            this.sp = sp;
            this.source = source;
            this.end = end;
        }
    }

    public static graphSourceEnd readGraphFromFile(String filePath){
        var spst = new SinglePairShortestPath();
        int s = 0, t = 0;
        File file = new File(filePath);
        try (
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferReader = new BufferedReader(fileReader);
                Scanner scanner = new Scanner(bufferReader)
        ) {
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            for(int i = 0; i < n; ++i) spst.addNode();
            for(int i = 0; i < m; ++i){
                int u = scanner.nextInt();
                int v = scanner.nextInt();
                double w = scanner.nextDouble();
                spst.addEdge(new Edge<>(u, v, w), false);
            }
            s = scanner.nextInt();
            t = scanner.nextInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new graphSourceEnd(spst, s, t);

    }

    public static void offline1(){
        var graph = new GraphMST();
        String filePath = "src/inputBellman.txt";
        File file = new File(filePath);
        try (
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferReader = new BufferedReader(fileReader);
                Scanner scanner = new Scanner(bufferReader)
        ) {
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            for(int i = 0; i < n; ++i) graph.addNode();
            for(int i = 0; i < m; ++i){
                int u = scanner.nextInt();
                int v = scanner.nextInt();
                double w = scanner.nextDouble();
                graph.addEdge(new Edge<>(u, v, w), true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        var mstEdges = graph.MSTkrushkal();
        System.out.println(mstEdges);
        double s = 0;
        for(var x : mstEdges){
            s += x.weight;
        }
        System.out.println(s);

        mstEdges = graph.MSTPrim();
        System.out.println(mstEdges);
        s = 0;
        for(var x : mstEdges){
            s += x.weight;
        }
        System.out.println(s);

    }



    public static AllPairShortestPath readGraphFromFileOffline3(String filePath){
        AllPairShortestPath apsp = null;
        int s = 0, t = 0;
        File file = new File(filePath);
        try (
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferReader = new BufferedReader(fileReader);
                Scanner scanner = new Scanner(bufferReader)
        ) {
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            apsp = new AllPairShortestPath(n);
            for(int i = 0; i < m; ++i){
                int u = scanner.nextInt();
                int v = scanner.nextInt();
                --u;--v;
                double w = scanner.nextDouble();
                apsp.addEdge(new Edge<>(u, v, w), false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return apsp;

    }

    public static void printMat(double[][] dist){
//        System.out.println(Arrays.deepToString(dist));
        for (double[] doubles : dist) {
            for (double aDouble : doubles) System.out.print(aDouble + " ");
            System.out.println();
        }
    }

    public static void offline3(){
        AllPairShortestPath apsp = readGraphFromFileOffline3("src/input.txt");
        double[][] distances = apsp.FloydWarshall();
        printMat(distances);
        double[][] allDist = apsp.Johnson();
        if(allDist == null){
            System.out.println("Negative cycle exists");
        }
        else{
            printMat(allDist);
        }
    }

    public static void main(String[] args){
//        graphTest();
//        bipartiteTest();
//        offline1();

//        offline2();
//        offline3();
//        offline4();

        convertToGraph();

//        double d = 0.0;
//        Double d2 = 0.0;
//        System.out.println(d2.compareTo(d));
    }



    public static void offline4(){
        String filePath = "src/input.txt";
        Graph<Double> g = new Graph<Double>();
        int s = 0, t = 0;
        File file = new File(filePath);
        try {
            Scanner scanner = new Scanner(file);
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            g = new Graph<Double>(n);
            for(int i = 0; i < m; ++i){
                int a = scanner.nextInt();
                int b = scanner.nextInt();
                double weight = scanner.nextDouble();
                g.addEdge(new Edge<Double>(a, b, weight), false);
            }
            s = scanner.nextInt();
            t = scanner.nextInt();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        MaxFlowMinCut maxFlowMinCut = new MaxFlowMinCut(g);
        maxFlowMinCut.MaxFlow(s, t);
    }

    public static void printExplanation(ArrayList<Integer> wins,
                                        ArrayList<Integer> left,
                                        ArrayList<Integer> sources, ArrayList<String> names,
                                        ArrayList<ArrayList<Integer>> matchPairs,
                                        int i
    ) {
        System.out.printf("They can win at most %d + %d =  %d%n", wins.get(i), left.get(i),
                wins.get(i) + left.get(i));
        if (sources.size() == 1) {
            System.out.print(names.get(sources.get(0)));
        } else {
            for (int j = 0; j < sources.size() - 1; ++j)
                System.out.print(names.get(sources.get(j)) + ", ");
            System.out.print(names.get(sources.get(sources.size() - 1)));
        }
        int w = 0;
        int gCount = 0;
        for (int j = 0; j < sources.size(); ++j) {
            for (int k = j + 1; k < sources.size(); ++k) {
                gCount += matchPairs.get(sources.get(j)).get(sources.get(k));
            }
        }
        for (int s : sources)
            w += wins.get(s);
        System.out.printf(" %s won total of %d games\n", sources.size() == 1 ? "has" : "have" , w);
        System.out.printf("The play each other %d games\n", gCount);
        System.out.printf("So on average, each of the teams in this group wins %d/%d = %.2f\n",
                gCount + w, sources.size(), ((double) gCount + w) / sources.size());

    }

    public static void convertToGraph() {

        String filePath = "src/input.txt";
        ArrayList<Integer> wins = new ArrayList<>();
        ArrayList<Integer> losses = new ArrayList<>();
        ArrayList<Integer> left = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<ArrayList<Integer>> matchPairs = new ArrayList<>();
        Graph<Double> g = new Graph<Double>();
        File file = new File(filePath);
        int n = 4;
        try {
            Scanner scanner = new Scanner(file);
            n = scanner.nextInt();
            for(int i = 0; i < n; ++i){
                names.add(scanner.next());
                wins.add(scanner.nextInt());
                losses.add(scanner.nextInt());
                left.add(scanner.nextInt());
                ArrayList<Integer> arr = new ArrayList<>();
                for(int j = 0; j < n; ++j) {
                    int t = scanner.nextInt();
                    arr.add(t);
                }
                matchPairs.add(arr);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }
        ArrayList<Integer> teamNodes = new ArrayList<>();
//        ArrayList<ArrayList<Integer>> matchNodes = new ArrayList<>();
        ArrayList<Integer> teamSources = new ArrayList<>();
        ArrayList<Integer> teamSinks = new ArrayList<>();
        ArrayList<Edge<Double>> teamToSinkEdges = new ArrayList<Edge<Double>>();


        for(int i = 0; i < n; ++i){
            int currNode = g.nodeCount();
            teamNodes.add(currNode);
            g.addNode();
            Edge<Double> e = new Edge<Double>(currNode, -1, 0.0);
            teamToSinkEdges.add(e);
            g.addEdge(e, false);
        }

        for(int i = 0; i < n; ++i){
            teamSources.add(g.nodeCount());
            g.addNode();
        }

        for(int i = 0; i < n; ++i){
            teamSinks.add(g.nodeCount());
            g.addNode();
        }




        for(int i = 0; i < n; ++i){
            for(int j = i; j < n; ++j){
                 if(matchPairs.get(i).get(j) != 0){
                     int currNode = g.nodeCount();
                     g.addNode();
                     g.addEdge(new Edge<Double>(currNode, teamNodes.get(i), Double.POSITIVE_INFINITY), false);
                     g.addEdge(new Edge<Double>(currNode, teamNodes.get(j), Double.POSITIVE_INFINITY), false);
                    for(int k = 0; k < n; ++k){
                        if(k != i && k != j){
                            g.addEdge(new Edge<Double>(teamSources.get(k),currNode,matchPairs.get(i).get(j).doubleValue()), false);
                        }
                    }
                 }
            }
        }


        for(int i = 0; i < n; ++i){
            boolean cont = false;
            for(int j = 0; j < n; ++j){
                teamToSinkEdges.get(j).to = teamSinks.get(i);
                double weight = wins.get(i) + left.get(i) - wins.get(j);
                if(weight < 0){
                    System.out.println(names.get(i) + " is eliminated");
                    ArrayList<Integer> sources = new ArrayList<>();
                    sources.add(j);
                    printExplanation(
                            wins, left, sources,names, matchPairs, i
                    );
                    cont = true;
                    break;
                }
                teamToSinkEdges.get(j).weight = weight;
            }
            if(cont) continue;
            MaxFlowMinCut maxFlowMinCut = new MaxFlowMinCut(g);
            double flow = maxFlowMinCut.MaxFlow(teamSources.get(i), teamSinks.get(i));
            double capacity = maxFlowMinCut.edgeCapacityFromNode(teamSources.get(i));

            if(Double.compare(flow, capacity) == 0){
                continue;
            }

            System.out.println(names.get(i) + " is eliminated");

            ArrayList<ArrayList<Integer>> cut = maxFlowMinCut.MinCut(teamSources.get(i));
            ArrayList<Integer> sourceSet = cut.get(0);
            ArrayList<Integer> sources = new ArrayList<>();
            for(int s : sourceSet){
                if(s < n){
                    sources.add(s);
                }
            }
            printExplanation(wins, left, sources, names, matchPairs, i);
        }

    }

    public static void printPath(int s, int t, ArrayList<EdgeDistancePair> arr){

        double totalCost = 0;
        ArrayList<Edge<Double>> pathEdges = new ArrayList<Edge<Double>>();
//        System.out.println(s);
//        System.out.println(t);
        while(t != s){
            Edge<Double> e = arr.get(t).pathEdge;
            totalCost += e.weight;
            pathEdges.add(e);
            t = e.from;
        }
        Collections.reverse(pathEdges);
        System.out.println(totalCost);
        System.out.print(s);

        for(Edge<Double> e : pathEdges)
            System.out.print(" -> "+ e.to);

        System.out.println();
    }

    public static void offline2(){
        SinglePairShortestPath spst;
        int s = 0, t = 0;
        String filePathBellMan = "src/inputBellman.txt";
        String filePathDij = "src/inputDij.txt";
        graphSourceEnd g = readGraphFromFile(filePathBellMan);
        spst = g.sp;
        s = g.source;
        t = g.end;

        System.out.println("----------------------- Bellman-Ford------------------------------");
        ArrayList<EdgeDistancePair> arr = spst.BellManFord(s);
//        System.out.println(arr);
        if(arr == null){
            System.out.println("The graph contains negative edge cycle");
        }
        else{
            System.out.println("The graph does not contain negative cycle");
            printPath(s, t, arr);
        }

        g = readGraphFromFile(filePathDij);
        spst = g.sp;
        s = g.source;
        t = g.end;

        System.out.println("----------------------- Dijkstra ------------------------------");


        arr = spst.Dijkstra(s);

        printPath(s, t, arr);
    }
}
