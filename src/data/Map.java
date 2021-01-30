package data;
import data.Node;

import java.util.HashMap;

 public class Map {
    HashMap<String,Node> nodes;
    private static Map map ;

    public Map() {
        nodes = new HashMap<>();
    }

     public HashMap<String, Node> getNodes() {
         return nodes;
     }

     public static Map getMap() {
         return map;
     }

     @Override
     public String toString() {
         return "Map{" +
                 "nodes=" + nodes +
                 '}';
     }
 }
