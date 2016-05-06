package mazeAlgo;
import java.util.Random;

public class Maze {
	
	public boolean visit[][]=new boolean[25][25];
	public int value[][]=new int[25][25];
	public int dx[]={0,1,0,-1};
	public int dy[]={1,0,-1,0};
	public int parent[][]=new int[25][25];
	
	public void buildMaze(){
		for(int i=0;i<25;i++)
			for(int j=0;j<25;j++){
				visit[i][j]=false;
				value[i][j]=0;
			}
		parent[0][0]=-1;
		dfs(0,0);
	}
	
	boolean isOk(int x,int y){
		return x>=0&&y>=0&&x<25&&y<25;
	}
	
	public void dfs(int i,int j){
		Random randomGenerator = new Random();
		visit[i][j]=true;
		int v[]={0,0,0,0};
		int x,y,id;
		while(v[0]==0||v[1]==0||v[2]==0||v[3]==0){
			id=randomGenerator.nextInt(4);
			if(v[id]==0){
				x=i+dx[id];
				y=j+dy[id];
				v[id]=1;
				if(isOk(x,y) && !visit[x][y]){
					value[i][j]=(value[i][j]|(1<<id));
					value[x][y]=(value[x][y]|(1<<((id+2)%4)));
					parent[x][y]=(id+2)%4;
					dfs(x,y);
				}
			}
		}
	}
	
}

