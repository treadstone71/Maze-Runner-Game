package mazeUi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MazeUi {
	
	mazeAlgo.Maze maz=new mazeAlgo.Maze();
	int penalty;
	private int xpos=0,ypos=0;
	public int vis[][]=new int[25][25];
	public JFrame mainMaze=new JFrame("Maze Runner Game");
	public JFrame location=new JFrame("Your Current Location");
	public JLabel lb=new JLabel("0 0",JLabel.CENTER);
	public JButton generate=new JButton("Generate Maze");
	public JButton info=new JButton("How to Play");
	public JButton giveUp=new JButton("Give Up");
	JButton label[][]=new JButton[25][25];
	
	MazeUi(){
		prepareGui();
	}
	
	public static void main(String[] args) {
		new MazeUi();
	}
	
	void prepareGui(){
		mainMaze.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				System.exit(0);
			}
		});
		mainMaze.setLayout(new FlowLayout());
		JPanel main1=new JPanel(new GridLayout(2,1));
		JPanel grid=new JPanel(new GridLayout(25,25));
		JPanel buttons=new JPanel(new FlowLayout());
		for(int i=0;i<25;i++){
			for(int j=0;j<25;j++){
				JButton jj=new JButton();
				jj.setBackground(Color.WHITE);
				jj.setPreferredSize(new Dimension(25,25));
				label[i][j]=jj;
				grid.add(label[i][j]);
			}
		}
		generate.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				xpos=0;
				ypos=0;
				penalty=0;
				maz.buildMaze();
				int v[]=new int[4];
				for(int i=0;i<25;i++){
					for(int j=0;j<25;j++){
						vis[i][j]=0;
						label[i][j].setBackground(Color.WHITE);
						label[i][j].setText("");
						for(int k=0;k<4;k++){
							if((maz.value[i][j]&(1<<k))==(1<<k)) v[k]=0;
							else v[k]=1;
						}
						label[i][j].setBorder(BorderFactory.createMatteBorder(v[3],v[2],v[1],v[0],Color.RED));
					}
				}
				calVisited(24,24);
				label[0][0].setBackground(Color.GREEN);
				label[24][24].setBackground(Color.BLUE);
			}
		});
		grid.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent key){
				label[xpos][ypos].setBackground(Color.WHITE);
				boolean wall;
				if(key.getKeyCode()==KeyEvent.VK_LEFT){
					wall=((maz.value[xpos][ypos])&(1<<2))==(1<<2);
					if(ypos-1>=0&&wall){
						ypos-=1;
					}
				}
				else if(key.getKeyCode()==KeyEvent.VK_UP){
					wall=((maz.value[xpos][ypos])&(1<<3))==(1<<3);
					if(xpos-1>=0&&wall){
						xpos-=1;
					}
				}
				else if(key.getKeyCode()==KeyEvent.VK_RIGHT){
					wall=((maz.value[xpos][ypos])&(1<<0))==(1<<0);
					if(ypos+1<=24&&wall){
						ypos+=1;
					}
				}
				else if(key.getKeyCode()==KeyEvent.VK_DOWN){
					wall=((maz.value[xpos][ypos])&(1<<1))==(1<<1);
					if(xpos+1<=24&&wall){
						xpos+=1;
					}
				}
				if(vis[xpos][ypos]==0){
					vis[xpos][ypos]=1;
					penalty++;
				}
				if(xpos==24&&ypos==24){
					String str=new String("Congrats\n"
							+ "You Won!!!!"+" \nwith penalty: "+penalty);
					JOptionPane.showMessageDialog(mainMaze,str);
				}
				lb.setText(""+xpos+", "+ypos);
				label[xpos][ypos].setBackground(Color.GREEN);
			}
		});
		
		info.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				String str=new String("Maze Runner is a Maze Solving Game\n"
						+ "Mazes are automatically generated when 'Generate Maze' Button is pressed\n"
						+ "The goal of this game is to move from Green cell to Blue cell using direction keys\n"
						+ "Green cel is where you currently are and Blue cell is your destination\n"
						+ "Red lines around a cell indicate walls\n"
						+ "You can move from one cell to another cel only if there is no wall between them\n"
						+ "You are won if you could reach the destination by above rules\n"
						+ "If you are tired of trying you can always press 'Give up' button to show the path\n"
						+ "Good Luck!!!!"); 
				JOptionPane.showMessageDialog(mainMaze,str);
			}
		});
		
		giveUp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				label[xpos][ypos].setBackground(Color.BLACK);
				label[0][0].setBackground(Color.GREEN);
				label[24][24].setBackground(Color.BLUE);
				printPath(24,24);
			}
		});
		
		grid.setFocusable(true);
		JPanel infopanel=new JPanel();
		infopanel.add(info);
		JPanel genpanel=new JPanel();
		genpanel.add(generate);
		JPanel giveuppanel=new JPanel();
		giveuppanel.add(giveUp);
		buttons.add(infopanel);
		buttons.add(genpanel);
		buttons.add(giveuppanel);
		main1.add(grid);
		main1.add(buttons);
		location.add(lb);
		location.pack();
		location.setVisible(true);
		mainMaze.add(main1);
		mainMaze.pack();
		mainMaze.setVisible(true);
	}
	
	void printPath(int i,int j){
		if(i==0&&j==0) 
			return;
		int id=maz.parent[i][j];
		int x=i+maz.dx[id];
		int y=j+maz.dy[id];
		label[x][y].setBackground(Color.GREEN);
		printPath(x,y);
	}
	
	void calVisited(int i,int j){
		vis[i][j]=1;
		if(i==0&&j==0)
			return;
		int id=maz.parent[i][j];
		int x=i+maz.dx[id];
		int y=j+maz.dy[id];
		calVisited(x,y);
	}
}


