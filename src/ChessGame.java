import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;


public class ChessGame extends JFrame implements MouseListener {
private int blackcount=0;
private int whitecount=0;
private boolean flag=true;
private boolean whitewin;
private boolean blackwin;
private boolean flash=false;
public Timer time;
private ArrayList<mypoint>blacklist;
private ArrayList<mypoint>whitelist;
	public ChessGame(){
	
		setSize(450,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	blacklist=new ArrayList<mypoint>();
	whitelist=new ArrayList<mypoint>();
	Container contain=this.getContentPane();
	
	JPanel toolbar=new JPanel();//工具面板实例化  
	//toolbar.setOpaque(true);
    //三个按钮初始化  
    JButton start=new JButton("重新开始");  
    start.addActionListener(new ActionListener()
            {
                 public void actionPerformed(ActionEvent e)
                 {
                     reset();
                 }
             });
    
    JButton undo=new JButton("悔棋");  
    undo.addMouseListener(new java.awt.event.MouseAdapter(){
        public void mouseClicked(MouseEvent e) {//仅当鼠标单击时响应
          undo();
    }

     }); 
    //将工具面板按钮用FlowLayout布局  
    toolbar.setLayout(new FlowLayout(FlowLayout.LEFT));  
    //将三个按钮添加到工具面板  
    toolbar.add(start);  
    toolbar.add(undo);  
    start.doClick();
    add(toolbar,BorderLayout.SOUTH);  
		this.addMouseListener(this);
	
		setVisible(true);
		
	}
	
	public void undo(){//悔棋
		if(flag==true){
			flash=true;
			whitelist.remove(whitelist.size()-1);
			flag=false;
			repaint();
			
		}else{
			blacklist.remove(blacklist.size()-1);
			flag=true;
			flash=true;
			repaint();
			
		}
	}
	
	  public void reset(){
	    	whitelist=new ArrayList<mypoint>();
	    	blacklist=new ArrayList<mypoint>();
	    	flash=true;
	    	whitecount=0;
	    	blackcount=0;
	    	repaint();
	    }
	
	@Override
	
	public void paint(Graphics g) {
		if(flash==true){
			super.paint(g);
		}
		flash=false;
		  g.setColor(Color.WHITE);
		  g.fillRect(0, 0, 420, 420);
		  g.setColor(Color.DARK_GRAY);
		  g.drawLine(5, 5, 420, 5);
		  g.drawLine(5, 5, 5, 420);
		  g.drawLine(420, 5, 420, 420);
		  g.drawLine(5, 420, 420, 420);
		  for(int i=1;i<=21;i++){
			  g.drawLine(5, 20*i, 420, 20*i);
			  g.drawLine(20*i, 5, 20*i, 420);
		  }
		  
		
		  g.setColor(Color.BLACK);
		  
		  for(int i=0;i<blacklist.size();i++){
			  g.fillOval(blacklist.get(i).getX()*20+12,blacklist.get(i).getY()*20+32, 16, 16);
		  }
 g.setColor(Color.RED);
		  
		  for(int i=0;i<whitelist.size();i++){
			  g.fillOval(whitelist.get(i).getX()*20+12,whitelist.get(i).getY()*20+32, 16, 16);
		  }
		this.checkwin(blacklist, 0, 0, 0,0);
		this.checkwin(whitelist, 0, 0, 0,1);
		g.setColor(Color.black);
		String str1 = "黑方胜利局数为:" + blackcount;
		  g.drawString(str1, 10, 450);
		  String str2 = "红方胜利局数为:" + whitecount;
		  g.drawString(str2, 150, 450);
		
		 }
	public static void main(String[] args){
		ChessGame snake=new ChessGame();
		
	}

  public boolean checkExist(mypoint a){
	  for(int i=0;i<whitelist.size();i++){
		  if(whitelist.get(i).getX()==a.getX()&&whitelist.get(i).getY()==a.getY()){
			  return true;
		  }
	  }
	  for(int i=0;i<blacklist.size();i++){
		  if(blacklist.get(i).getX()==a.getX()&&blacklist.get(i).getY()==a.getY()){
			  return true;
		  }
	  }
	  return false;
  }

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		double x=e.getX();
		double y=e.getY();
		
		if(x>=5&&x<=410&&y>=5&&y<=410){
		double k=20;
		x=x/k-1;
		y=y/k-2;
	    int ax=(int) Math.round(x);
	    int ay=(int) Math.round(y);
	    if(checkExist(new mypoint(ax,ay))){
	    	JOptionPane.showMessageDialog(null, "该位置已有棋子！"); 
			repaint();
	    }else{
	    if(flag==true){
	    blacklist.add(new mypoint(ax,ay));
	    }
	    if(flag==false){
		    whitelist.add(new mypoint(ax,ay));
		    }
	    flag=!flag;
	   
	    repaint();
	    }
		}
	}

    public void checkwin(ArrayList<mypoint> list,int pos,int direct,int count,int type){
    	if(count==0){
    		for(int i=0;i<list.size();i++){
    			for(int j=0;j<list.size();j++){
    				
    					if(list.get(i).getY()==list.get(j).getY()&&(list.get(j).getX()-list.get(i).getX()==-1)){
    						direct=2;
    						count++;
    						checkwin(list,j,2,count,type);
    						count=0;
    					}
    					
    					if(list.get(i).getX()==list.get(j).getX()&&(list.get(j).getY()-list.get(i).getY()==-1)){
    						direct=0;
    						count++;
    						checkwin(list,j,0,count,type);
    						count=0;
    					  }
    					if(list.get(j).getY()==list.get(i).getY()-1&&(list.get(j).getX()-list.get(i).getX()==1)){
    						direct=4;
    						count++;
    		checkwin(list,j,4,count,type);
    						count=0;
    					}
    					if(list.get(j).getY()==list.get(i).getY()+1&&(list.get(j).getX()-list.get(i).getX()==1)){
    						direct=5;
    						count++;
    					
    						checkwin(list,j,5,count,type);
    						count=0;
    					}
    					
    		             	}
    	        	      }
    	
    	}
    	
    	
    	else{
    		if(direct==4){
    			for(int i=0;i<list.size();i++){
    				if(i!=pos){
    					if(list.get(i).getX()==list.get(pos).getX()+1&&(list.get(i).getY()-list.get(pos).getY()==-1)){
    						
    						count=count+1;
    						if(count==4){
    							if(type==0){
    							System.out.println("blackwin!");
    							blackcount++;
    							JOptionPane.showMessageDialog(null, "黑方赢了！"); 
    							restart();
    							}if(type==1){
    								System.out.println("whitewin!");
        							whitecount++;
        							JOptionPane.showMessageDialog(null, "红方赢了！"); 
        							restart();
    							}
    						}else{
    						checkwin(list,i,direct,count,type);
    						}
    					}
    				}
    			}
    			}
    		if(direct==5){
    			for(int i=0;i<list.size();i++){
    				if(i!=pos){
    					if(list.get(i).getX()==list.get(pos).getX()+1&&(list.get(i).getY()-list.get(pos).getY()==1)){
    						
    						count=count+1;
    						if(count==4){
    							if(type==0){
    							System.out.println("blackwin!");
    							blackcount++;
    							JOptionPane.showMessageDialog(null, "黑方赢了！"); 
    							restart();
    							}if(type==1){
    								System.out.println("whitewin!");
        							whitecount++;
        							JOptionPane.showMessageDialog(null, "红方赢了！"); 
        							restart();
    							}
    						}else{
    						checkwin(list,i,direct,count,type);
    						}
    					}
    				}
    			}
    			}
    		
			if(direct==0){
			for(int i=0;i<list.size();i++){
				if(i!=pos){
					if(list.get(pos).getX()==list.get(i).getX()&&(list.get(i).getY()-list.get(pos).getY()==-1)){
						
						count=count+1;
						if(count==4){
							if(type==0){
    							System.out.println("blackwin!");
    							blackcount++;
    							JOptionPane.showMessageDialog(null, "黑方赢了！"); 
    							restart();
    							}if(type==1){
    								System.out.println("whitewin!");
        							whitecount++;
        							JOptionPane.showMessageDialog(null, "红方赢了！"); 
        							restart();
    							}
    						}else{
    						checkwin(list,i,direct,count,type);
    						}
					}
				}
			}
			}
			
			if(direct==2){
    			for(int i=0;i<list.size();i++){
    				if(i!=pos){
    					if(list.get(pos).getY()==list.get(i).getY()&&(list.get(i).getX()-list.get(pos).getX()==-1)){
    						
    						count=count+1;
    						if(count==4){
    							if(type==0){
        							System.out.println("blackwin!");
        							blackcount++;
        							JOptionPane.showMessageDialog(null, "黑方赢了！"); 
        							restart();
        							}if(type==1){
        								System.out.println("whitewin!");
            							whitecount++;
            							JOptionPane.showMessageDialog(null, "红方赢了！"); 
            							restart();
        							}
        						}else{
        						checkwin(list,i,direct,count,type);
        						}
    					}
    				}
    			}
    			}
			
		
}
    	
    }
    
    
  
    public void restart(){
    	whitelist=new ArrayList<mypoint>();
    	blacklist=new ArrayList<mypoint>();
    	flash=true;
    	repaint();
    }

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
class mypoint{
	int x;
	int y;
	public mypoint(int a,int b){
		this.x=a;
		this.y=b;
	}
	public int getX(){
		return this.x;
	}
	public int getY(){
		return this.y;
	}
}