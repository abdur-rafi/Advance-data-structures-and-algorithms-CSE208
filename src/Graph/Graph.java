package Graph;

import java.io.*;
import java.util.*;

public class Graph<T> {

    private ArrayList<ArrayList<Edge<T>>> adjList;
    private int time = 0;
    private final int WHITE = 0, GREY = 1, BLACK = 2;
    private Adder<T> adder;
    public Graph(int n, Adder<T> adder){
        adjList = new ArrayList<>();
        for(int i = 0; i < n; ++i){
            adjList.add(new ArrayList<>());
        }
        this.adder = adder;
    }
    public Graph(Adder<T> adder){
        adjList = new ArrayList<>();
        this.adder = adder;
    }
    public void addNode(){
        adjList.add(new ArrayList<>());
    }
    public void addEdge(Edge<T> e, boolean addReverse){
        adjList.get(e.from).add(e);
        if(addReverse){
            var r = new Edge<T>(e.to, e.from, e.weight);
            adjList.get(r.from).add(r);
        }
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
        Graph<T> gT = new Graph<T>(adjList.size(), adder);
        for(var u : adjList){
            for(var v : u){
                gT.addEdge(new Edge<T>(v.to, v.from, v.weight), false);
            }
        }
        ArrayList<ArrayList<Integer>> components = new ArrayList<>();
        for(int i = 0; i < adjList.size(); ++i) vis.set(i, false);
        while(!nodeStack.isEmpty()){
            var top = nodeStack.pop();
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
            var scanner = new Scanner(file);
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
                var prevDegree = inDegrees.get(v.to);
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
            var front = q.poll();
            for(var v : adjList.get(front)){
                var prev = inDegrees.get(v.to);
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

    public ArrayList<Edge<T>> MSTkrushkal(){
        ArrayList<Edge<T>> edges = new ArrayList<>();
        for(var u : adjList){
            edges.addAll(u);
        }
        edges.sort((e1, e2)->adder.comp(e1.weight, e2.weight));
        int m = edges.size();
        DisJointSet set = new DisJointSet(adjList.size());
        ArrayList<Edge<T>> mstEdges = new ArrayList<Edge<T>>();
        for (Edge<T> edge : edges) {
            if (!set.isInSameSet(edge.from, edge.to)) {
                mstEdges.add(edge);
                set.union(edge.from, edge.to);
            }
            if (mstEdges.size() == adjList.size() - 1) break;
        }

        return mstEdges;
    }

    public ArrayList<Edge<T>> MSTPrim(){

        class nodeDistPair{
            public int node;
            public T dist;
            nodeDistPair(int n, T d){
                node = n;
                dist = d;
            }
        }

        PriorityQueue<nodeDistPair> pq = new PriorityQueue<>((t1, t2)->{
            if(adder.comp(t1.dist, t2.dist) == 0){
                return t1.node - t2.node;
            }
            return adder.comp(t1.dist, t2.dist);
        });
        int n = adjList.size();

        T mx = adder.zero();
        for(var u : adjList){
            for(var v : u) mx = adder.add(mx, v.weight);
        }

        for(int i = 1; i < n; ++i){
            pq.add(new nodeDistPair(i, mx));
        }
        pq.add(new nodeDistPair(0, adder.zero()));

        ArrayList<Integer> parent = new ArrayList<>();
        for(int i = 0; i < n ;++i) parent.add(-1);
        while(!pq.isEmpty()){
            var top = pq.remove();
            for(var x : adjList.get(top.node)){
                
            }
        }

        return new ArrayList<Edge<T>>();
    }
}
