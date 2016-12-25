import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;
class Dot
{
	boolean e=false;
	int x,y;
	Dot(int x,int y)
	{
		this.x=x;
		this.y=y;
	}
}
public class PacDemos extends Frame implements Runnable,ActionListener
{
	String cmd="p1Start";
	long time1=0;
	long time2=0;
	String str="player 1's turn";
	String mode="p1";
	String pname;
	Color pcolor;
	int no_of_dots;
	TextField name,ndots;
	Choice color;
	Button p1start;
	Button p2start;
	Button result;
	TextArea controls;
	boolean started=false;
	String dir="right";
	int r=20;
	int x=100;
	int y=250;
	int theta=15;
	Set<Dot> dots;
	StopWatch s;
	StopWatch s1;
	PacDemos()
	{
		new Thread(this).start();
		addKeyListener(new MyKeyAdapter());
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we)
			{
				System.exit(0);
			}
		});
		dots=new HashSet<Dot>();
		s=new StopWatch();
		s1=new StopWatch();
	}
	public static void main(String[] args)
	{
		String usage="Controls:\nUp:w or Up_arrow\nLeft:a or Left_arrow\nDown:s or Down_arrow\nRight:d or Right_arrow";
		PacDemos pacman=new PacDemos();
		pacman.setLayout(new FlowLayout(FlowLayout.CENTER));
		pacman.name=new TextField("Player Name");
		pacman.ndots=new TextField("10");
		pacman.color=new Choice();
		pacman.color.add("red");
		pacman.color.add("green");
		pacman.p1start=new Button("p1Start");
		pacman.p2start=new Button("p2Start");
		pacman.result=new Button("result");
		pacman.controls=new TextArea(usage);
		pacman.controls.setEditable(false);
		pacman.add(pacman.name);
		pacman.add(new Label("No of dots:"));
		pacman.add(pacman.ndots);
		pacman.add(new Label("Dots Color:"));
		pacman.add(pacman.color);
		pacman.add(pacman.p1start);
		pacman.add(pacman.p2start);
		pacman.add(pacman.result);
		pacman.add(pacman.controls);
		pacman.p1start.addActionListener(pacman);
		pacman.p2start.addActionListener(pacman);
		pacman.result.addActionListener(pacman);
		pacman.pack();
		pacman.setVisible(true);
	}
	public void actionPerformed(ActionEvent ae)
	{
		java.util.Random random=new java.util.Random();
		cmd=ae.getActionCommand();
		if(cmd.equals("p1Start"))
		{
		//	removeAll();
			started=true;
			s.start();
			pname=name.getText();
			switch(color.getSelectedIndex())
			{
				case 0:
					pcolor=Color.red;
					break;
				case 1:
					pcolor=Color.green;
					break;
			}
			no_of_dots=Integer.parseInt(ndots.getText());
			for(int i=0;i<no_of_dots;i++)
				dots.add(new Dot(random.nextInt(1250)+58,random.nextInt(500)+220));
			setExtendedState(Frame.MAXIMIZED_BOTH);
			requestFocusInWindow();
			str="player 2's turn";
			repaint();
		}
		else if(cmd.equals("p2Start"))
		{
		//	removeAll();
			started=true;
			s1.start();
			pname=name.getText();
			try{  
//step1 load the driver class  
Class.forName("oracle.jdbc.driver.OracleDriver");  
  
//step2 create  the connection object  
Connection con=(Connection)DriverManager.getConnection(  
"jdbc:oracle:thin:@localhost:1521:orcl","c##scott","scott");  
  
//step3 create the statement object  
Statement stmt=con.createStatement();  
  
//step4 execute query  
stmt.executeUpdate("insert into emp(ename) values('"+pname+"')");  
//while(rs.next())  
//System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
  
//step5 close the connection object  
con.close();  
  
}catch(Exception e){ System.out.println(e);}  
			switch(color.getSelectedIndex())
			{
				case 0:
					pcolor=Color.red;
					break;
				case 1:
					pcolor=Color.green;
					break;
			}
			no_of_dots=Integer.parseInt(ndots.getText());
			for(int i=0;i<no_of_dots;i++)
				dots.add(new Dot(random.nextInt(1250)+58,random.nextInt(500)+220));
			setExtendedState(Frame.MAXIMIZED_BOTH);
			requestFocusInWindow();
			str="click result";
/*			if(time1>time2)
				str="player1 wins time taken="+(String.valueOf((int)time1));
			else if(time2>time1)
				str="player2 wins time taken="+(String.valueOf((int)time2));
			else
				str="equal";*/
			repaint();
		}
		else if(cmd.equals("result")){
			if(time1<time2)
				str="player1 wins time taken="+(String.valueOf((int)time1))+"seconds";
			else if(time2<time1)
				str="player2 wins time taken="+(String.valueOf((int)time2))+"seconds";
			repaint();
		}
		
	}
	public void paint(Graphics g) 
	{	
		g.setFont(new Font("Arial",Font.BOLD,50));
		g.setColor(Color.red);
		g.drawString(str,300,400);
		if(no_of_dots>0)
		{
			if(started)
			{
		//		s.start();
				g.clearRect(50,220,1300,510);
				g.setFont(new Font("Arial",Font.BOLD,50));
				g.setColor(Color.red);
				g.drawString(String.valueOf(no_of_dots),1300,100);
	//			g.drawString(String.valueOf((int)s.startTime),50,50);
				g.drawRect(50,220,1300,510);
				for(Dot i:dots)
				{
					if(!i.e)
					{
						g.setColor(pcolor);
						g.fillOval(i.x,i.y,10,10);
					}
				}
				g.setColor(Color.yellow);
				if(dir.equals("right"))
					g.fillArc(x,y,2*r,2*r,theta,360-(2*theta));
				else if(dir.equals("left"))
					g.fillArc(x,y,2*r,2*r,180-theta,-(360-(2*theta)));
				else if(dir.equals("up"))
					g.fillArc(x,y,2*r,2*r,90-theta,-(360-(2*theta)));
				else if(dir.equals("down"))
					g.fillArc(x,y,2*r,2*r,270+theta,(360-(2*theta)));	
			}
		}
		else
		{
			if(cmd.equals("p1Start")){s.stop();
			time1=s.getElapsedTimeSecs();}
			if(cmd.equals("p2Start")){s1.stop();
			time2=s1.getElapsedTimeSecs();}
//			str="player 2's turn";
//			g.setFont(new Font("Arial",Font.BOLD,100));
//			g.setColor(Color.red);
//			g.drawString("player 2's turn",400,400);
		}
	}
/*	synchronized*/ void eaten()
	{
		for(Dot i:dots)
			{
				if((Math.pow((i.x-(x+r)),2)+Math.pow((i.y-(y+r)),2))<(r*r)&&i.e==false)
				{
					i.e=true;
					no_of_dots--;
				}
			}
	}
	public void run()
	{
		int i=0;
		while(true)
		{
			if((i++)%2==0)
				theta=30;
			else
				theta=15;
			try{Thread.sleep(100);}catch(Exception e){}
		}
	}
	class MyKeyAdapter extends KeyAdapter
	{
		public void keyPressed(KeyEvent ke) 
		{
			int i=ke.getKeyCode();
			switch(i) 
			{
				case KeyEvent.VK_RIGHT:
				dir="right";
				if((x+2*r)<1350)
					x=x+10;
				break;
				case KeyEvent.VK_LEFT:
				dir="left";
				if(x>58)
					x=(x-10);
				break;
				case KeyEvent.VK_UP:
				dir="up";
				if(y>220)
					y=(y-10);
				break;
				case KeyEvent.VK_DOWN:
				dir="down";
				if((y+2*r)<730)
					y=(y+10);
				break;
			}
				eaten();
				repaint();
		}
		public void keyTyped(KeyEvent ke) 
		{
			char ch=ke.getKeyChar();
			switch(ch) 
			{
				case 'd':
				dir="right";
				if((x+2*r)<1300)
					x=x+10;
				break;
				case 'a':
				dir="left";
				if(x>58)
					x=(x-10);
				break;
				case 'w':
				dir="up";
				if(y>240)
					y=(y-10);
				break;
				case 's':
				dir="down";
				if((y+2*r)<730)
					y=(y+10);
				break;
			}
				eaten();
				repaint();
				
		}
	}
}
class StopWatch {

  long startTime = 0;
  long stopTime = 0;
  boolean running = false;


  public void start() {
    this.startTime = System.currentTimeMillis();
    this.running = true;
  }


  public void stop() {
    this.stopTime = System.currentTimeMillis();
    this.running = false;
  }


  //elaspsed time in milliseconds
  public long getElapsedTime() {
    long elapsed;
    if (running) {
      elapsed = (System.currentTimeMillis() - startTime);
    }
    else {
      elapsed = (stopTime - startTime);
    }
    return elapsed;
  }


  //elaspsed time in seconds
  public long getElapsedTimeSecs() {
    long elapsed;
    if (running) {
      elapsed = ((System.currentTimeMillis() - startTime) / 1000);
    }
    else {
      elapsed = ((stopTime - startTime) / 1000);
    }
    return elapsed;
  }
}