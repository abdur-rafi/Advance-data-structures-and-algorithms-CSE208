package Graph;

import java.io.*;
import java.util.*;

public class Graph<T extends Comparable<T> > {

    public ArrayList<ArrayList<Edge<T>>> adjList;
    protected int time = 0;
    protected final int WHITE = 0, GREY = 1, BLACK = 2;
    protected ArrayList<Edge<T>> edges;
    public Graph(int n){
        adjList = new ArrayList<>();
        edges = new ArrayList<>();
        for(int i = 0; i < n; ++i){
            adjList.add(new ArrayList<>());
        }
    }
    public Graph(){
        adjList = new ArrayList<>();
        edges = new ArrayList<Edge<T>>();
    }
    public void addNode(){
        adjList.add(new ArrayList<>());
    }
    public void addEdge(Edge<T> e, boolean addReverse){
        adjList.get(e.from).add(e);
        edges.add(e);
        if(addReverse){
            var r = new Edge<T>(e.to, e.from, e.weight);
            adjList.get(r.from).add(r);
            edges.add(r);
        }
    }
    public int nodeCount(){
        return adjList.size();
    }

    public ArrayList<Edge<T>> getEdges(){
        return edges;
    }

    public ArrayList<Integer> bfs(int start){
        Queue<Integer> q = new LinkedList<>();
        q.add(start);
        ArrayList<Integer> levels = new ArrayList<>();
        for(int i = 0; i < adjList.size(); ++i) levels.add(-1);
        levels.set(start, 0);
        while(!q.isEmpty()){
            int u = q.remove();
            for(var v : adjList.get(u)){
                if(levels.get(v.to) == -1){
                    levels.set(v.to, levels.get(u) + 1);
                    q.add(v.to);
                }
            }
        }
        return levels;
    }

    public ArrayList<Edge<T>> bfsReturnEdge(int start){
        Queue<Integer> q = new LinkedList<>();
        q.add(start);
        ArrayList<Integer> levels = new ArrayList<>();
        ArrayList<Edge<T>> parentEdges = new ArrayList<Edge<T>>();
        for(int i = 0; i < adjList.size(); ++i) parentEdges.add(null);
        for(int i = 0; i < adjList.size(); ++i) levels.add(-1);
        levels.set(start, 0);
        while(!q.isEmpty()){
            int u = q.remove();
            for(var v : adjList.get(u)){
                if(levels.get(v.to) == -1){
                    levels.set(v.to, levels.get(u) + 1);
                    parentEdges.add(v);
                    q.add(v.to);
                }
            }
        }
        return parentEdges;
    }

    public ArrayList<Edge<T>> bfsReturnEdgeIgnoreZero(int start, T zero){
        Queue<Integer> q = new LinkedList<>();
        q.add(start);
        ArrayList<Integer> levels = new ArrayList<>();
        ArrayList<Edge<T>> parentEdges = new ArrayList<Edge<T>>();
        for(int i = 0; i < adjList.size(); ++i) parentEdges.add(null);
        for(int i = 0; i < adjList.size(); ++i) levels.add(-1);
        levels.set(start, 0);
        while(!q.isEmpty()){
            int u = q.remove();
            for(var v : adjList.get(u)){
                if(v.weight.compareTo(zero) == 0){
                    continue;
                }
                if(levels.get(v.to) == -1){
                    levels.set(v.to, levels.get(u) + 1);
                    parentEdges.set(v.to, v);
                    q.add(v.to);
                }
            }
        }
        return parentEdges;
    }


    public ArrayList<Integer> dfs(int start){
        ArrayList<Integer> color = new ArrayList<>();
        for(int i = 0; i < adjList.size(); ++i) color.add(WHITE);
        _dfs(start, color);
        for(int i = 0; i < adjList.size(); ++i){
            if(color.get(i) == WHITE)
                _dfs(i, color);
        }
        return color;
    }

    public void _dfs(int u, ArrayList<Integer> color){
        color.set(u, GREY);
        for(var v : adjList.get(u)){
            if(color.get(v.to) == WHITE){
                _dfs(v.to, color);
            }

        }
        color.set(u, BLACK);
    }

    private boolean _topDfs(int u, ArrayList<Integer> color, ArrayList<Integer> lst){
        color.set(u, GREY);
        boolean r = true;
        for(var v : adjList.get(u)){
            if(color.get(v.to) == WHITE){
                r &= _topDfs(v.to, color, lst);
            }
            else if(color.get(v.to) == GREY){
                return false;
            }

        }
        color.set(u, BLACK);
        lst.add(u);
        return r;
    }

    public ArrayList<Integer> topologicalSort(){
        ArrayList<Integer> lst = new ArrayList<>();
        ArrayList<Integer> color = new ArrayList<>();
        for(int i = 0; i < adjList.size(); ++i) color.add(WHITE);

        for(int i = 0; i < adjList.size(); ++i){
            if(color.get(i) == WHITE) {
                if(!_topDfs(i, color, lst)){
                    return null;
                }

            }
        }
        Collections.reverse(lst);
        return lst;
    }

    private void dfsForSCC(int u, Stack<Integer> nodeStack, ArrayList<Boolean> vis){
        for(var v : adjList.get(u)){
            if(!vis.get(v.to)){
                vis.set(v.to, true);
                dfsForSCC(v.to, nodeStack, vis);
            }
        }
        nodeStack.push(u);
    }

    public ArrayList<ArrayList<Integer>> StronglyConnectedComponents(){
        this.time = 0;
        ArrayList<Boolean> vis = new ArrayList<>();
        for(int i = 0; i < adjList.size(); ++i){
            vis.add(false);
        }
        Stack<Integer> nodeStack = new Stack<>();
        for(int i = 0; i < adjList.size(); ++i){
            if(!vis.get(i)){
                dfsForSCC(i, nodeStack, vis);
            }
        }
        Graph<T> gT = new Graph<T>(adjList.size());
        for(var u : adjList){
            for(var v : u){
                gT.addEdge(new Edge<T>(v.to, v.from, v.weight), false);
            }
        }
        ArrayList<ArrayList<Integer>> components = new ArrayList<>();
        for(int i = 0; i < adjList.size(); ++i) vis.set(i, false);
        while(!nodeStack.isEmpty()){
            Integer top = nodeStack.pop();
            if(!vis.get(top)) {
                Stack<Integer> s = new Stack<>();
                vis.set(top, true);
                gT.dfsForSCC(top, s, vis);
                components.add(new ArrayList<>(s));
            }
        }

        return components;
    }

    private boolean _bipartite(int startNode, ArrayList<Integer> colors){
        Queue<Integer> q = new LinkedList<>();
        q.add(startNode);
        colors.set(startNode, 0);
        while(!q.isEmpty()){
            int top = q.remove();
            for(var v : adjList.get(top)) {
                if (colors.get(v.to) == -1) {
                    colors.set(v.to, (colors.get(top) + 1) % 2);
                    q.add(v.to);
                }
                else if(colors.get(v.to).compareTo(colors.get(top)) == 0 ){
                    return false;
                }
            }
        }
        return  true;
    }

    public ArrayList<Integer> bipartite(){
        ArrayList<Integer> colors = new ArrayList<>();
        for(int i = 0; i < adjList.size(); ++i) colors.add(-1);
        boolean r = true;
        for(int i = 0; i < adjList.size(); ++i){
            if(colors.get(i) == -1){
                r= r & _bipartite(i, colors);
                if(!r) return null;
            }
        }
        return colors;
    }

    public void inputFromConsole(){
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        adjList = new ArrayList<>();
        for(int i = 0; i < n; ++i) adjList.add(new ArrayList<>());
        for(int i = 0; i < m; ++i){
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            addEdge(new Edge<T>(a, b), false);
        }
        System.out.println(bfs(0));
    }

    public void inputFromFile(String filePath){
        File file = new File(filePath);
        try {
            Scanner scanner = new Scanner(file);
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            adjList = new ArrayList<>();
            for(int i = 0; i < n; ++i) adjList.add(new ArrayList<>());
            for(int i = 0; i < m; ++i){
                int a = scanner.nextInt();
                int b = scanner.nextInt();
                double weight = scanner.nextDouble();
                addEdge(new Edge<T>(a, b), false);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void writeToFile(String filePath){
        File file = new File(filePath);
        try(
                FileWriter fileWriter = new FileWriter(file, true);
                BufferedWriter bw = new BufferedWriter(fileWriter);
                PrintWriter pw = new PrintWriter(bw);
        ) {
            bw.write("hello file 2");
            pw.println("print writer");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Integer> topSortWithDegree(){
        int n = adjList.size();
        ArrayList<Integer> inDegrees = new ArrayList<>();
        for(int i = 0; i < n; ++i) inDegrees.add(0);
        for(var u : adjList){
            for(var v : u){
                Integer prevDegree = inDegrees.get(v.to);
                inDegrees.set(v.to, prevDegree + 1);
            }
        }
//        Queue<Integer> q = new LinkedList<>();
        PriorityQueue<Integer> q = new PriorityQueue<>((o1, o2) -> -1 * o1.compareTo(o2));
        for(int i = 0; i < n; ++i){
            if(inDegrees.get(i) == 0) q.add(i);
        }
        System.out.println(q);
        System.out.println(q.peek());
        ArrayList<Integer> topSorted = new ArrayList<>();
        while(!q.isEmpty()){
            Integer front = q.poll();
            for(var v : adjList.get(front)){
                Integer prev = inDegrees.get(v.to);
                if(prev == 1){
                    q.add(v.to);
                }
                inDegrees.set(v.to, prev - 1);
            }
            topSorted.add(front);
        }
        if(topSorted.size() != n) return null;
        return topSorted;

    }

    public void setMapTest(){
        Set<Integer> s = new HashSet<>();
        Set<Edge<Integer>> st = new HashSet<>();
        var edge = new Edge<Integer>(0, 5,0);
        st.add(edge);
        edge.to = 3;
        System.out.println(st.contains(edge));

        Set<Edge<Integer>> st2 = new TreeSet<>((o1, o2)->{
            if(o1.from == o2.from) return o1.to - o2.to;
            return o1.from - o2.from;
        });
        st2.add(edge);
        System.out.println(st2.contains(edge));
        edge.to = 1;
        System.out.println(st2.contains(edge));

        Map<Integer, Edge<Integer>> mp = new TreeMap<>();

    }

}
