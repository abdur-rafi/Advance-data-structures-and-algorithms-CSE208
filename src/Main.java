import Graph.Graph;
import Graph.RedBlackTree;
import Graph.TreePrinter;

import java.io.*;
import java.util.*;

import Graph.Edge;
import Graph.IntegerAdder;
import Graph.DoubleAdder;
import Graph.GraphMST;
import Graph.SinglePairShortestPath;
import Graph.EdgeDistancePair;
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
        for(var i : arr){
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
//        graph.addEdge(new Edge<Integer>(4, 6, 0));
//        graph.addEdge(new Edge<Integer>(3, 4, 0), false);
//        graph.addEdge(new Edge<Integer>(0, 1, 0), false);

//        graph.addEdge(new Edge<Integer>(1, 0, 0));
//        graph.addEdge(new Edge<Integer>(1, 0, 0));

//        var lst = graph.topologicalSort();
//        System.out.println(lst);
//        System.out.println(graph.topSortWithDegree());
//
//        var components = graph.StronglyConnectedComponents();
//        System.out.println(components);

//        graph.addEdge(new Edge<Integer>(1, 0, 0));
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
        var file = new File(filePath);
        try (
                var fileReader = new FileReader(file);
                var bufferReader = new BufferedReader(fileReader);
                var scanner = new Scanner(bufferReader)
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
        var file = new File(filePath);
        try (
                var fileReader = new FileReader(file);
                var bufferReader = new BufferedReader(fileReader);
                var scanner = new Scanner(bufferReader)
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
        var file = new File(filePath);
        try (
                var fileReader = new FileReader(file);
                var bufferReader = new BufferedReader(fileReader);
                var scanner = new Scanner(bufferReader)
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

    public static void main(String[] args){
//        graphTest();
//        bipartiteTest();
//        offline1();

        offline2();
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
