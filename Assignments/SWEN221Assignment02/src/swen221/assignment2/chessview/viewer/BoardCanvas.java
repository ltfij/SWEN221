package swen221.assignment2.chessview.viewer;

import java.awt.*;
import java.util.*;
import java.util.List;

import swen221.assignment2.chessview.*;
import swen221.assignment2.chessview.pieces.*;

@SuppressWarnings("serial")
public class BoardCanvas extends Canvas {
	private ArrayList<Board> boards;
	private int index = 0;
	private static final Color BLACK = new Color(90,48,158);
	private static final Color WHITE = new Color(210,205,185);
	private Font font;
	
	private static String[] preferredFonts = {"Arial","Times New Roman"};
	
	public BoardCanvas(List<Board> boards) {
		this.boards = new ArrayList<Board>(boards);
		setBounds(0, 0, 400, 400);
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();		
		HashSet<String> availableNames = new HashSet<>();
		
		for(String name : env.getAvailableFontFamilyNames()) {
			availableNames.add(name);			
		}
		
		for(String pf : preferredFonts) {
			if(availableNames.contains(pf)) {
				font = new Font(pf,Font.PLAIN,20);
				break;
			}
		}
	}
	
	public void paint(Graphics g) {		
		int width = (getWidth()-4)/9;
		int height = (getHeight()-4)/9;
		if(font != null) {
			g.setFont(font);
		}
		g.setColor(Color.WHITE);
		g.fillRect(0,0,getWidth(),getHeight());		
		drawBackground(g,width+2,2,width,height);
		drawCoords(g,2,2,width,height);
		drawPieces(g,width+2,2,width,height);
	}
	
	private void drawPieces(Graphics g, int startx, int starty, int width, int height) {
		for(int row=1;row<=8;++row) {
			int ypos = starty + ((8-row)*height);
			for(int col=1;col<=8;++col) {
				int xpos = startx + ((col-1)*width);
				Piece p = boards.get(index).pieceAt(new Position(row,col));
				if(p != null) {									
					drawPiece(p,g,xpos,ypos,width,height);
				}
			}	
		}
	}
	
	private void drawPiece(Piece p, Graphics g, int startx, int starty, int width, int height) {
		if(p.isWhite()) {
			g.setColor(Color.WHITE);
		} else {
			g.setColor(Color.BLACK);
		}
		
		width -= 10;
		height -= 10;
		
		startx += 5;
		starty += 5;
		int endx = startx + width;
		int endy = starty + height;		
		
		if(p instanceof Pawn) {
			int lmidx = startx + width/3;		
			int rmidx = endx - (width/3);
			int topy = starty + (height/3);
			int boty = starty + 2*(height/3);
			int[] xpoints = new int[]{lmidx,rmidx,rmidx,endx,startx,lmidx};
			int[] ypoints = new int[]{topy,topy,boty,endy,endy,boty};
			g.fillPolygon(xpoints,ypoints,xpoints.length);
			int lx = startx + width/5;		
			int rx = endx - (width/5);			
			g.fillOval(lx, starty, rx-lx, height/2);
			g.fillRect(startx, endy-(height/5), width, height/5);
		} else if(p instanceof Knight) {			
			int midy = starty + height/3;
			int tmidy = midy - height/4;
			int bmidy = midy + height/4;
			int midx = startx + width/2;
			int lmidx = startx + width/3;		
			int llmidx = startx + (3*width)/12;
			int rmidx = endx - (width/3);
			int[] xpoints = new int[]{startx,lmidx,midx,endx,rmidx,lmidx,midx,llmidx};
			int[] ypoints = new int[]{midy,tmidy,starty,tmidy,endy,endy,midy,bmidy};
			g.fillPolygon(xpoints,ypoints,xpoints.length);			
			g.fillRect(startx, endy-(height/5), width, height/5);
		} else if(p instanceof Rook) {
			int lmidx = startx + width/6;		
			int llmidx = startx + (2*width)/6;
			int rmidx = endx - (width/6);
			int rrmidx = endx - (2*width)/6;	
			int topy = starty + (height/5);					
			int midy = starty + (height/3);
			int[] xpoints = new int[] { startx, lmidx, lmidx, llmidx, llmidx,
					rrmidx, rrmidx, rmidx, rmidx, endx, endx, rmidx, rmidx, lmidx,	
					lmidx, startx };
			int[] ypoints = new int[] { starty, starty, topy, topy, starty,
					starty, topy, topy, starty, starty, midy, midy, endy, endy, midy, midy };
			g.fillPolygon(xpoints,ypoints,xpoints.length);
			g.fillRect(startx, endy-(height/5), width, height/5);
		} else if(p instanceof Bishop) {
			int lx = startx + width/5;		
			int rx = endx - (width/5);			
			g.fillOval(lx, starty, rx-lx, height);
			g.fillRect(startx, endy-(height/5), width, height/5);
		} else if(p instanceof Queen) {
			int lmidx = startx + width/3;
			int llmidx = startx + width/5;
			int midx = startx + width/2;
			int rmidx = endx - width/3;
			int rrmidx = endx - width/5;
			int topy = starty + (height/2);					
			int[] xpoints = new int[] { startx, lmidx,midx,rmidx,endx,rrmidx,llmidx};
			int[] ypoints = new int[] { starty, topy, starty,topy,starty,endy,endy};
			g.fillPolygon(xpoints,ypoints,xpoints.length);
			g.fillRect(startx, endy-(height/5), width, height/5);
		} else {			
			int leftx = startx + width/5;
			int midx = startx + width/2;
			int rightx = endx - width/5;
			int topy = starty + height/5;			
			int midy = starty + height/2;
			
			g.fillRect(leftx,topy,rightx-leftx,height/5);
			g.fillRect(midx-(width/10),starty,width/5,2*height/3);
			g.fillOval(leftx, midy, rightx-leftx, endy-midy);
			g.fillRect(startx, endy-(height/5), width, height/5);
		} 
	}
	
	private void drawCoords(Graphics g, int startx, int starty, int width, int height) {
		g.setColor(BLACK);		
				 		
		FontMetrics metrics = g.getFontMetrics();
		
		int Aoff = (width - metrics.charWidth('A')) / 2;
		int Boff = (width - metrics.charWidth('B')) / 2;
		int Coff = (width - metrics.charWidth('C')) / 2;
		int Doff = (width - metrics.charWidth('D')) / 2;
		int Eoff = (width - metrics.charWidth('E')) / 2;
		int Foff = (width - metrics.charWidth('F')) / 2;
		int Goff = (width - metrics.charWidth('G')) / 2;
		int Hoff = (width - metrics.charWidth('H')) / 2;			
		int Oneoff = (width - metrics.charWidth('1')) / 2;
		int Twooff = (width - metrics.charWidth('2')) / 2;
		int Threeoff = (width - metrics.charWidth('3')) / 2;
		int Fouroff = (width - metrics.charWidth('4')) / 2;
		int Fiveoff = (width - metrics.charWidth('5')) / 2;
		int Sixoff = (width - metrics.charWidth('6')) / 2;
		int Sevenoff = (width - metrics.charWidth('7')) / 2;
		int Eightoff = (width - metrics.charWidth('8')) / 2;
		
		starty += metrics.getAscent() + (height-metrics.getHeight())/2;
		
		g.drawChars(new char[]{'1'},0,1,startx+Oneoff,starty+(height*7));
		g.drawChars(new char[]{'2'},0,1,startx+Twooff,starty+(height*6));
		g.drawChars(new char[]{'3'},0,1,startx+Threeoff,starty+(height*5));
		g.drawChars(new char[]{'4'},0,1,startx+Fouroff,starty+(height*4));
		g.drawChars(new char[]{'5'},0,1,startx+Fiveoff,starty+(height*3));
		g.drawChars(new char[]{'6'},0,1,startx+Sixoff,starty+(height*2));
		g.drawChars(new char[]{'7'},0,1,startx+Sevenoff,starty+(height));
		g.drawChars(new char[]{'8'},0,1,startx+Eightoff,starty);
		
		starty += (height*8);
		g.drawChars(new char[]{'A'},0,1,startx+Aoff+width,starty);
		g.drawChars(new char[]{'B'},0,1,startx+Boff+(width*2),starty);
		g.drawChars(new char[]{'C'},0,1,startx+Coff+(width*3),starty);
		g.drawChars(new char[]{'D'},0,1,startx+Doff+(width*4),starty);
		g.drawChars(new char[]{'E'},0,1,startx+Eoff+(width*5),starty);
		g.drawChars(new char[]{'F'},0,1,startx+Foff+(width*6),starty);
		g.drawChars(new char[]{'G'},0,1,startx+Goff+(width*7),starty);
		g.drawChars(new char[]{'H'},0,1,startx+Hoff+(width*8),starty);		
	}
	
	private void drawBackground(Graphics g, int startx, int starty, int width, int height) {
		int endx = startx + (8*width);
		int endy = starty + (8*height);
		boolean flag = false;
		for(int y=starty;y!=endy;y=y+height) {
			for(int x=startx;x!=endx;x=x+width) {			
				flag = !flag;
				if(flag) {
					g.setColor(BLACK);
				} else {
					g.setColor(WHITE);
				}
				g.fillRect(x,y,width,height);
			}
			flag = !flag;
		}
	}	
	
	public void fwd(int amount) {
		index = Math.min(boards.size()-1,index + amount);
		repaint();		
	}
	
	public void bwd(int amount) {
		index = Math.max(0,index - amount);
		repaint();		
	}	
}
