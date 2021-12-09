import Graph.Graph;
import Graph.RedBlackTree;
import Graph.TreePrinter;

import java.io.*;
import java.util.*;

import Graph.Edge;
import Graph.IntegerAdder;
import Graph.DoubleAdder;
import Graph.GraphMST;
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

    public static void offline1(){
        var graph = new GraphMST();
        String filePath = "src/input.txt";
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

    public static void main(String args[]){
//        graphTest();
//        bipartiteTest();
        offline1();

    }
}
