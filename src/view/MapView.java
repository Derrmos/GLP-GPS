package view;

import model.Map;
import model.Network;
import model.Node;
import process.builders.MapBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MapView extends JPanel{

    private Map map ;

    private class Click implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();

            for(Node node : map.getNodes().values()){
                if(node.getPosition().getX() <= x+6 && node.getPosition().getX()>= x-6
                        && node.getPosition().getY() <= y+6 && node.getPosition().getY() >= y-6){
                    if(node.isPOI())
                    JOptionPane.showMessageDialog(MapView.this,node.getPoi().getName());
                    else JOptionPane.showMessageDialog(MapView.this,"Ce n'est pas un POI", "PAS POI", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    public MapView(Map map){
        this.map = map ;
        this.addMouseListener(new Click());
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g.create();

        for (Network network : map.getNetworks().values()){
           for(String nodeId1 : network.getWays().keySet()){
               Node node1 = map.getNodes().get(nodeId1);
               for(String nodeId2 : network.getWays().get(nodeId1).keySet()){
                   Node node2 = map.getNodes().get(nodeId2);
                   g2d.setStroke(new BasicStroke(5));
                   g2d.drawLine(node1.getPosition().getX(),node1.getPosition().getY(), node2.getPosition().getX(), node2.getPosition().getY());
               }
           }
        }
        for(Node node : map.getNodes().values()){
            g.setColor(nodeColorType(node));
            g.fillOval(node.getPosition().getX()-6,node.getPosition().getY()-6,12,12);
        }
    }
    private Color nodeColorType(Node node){
        Color color = Color.BLACK;
        if(node.getPoi() != null) {
            switch (node.getPoi().getType()) {
                case ATTRACTION: {
                    color = Color.ORANGE;
                    break;
                }
                case BUILDING: {
                    color = Color.BLUE;
                    break;
                }
                case STATION: {
                    color = Color.GRAY;
                    break;
                }
                default:
                    color = Color.BLACK;
            }
        }
        return color;
    }

}
