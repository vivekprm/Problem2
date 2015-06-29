package com.problem;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by vivek on 27/6/15.
 */
public class Digraph {
    private final int V;
    private int E;
    private Bag<Integer>[] adj;

    public Digraph(int V) {
        if (V < 0) throw new IllegalArgumentException("Number of vertices must be non-negative");
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<>();
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    private boolean contains(Bag<Integer> integers, int w){
        Iterator<Integer> it = integers.iterator();
        while (it.hasNext()){
            if(it.next()==w){
                return true;
            }
        }
        return false;
    }
    public void addEdge(int v, int w) {
        if(!contains(adj[v], w)) {
            adj[v].add(w);
            E++;
        }
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    public String stringRepresentation(Map<String, Integer> mapping) {
        StringBuilder s = new StringBuilder();
        String NEWLINE = System.getProperty("line.separator");
        s.append(V + " vertices, " + E + " edges " + NEWLINE);
        s.append("P: Q R S indicates Q, R, S follow P (i.e. P>Q, P>R, P>S)"+ NEWLINE);
        for (int v = 0; v < V; v++) {
            if(getKeyFromValue(mapping,v)!=null)
            s.append(String.format("%s: ", getKeyFromValue(mapping,v)));
            for (int w : adj[v]) {
                s.append(String.format("%s ", getKeyFromValue(mapping,w)));
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    private static Object getKeyFromValue(Map hm, Object value) {
        for (Object o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }
}
