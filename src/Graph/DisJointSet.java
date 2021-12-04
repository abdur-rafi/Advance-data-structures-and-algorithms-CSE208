package Graph;

import java.util.ArrayList;

class pair{
    public int parent;
    public int rank;

    public pair(int parent, int rank){
        this.parent = parent;
        this.rank = rank;
    }
}

public class DisJointSet {
    private ArrayList<pair> arrList;

    public DisJointSet(int n){
        arrList = new ArrayList<pair>();
        for(int i = 0; i < n; ++i){
            arrList.add(new pair(i, 0));
        }
    }

    public int getRepresentative(int u){
        if(arrList.get(u).parent != u){

            var p = getRepresentative(arrList.get(u).parent);
            arrList.get(u).parent = p;
            return p;
        }
        return u;
    }

    public boolean isInSameSet(int u, int v){
        return getRepresentative(u) == getRepresentative(v);
    }

    public void union(int u, int v){


        var pu = getRepresentative(u);
        var pv = getRepresentative(v);

        if(pu == pv) return;
        var pairU = arrList.get(pu);
        var pairV = arrList.get(pv);
        if(pairU.rank > pairV.rank){
            pairV.parent = pairU.parent;
        }
        else{
            pairU.parent = pairV.parent;
            if(pairU.rank == pairV.rank){
                pairV.rank++;
            }

        }
    }
}
