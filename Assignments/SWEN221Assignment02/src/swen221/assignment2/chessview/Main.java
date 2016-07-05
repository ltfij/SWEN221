package swen221.assignment2.chessview;

import java.util.*;

import swen221.assignment2.chessview.viewer.*;

import java.io.*;

public class Main {
	public static void main(String[] args) {
		
		try {
			ChessGame game = new ChessGame(new FileReader(args[0]));			
			new BoardFrame(game);
			//textView(game);
		} catch(IOException e) {			
			System.err.println("Error loading file: " + args[0]);
			System.err.println(e.getMessage());			
		}
	}
	
	public static void textView(ChessGame game) {
		try {			
			List<Board> boards = game.boards();
			List<Round> rounds = game.rounds();
			
			System.out.println(boards.get(0));
			
			for(int i=0;i!=rounds.size();++i) {
				System.out.println("\n==================");	
				Round r = rounds.get(i);
				System.out.println("WHITE PLAYS: " + r.white());				
				if(((2*i)+1) >= boards.size()) {
					throw new RuntimeException("Invalid move!");
				}
				Board wb = boards.get((2*i)+1);																			
				System.out.println(wb);
				if(r.black() != null) {
					System.out.println("\nBLACK PLAYS: " + r.black());
					if(((2*i)+2) >= boards.size()) {
						throw new RuntimeException("Invalid move!");
					}
					Board bb = boards.get((2*i)+2);
					System.out.println(bb);
				}				
			}			
			
		} catch(Exception e) {			
			e.printStackTrace(); // useful for debugging
		}
	}
}
