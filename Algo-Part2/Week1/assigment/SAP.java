import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;

public class SAP {
    private final Digraph G;

    public SAP(Digraph G) {
        if(G == null) {
            throw new IllegalArgumentException();
        }
        this.G = new Digraph(G);
    }

    private Map<Integer, Integer> getAncestors(int v) {
        Queue<Integer> que = new Queue<Integer>();
        Map<Integer, Integer> ancestors = new HashMap<Integer, Integer>();
        que.enqueue(v);
        ancestors.put(v, 0);
        while(!que.isEmpty()) {
            int head = que.dequeue();
            int dist = ancestors.get(head);
            for(Integer w: G.adj(head)) {
                if(!ancestors.containsKey(w) || ancestors.get(w) > dist + 1) {
                    que.enqueue(w);
                    ancestors.put(w, dist + 1);
                }
            }
        }
        return ancestors;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if(v < 0 || v >= G.V() || w < 0 || w >= G.V()) {
            throw new IllegalArgumentException();
        }
        Map<Integer, Integer> ancestorsV = getAncestors(v);
        Map<Integer, Integer> ancestorsW = getAncestors(w);
        int dist = -1;
        for(Entry<Integer, Integer> items: ancestorsV.entrySet()) {
            if(ancestorsW.containsKey(items.getKey())) {
                int current_dist = ancestorsW.get(items.getKey()) + items.getValue();
                if(dist < 0 || current_dist < dist) {
                    dist = current_dist;
                }
            }
        }
        return dist;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if(v < 0 || v >= G.V() || w < 0 || w >= G.V()) {
            throw new IllegalArgumentException();
        }
        Map<Integer, Integer> ancestorsV = getAncestors(v);
        Map<Integer, Integer> ancestorsW = getAncestors(w);
        int dist = -1;
        int anc = -1;
        for(Entry<Integer, Integer> items: ancestorsV.entrySet()) {
            if(ancestorsW.containsKey(items.getKey())) {
                int current_dist = ancestorsW.get(items.getKey()) + items.getValue();
                if(dist < 0 || current_dist < dist) {
                    dist = current_dist;
                    anc = items.getKey();
                }
            }
        }
        return anc;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if(v == null || w == null) {
            throw new IllegalArgumentException();
        }
        int dist = -1;
        for(Integer eV: v) {
            if(eV == null) {
                throw new IllegalArgumentException();
            }
            for(Integer eW: w) {
                if(eW == null) {
                    throw new IllegalArgumentException();
                }
                if(eW == eV) {
                    return 0;
                }
                int current_dist = length(eV, eW);
                if(current_dist > 0 && (dist < 0 || current_dist < dist)) {
                    dist = current_dist;
                }
            }
        }
        return dist;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if(v == null || w == null) {
            throw new IllegalArgumentException();
        }
        int dist = -1;
        int ans = -1;
        for(Integer eV: v) {
            if(eV == null) {
                throw new IllegalArgumentException();
            }
            for(Integer eW: w) {
                if(eW == null) {
                    throw new IllegalArgumentException();
                }
                if(eW == eV) {
                    return eW;
                }
                int current_dist = length(eV, eW);
                if(current_dist > 0 && (dist < 0 || current_dist < dist)) {
                    dist = current_dist;
                    ans = ancestor(eV, eW);
                }
            }
        }
        return ans;
    }

    // do unit testing of this class
    public static void main(String[] args) {
    }
}
