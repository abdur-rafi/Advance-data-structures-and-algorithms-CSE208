import Graph.Graph;
import Graph.RedBlackTree;
import Graph.TreePrinter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import Graph.Edge;

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

    public static void main(String args[]){
        graphTest();
//        bipartiteTest();

    }
}
