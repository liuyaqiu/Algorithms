import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.In;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Stack;

public class WordNet {
    private Map<String, List<Integer>> dict = new HashMap<String, List<Integer>>();
    private Map<Integer, String> reverseDict = new HashMap<Integer, String>();
    private SAP sap;

    private boolean dfs(int v, Digraph G, Map<Integer, Boolean> visit, Stack<Integer> S) {
        visit.put(v, true);
        S.push(v);
        for(int w: G.adj(v)) {
            if(S.contains(w)) {
                return false;
            }
            if(!visit.containsKey(w)) {
                if(!dfs(w, G, visit, S)) {
                    return false;
                }
            }
        }
        S.pop();
        return true;
    }

    private boolean isDAG(Digraph G) {
        Map<Integer, Boolean> visit = new HashMap<Integer, Boolean>();
        Stack<Integer> S = new Stack<Integer>();
        for(int i = 0; i < G.V(); i++) {
            if(!visit.containsKey(i)) {
                if(!dfs(i, G, visit, S)) {
                    return false;
                }
            }
        }
        int cnt = 0;
        for(int i = 0; i < G.V(); i++) {
            if(G.outdegree(i) == 0) {
                cnt += 1;
            }
        }
        return cnt == 1;
    }

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if(synsets == null || hypernyms == null) {
            throw new IllegalArgumentException();
        }
        In in = new In(synsets);
        int maxId = 0;
        while(in.hasNextLine()) {
            String line = in.readLine();
            String[] items = line.split(",");
            String[] nouns = items[1].split(" ");
            for(String noun: nouns) {
                List<Integer> noun_list = null;
                if(!dict.containsKey(noun)) {
                    noun_list = new LinkedList<Integer>();
                }
                else {
                    noun_list = dict.get(noun);
                }
                noun_list.add(Integer.parseInt(items[0]));
                dict.put(noun, noun_list);
            }
            reverseDict.put(Integer.parseInt(items[0]), items[1]);
            maxId = Math.max(maxId, Integer.parseInt(items[0]));
        }
        in.close();
        Digraph G = new Digraph(maxId + 1);
        in = new In(hypernyms);
        while(in.hasNextLine()) {
            String line = in.readLine();
            String[] items = line.split(",");
            int v = Integer.parseInt(items[0]);
            for(int i = 1; i < items.length; i++) {
                G.addEdge(v, Integer.parseInt(items[i]));
            }
        }
        in.close();
        if(!isDAG(G)) {
            throw new IllegalArgumentException();
        }
        sap = new SAP(G);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return dict.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if(word == null) {
            throw new IllegalArgumentException();
        }
        return dict.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if(!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
        return sap.length(dict.get(nounA), dict.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if(!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
        int id = sap.ancestor(dict.get(nounA), dict.get(nounB));
        return reverseDict.get(id);
    }

    // do unit testing of this class
    public static void main(String[] args) {
    }
}
