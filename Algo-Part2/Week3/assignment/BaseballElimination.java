import java.util.HashMap;
import java.util.Arrays;
import java.util.ArrayList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FordFulkerson;

public class BaseballElimination {
    private String[] id2team;
    private HashMap<String, Integer> team2id;
    private int[] w;
    private int[] l;
    private int[] r;
    private int[][] g;
    public BaseballElimination(String filename) {
        In in = new In(filename);
        int n = Integer.parseInt(in.readLine().trim());
        id2team = new String[n];
        team2id = new HashMap<String, Integer>();
        w = new int[n];
        l = new int[n];
        r = new int[n];
        g = new int[n][n];
        int id = 0;
        while(in.hasNextLine()) {
            String[] items = in.readLine().trim().split("\\s+");
            String team = items[0];
            int win = Integer.parseInt(items[1]);
            int loss = Integer.parseInt(items[2]);
            int remain = Integer.parseInt(items[3]);
            id2team[id] = team;
            w[id] = win;
            l[id] = loss;
            r[id] = remain;
            team2id.put(team, id);
            for(int k = 0; k < n; k++) {
                g[id][k] = Integer.parseInt(items[4 + k]);
            }
            id += 1;
        }
    }

    public int numberOfTeams() {
        return id2team.length;
    }

    public Iterable<String> teams() {
        return new ArrayList<String>(Arrays.asList(id2team));
    }

    public int wins(String team) {
        if(team == null) {
            throw new IllegalArgumentException();
        }
        Integer id = team2id.get(team);
        if(id == null) {
            throw new IllegalArgumentException();
        }
        return w[id];
    }

    public int losses(String team) {
        if(team == null) {
            throw new IllegalArgumentException();
        }
        Integer id = team2id.get(team);
        if(id == null) {
            throw new IllegalArgumentException();
        }
        return l[id];
    }

    public int remaining(String team) {
        if(team == null) {
            throw new IllegalArgumentException();
        }
        Integer id = team2id.get(team);
        if(id == null) {
            throw new IllegalArgumentException();
        }
        return r[id];
    }

    public int against(String team1, String team2) {
        if(team1 == null || team2 == null) {
            throw new IllegalArgumentException();
        }
        Integer id1 = team2id.get(team1);
        Integer id2 = team2id.get(team2);
        if(id1 == null || id2 == null) {
            throw new IllegalArgumentException();
        }
        return g[id1][id2];
    }

    public boolean isEliminated(String team) {
        if(team == null) {
            throw new IllegalArgumentException();
        }
        Integer k = team2id.get(team);
        if(k == null) {
            throw new IllegalArgumentException();
        }
        int n = numberOfTeams();
        FlowNetwork flow = new FlowNetwork(n + n * n + 2);
        int s = n + n * n;
        int t = n + n * n + 1;
        for(int i = 0; i < n; i++) {
            if(i == k) {
                continue;
            }
            for(int j = i + 1; j < n; j++) {
                if(j == k) {
                    continue;
                }
                flow.addEdge(new FlowEdge(s, i * n + j, g[i][j]));
                flow.addEdge(new FlowEdge(i * n + j, i + n * n, Integer.MAX_VALUE));
                flow.addEdge(new FlowEdge(i * n + j, j + n * n, Integer.MAX_VALUE));
            }
        }
        for(int i = 0; i < n; i++) {
            if(i != k) {
                int cap = w[k] + r[k] - w[i];
                if(cap < 0) {
                    return true;
                }
                flow.addEdge(new FlowEdge(i + n * n, t, cap));
            }
        }
        FordFulkerson max_flow = new FordFulkerson(flow, s, t);
        for(int i = 0; i < n; i++) {
            if(max_flow.inCut(i + n * n)) {
                return true;
            }
        }
        return false;
    }

    public Iterable<String> certificateOfElimination(String team) {
        if(team == null) {
            throw new IllegalArgumentException();
        }
        Integer k = team2id.get(team);
        if(k == null) {
            throw new IllegalArgumentException();
        }
        int n = numberOfTeams();
        FlowNetwork flow = new FlowNetwork(n + n * n + 2);
        int s = n + n * n;
        int t = n + n * n + 1;
        for(int i = 0; i < n; i++) {
            if(i == k) {
                continue;
            }
            for(int j = i + 1; j < n; j++) {
                if(j == k) {
                    continue;
                }
                flow.addEdge(new FlowEdge(s, i * n + j, g[i][j]));
                flow.addEdge(new FlowEdge(i * n + j, i + n * n, Integer.MAX_VALUE));
                flow.addEdge(new FlowEdge(i * n + j, j + n * n, Integer.MAX_VALUE));
            }
        }
        ArrayList<String> res = new ArrayList<String>();
        for(int i = 0; i < n; i++) {
            if(i != k) {
                int cap = w[k] + r[k] - w[i];
                if(cap < 0) {
                    res.add(id2team[i]);
                    return res;
                }
                flow.addEdge(new FlowEdge(i + n * n, t, cap));
            }
        }
        FordFulkerson max_flow = new FordFulkerson(flow, s, t);
        for(int i = 0; i < n; i++) {
            if(max_flow.inCut(i + n * n)) {
                res.add(id2team[i]);
            }
        }
        if(res.isEmpty()) {
            return null;
        }
        return res;
    }

    /*
    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
               StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
    */

}
