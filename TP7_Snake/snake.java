package TP7_Snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.KeyEventPostProcessor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class snake extends JFrame {
	int nbCasesX = 50;
	int nbCasesY = 30;
	JButton button2 = new JButton();
	int largeur =20;
	int vitesse = 100;
	int marge = 100;
	int direction = 1;  //1: droite 2:bas 3:gauche 4: haut
	Color Couleurbg = Color.black;
	Color CouleurTete = Color.orange;
	Color CouleurCorps = Color.green;
	Color CouleurRepas = Color.MAGENTA;
	Color Couleurobstacle = Color.blue;
	
	Cellule tete = new Cellule( 5, 5, CouleurTete);  //just for the test
	
	ArrayList<Cellule> serpent;
	ArrayList<Cellule> obstacles;
	Cellule Repas;
	JPanel notrePanel;
	Timer horloge;
	
	void initialSerpent() {
		serpent = new ArrayList<Cellule>();
		int centrX= nbCasesX/2;
		int centreY = nbCasesY/2;
		serpent.add(new Cellule(centrX, centreY, CouleurTete));
		serpent.add(new Cellule(centrX-1, centreY, CouleurCorps));
		serpent.add(new Cellule(centrX-2, centreY, CouleurCorps));
		serpent.add(new Cellule(centrX-3, centreY, CouleurCorps));
		serpent.add(new Cellule(centrX-4, centreY, CouleurCorps));
	}
	void genererRepas() {
		boolean b=false;
		do {
		b= false;
		Random r= new Random();
		int x = r.nextInt(nbCasesX);
		int y = r.nextInt(nbCasesY);
		Repas = new Cellule(x, y, CouleurRepas);
		
		for(Cellule c : serpent)
			if(c.x == Repas.x && c.y ==Repas.y) {
				b= true;
				break;
			}
		for(Cellule o : obstacles)
			if(o.x == Repas.x && o.y ==Repas.y) {
				b= true;
				break;
			}
		}while(b==true);		
	}
	void obstacles() {
		obstacles = new ArrayList<Cellule>();
		int extrX1= nbCasesX/4;
		int extrX2= 3*nbCasesX/4;
		int extrY1 = nbCasesY/3;
		int extrY2 = 3*nbCasesY/4;
		
		for (int i = extrX1; i<=extrX2 ;i++ ) {
			obstacles.add(new Cellule(i, extrY1, Couleurobstacle));
		}
		for (int i = extrX1; i<=extrX2 ;i++ ) {
			obstacles.add(new Cellule(i, extrY2, Couleurobstacle));
		}
	}
	void gameOver() {
		JFrame frame = new JFrame("game over!!");
		
		JLabel label = new JLabel("Game Over !!!");
		label.setBounds(100, 50, 300, 30);
		
		JButton button1 = new JButton();
		button1.setText("Annuler");
		button1.setBounds(50, 100, 95, 30);
		
//		JButton button2 = new JButton();
		button2.setText("Rejouer");
		button2.setBounds(150, 100, 95, 30);
		
		
		frame.add(button1);
		frame.add(button2);
		frame.add(label);
		frame.setLayout(null);
		frame.setSize(300, 300);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	public snake() {
		this.setTitle("Le jeu Snake !! 14/03/2023");
		
		this.setSize(nbCasesX*largeur+2*marge+2*7,nbCasesY*largeur+31+7+2*marge);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		initialSerpent();
		obstacles();
		genererRepas();
		
		button2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				dispose();
				new snake();
			}
		});
		
		//Implementation du clavier
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);

				if (e.getKeyCode() == KeyEvent.VK_DOWN && direction != 4) {
					direction = 2;
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT && direction != 3) {
					direction = 1;
				}
				if (e.getKeyCode() == KeyEvent.VK_LEFT && direction != 1) {
					direction = 3;
				}
				if (e.getKeyCode() == KeyEvent.VK_UP && direction != 2) {
					direction = 4;
				}
				
			}
		} );
		//Implementer la souris
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				
			}
		});
		
		notrePanel = new JPanel() {
			@Override
		public void paint(Graphics g) {
			// TODO Auto-generated method stub
			super.paint(g);
			Cellule C1 =new Cellule(0,0,Color.green);
			Cellule C2 =new Cellule(nbCasesX-1,nbCasesY-1,Color.red);
			//dessinerCellule(C1,g);
			//dessinerCellule(C2,g);
			//dessinerCellule(tete, g);
			dessinerMatrice(g);
			dessinerSnake(g);
			dessinerCellule(Repas, g);
			dessinerObstacle(g);
			
		}};
		
		horloge = new Timer(vitesse, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//deplacer le corps avant de deplacer la tete
				//serpent.get(serpent.size()-1).x = serpent.get(0).x;
				//serpent.get(serpent.size()-1).y = serpent.get(0).y;
				//le fait qu'on a pas generer le repas dans l'horloge revient a le fait qu;il va se generer a chaque instant

				for(int i =serpent.size()-1 ; i>=1 ;i--) {
					serpent.get(i).x = serpent.get(i-1).x;
					serpent.get(i).y = serpent.get(i-1).y;
				}
				
				//deplacer la tete
				if(direction == 1)
					serpent.get(0).x++;
				if(direction == 2)
					serpent.get(0).y++;
				if(direction == 3)
					serpent.get(0).x--;
				if(direction == 4)
					serpent.get(0).y--;
				
				//corriger la position de la tete pour rester dans la grille(dans la matrice)
				if(serpent.get(0).x == nbCasesX) serpent.get(0).x =0;
				if(serpent.get(0).x == -1) serpent.get(0).x =nbCasesX-1;
				if(serpent.get(0).y == nbCasesY) serpent.get(0).y =0;
				if(serpent.get(0).y == -1) serpent.get(0).y =nbCasesY-1;
				
				//genererRepas();
				//tester si le repas a ete mange ou non par la tete du serpent
				if(serpent.get(0).x == Repas.x && serpent.get(0).y == Repas.y) {
					genererRepas();
					serpent.add(new Cellule(serpent.get(serpent.size()-1).x, serpent.get(serpent.size()-1).x, CouleurCorps));
				}
				
				//pour les obstacles
				for(int i =0 ;i<obstacles.size()-1;i++)
					if(obstacles.get(i).x==serpent.get(0).x && obstacles.get(i).y==serpent.get(0).y) {
						horloge.stop();
						gameOver();
						}
				
				for(int i =1 ;i<serpent.size();i++)
					if(serpent.get(0).x==serpent.get(i).x && serpent.get(i).y==serpent.get(0).y )
						horloge.stop();
				repaint();
			}
		});
		horloge.start();
				
		this.setContentPane(notrePanel);
		this.setVisible(true);
	}
	
	void dessinerCellule(Cellule cel, Graphics g) {
		g.setColor(cel.couleur);
		g.fillRect(marge+cel.x*largeur, marge+cel.y*largeur, largeur, largeur);
	}
	void dessinerMatrice(Graphics g) {

		g.setColor(Couleurbg);
		g.fillRect(marge, marge, nbCasesX*largeur , nbCasesY*largeur);
		
		g.setColor(Color.gray);
		for (int i = 0; i<nbCasesX+1 ; i++) {
			g.drawLine(marge +i*largeur, marge, marge +i*largeur, marge+nbCasesY*largeur);			
		}
		for (int i = 0; i<nbCasesY+1 ; i++) {
			g.drawLine(marge , marge+i*largeur, marge+nbCasesX*largeur , marge+i*largeur);
			
		}
	}
	void dessinerSnake(Graphics g) {
		for(Cellule c : serpent)
			dessinerCellule(c, g);
	}
	void dessinerObstacle(Graphics g) {
		for(Cellule o : obstacles)
			dessinerCellule(o, g);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new snake();
	}
//	public void actionPerformed(ActionEvent e) {
//		if(e.getSource()==button2) {
//			dispose();
//			new snake();
//			System.out.println("hello ");
//		}
//		
//	}
	

}

class Cellule{
	int x,y;
	Color couleur;
	public Cellule(int x, int y, Color couleur) {
		super();
		this.x = x;
		this.y = y;
		this.couleur = couleur;
	}
	
	
}
