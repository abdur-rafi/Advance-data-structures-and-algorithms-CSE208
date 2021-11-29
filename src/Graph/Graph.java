package Graph;

import java.io.*;
import java.util.*;

public class Graph<T> {

    private ArrayList<ArrayList<Edge<T>>> adjList;
    private int time = 0;
    private final int WHITE = 0, GREY = 1, BLACK = 2;
    public Graph(int n){
        adjList = new ArrayList<>();
        for(int i = 0; i < n; ++i){
            adjList.add(new ArrayList<>());
        }
    }
    public Graph(){
        adjList = new ArrayList<>();
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
        Graph<T> gT = new Graph<T>(adjList.size());
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

}
