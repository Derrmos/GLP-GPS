package view;

import model.Map;
import model.Network;
import model.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MapView extends JPanel{

    private static final int MIN_DEC_POS_X = -500;
    private static final int MIN_DEC_POS_Y = -500;
    private static final int MAX_DEC_POS_X = 0;
    private static final int MAX_DEC_POS_Y = 0;

    private Map map ;

    private int newDecX = 0;
    private int newDecY = 0;

    private int decPosX = 0;
    private int decPosY = 0;

    private int cursorPosX = 0;
    private int cursorPosY = 0;

    private JButton resetButton = new JButton("Reset default position");

    private class MapDraggedListener implements MouseMotionListener, MouseListener {

        @Override
        public void mouseDragged(MouseEvent e) {
            setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
            int dx = e.getX() - cursorPosX;
            int dy = e.getY() - cursorPosY;

            if (dx + decPosX <= MAX_DEC_POS_X && dx + decPosX >= MIN_DEC_POS_X) newDecX = dx + decPosX;
            if (dy + decPosY <= MAX_DEC_POS_Y && dy + decPosY >= MIN_DEC_POS_Y) newDecY = dy + decPosY;
            repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            cursorPosX = e.getX();
            cursorPosY = e.getY();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            decPosX = newDecX;
            decPosY = newDecY;
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    private class POIClickListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();

            for(Node node : map.getNodes().values()){
                if(node.getPosition().getX() + newDecX <= x+6 && node.getPosition().getX() + newDecX >= x-6
                        && node.getPosition().getY() + newDecY <= y+6 && node.getPosition().getY() + newDecY >= y-6){
                    if(node.isPOI())
                    JOptionPane.showMessageDialog(MapView.this, node.getPoi().getName() + " | Type : " + node.getPoi().getType().toString(), "POI Info", JOptionPane.PLAIN_MESSAGE);
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

    private class ResetDefaultPosButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            decPosX = 0;
            decPosY = 0;
            newDecX = 0;
            newDecY = 0;
            repaint();
        }

    }

    public MapView(Map map){
        this.map = map;

        setLayout(new BorderLayout());

        addMouseListener(new POIClickListener());
        addMouseMotionListener(new MapDraggedListener());
        addMouseListener(new MapDraggedListener());

        resetButton.addActionListener(new ResetDefaultPosButtonListener());
        add(BorderLayout.SOUTH, resetButton);
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
                   g2d.drawLine(node1.getPosition().getX() + newDecX,node1.getPosition().getY() + newDecY, node2.getPosition().getX() + newDecX, node2.getPosition().getY() + newDecY);
               }
           }
        }
        for(Node node : map.getNodes().values()){
            g.setColor(getNodeTypeColor(node));
            g.fillOval(node.getPosition().getX()-6 + newDecX,node.getPosition().getY()-6 + newDecY,12,12);
        }
    }

    private Color getNodeTypeColor(Node node){
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
