

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Timer;
import javax.swing.JOptionPane;

public class GameEngine implements KeyListener, GameReporter{
	GamePanel gp;
		
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();	
	private SpaceShip v;	
	private SpaceShip2 v1;	

	
	private Timer timer;
	
	private long score = 0;
	private double difficulty = 0.1;
	
	Diff diff = new Diff();

	
	public GameEngine(GamePanel gp, SpaceShip v, SpaceShip2 v1) {
		this.gp = gp;
		this.v = v;		
		this.v1 = v1;

		gp.sprites.add(v);
		gp.sprites.add(v1);

		timer = new Timer(50, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				process();
			}
		});
		timer.setRepeats(true);
		
	}
	
	public void start(){
		timer.start();
		
	}
	
	private void generateEnemy(){
		Enemy e = new Enemy((int)(Math.random()*780), 30);
		gp.sprites.add(e);
		enemies.add(e);

	
	}
	
	private void process(){

		if(Math.random() < difficulty){
			generateEnemy();
		}

		
		Iterator<Enemy> e_iter = enemies.iterator();
		while(e_iter.hasNext()){
			Enemy e = e_iter.next();
			e.proceed();
		
			if(!e.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e);
				score += 100;
				difficulty += diff.getDiff();
				// difficulty += 0.1;
			}
			
		}
	
		gp.updateGameUI(this);
		
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double vr1 = v1.getRectangle();
		Rectangle2D.Double er;
		for(Enemy e : enemies){
			er = e.getRectangle();
			if(er.intersects(vr) || er.intersects(vr1)){
				die();
				return;
			}
		
			
		}
	}
	
	public void die(){
		timer.stop();
		JOptionPane.showMessageDialog(null, "Game Over  !!", "Results",JOptionPane.INFORMATION_MESSAGE );
	
	}
	
	void controlVehicle(KeyEvent e) { 
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			v1.move(-1);
			break;
		case KeyEvent.VK_RIGHT:
			v1.move(1);
			break;
		// case KeyEvent.VK_D:
		// 	difficulty += 0.1;
		// 	break;
		case KeyEvent.VK_A:
			v.move(-1);
			break;
		case KeyEvent.VK_S:
			v.move(1);
			break;
		}
	}

	public long getScore(){
		return score;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		controlVehicle(e);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//do nothing
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//do nothing		
	}
}
