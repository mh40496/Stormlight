/* BACK UP COPY: BEFORE ADJUSTING savePouch()
// Author: Marcus Hacker
// Test class for Spheres.java Class
*/

/*
TODO: 	* Add pouch spaces, like storage limitation e.g Can only have 4 pouches max.
		* Shake method, essentially scrambles the order of spheres in the list (not important).
		* Stormlight interactions.
*/

import java.util.Scanner;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.io.*;

public class pouchManagement {
	public static void main ( String args[] ) {
		menu();
	} // END of MAIN
	
	// Method to prompt the user a menu choices
	// PARAMETER: -
	// OUTPUT: -
	private static void menu() {
		ArrayList<Sphere> pouch = new ArrayList<Sphere>();
		boolean resume, stowed, havePouch;
		int choice;
		
		resume = true;
		stowed = false;
		havePouch = false;
		do {
			choice = usrInt( "[1] Add a sphere\n" +
							 "[2] Take out a sphere\n" +
							 "[3] Check the total value of the pouch\n" +
							 "[4] Count the spheres in the pouch\n" +
							 "[5] Pick up another pouch\n" +
							 "[6] Stow away current pouch\n" +
							 "[7] Rename currently held pouch\n" +
							 "[0] Continue on your way\n" + 
							 "You decide to: ");
			 switch( choice ) {
				case 1:
					if( havePouch ) {
						addSphere( pouch );
						stowed = false;
					} else
						System.out.println( "You are not holding a pouch..." );
					break;
					
				case 2:
					if( havePouch ) {
						if( pouch.size() != 0 )
							stowed = false;
						removeSphere( pouch );
					} else
						System.out.println( "You are not holding a pouch..." );
					break;
				
				case 3:
					if( havePouch ) {
						if( pouch.size() != 0 )
							System.out.println( "Total value of all spheres in the pouch is equal to " + checkValue( pouch ) + " clearchip(s)" );
						else
							System.out.println( "The pouch is empty..." );
					} else
						System.out.println( "You are not holding a pouch..." );
					break;
					
				case 4:
					if( havePouch ) {
						checkSpheres( pouch );
					} else
						System.out.println( "You are not holding a pouch..." );
					break;
				
				case 5:
					pickPouch( pouch );
					stowed = false;
					havePouch = true;
					break;
				
				case 6:
					if( havePouch ) {
						movePouchStowed();
						stowed = true;
					} else
						System.out.println( "You are not holding a pouch..." );
					break;

				case 7:
					if( havePouch ) {
						renameCurrentPouch();
					} else
						System.out.println( "You are not holding a pouch..." );
					break;
					
				case 8:
					newPouch();
					break;
					
				case 0:
					if( havePouch ) {
						if( !stowed )
							System.out.println( "You better stow the pouch first." );
						else
							resume = false;
					} else 
						resume = false;
					break;
				
				default:
					System.out.println( "Huh?" );
					break;
			 }
			 System.out.println(); // NEWLINE for appearance
		} while( resume );
	} // END of menu()
	
	// Method to prompt the user the parameter string and read user keyboard integer input. Will repeat until a valid input is detected.
	// PARAMETER: String msg
	// OUTPUT: Integer input
	private static int usrInt( String msg ) {
		Scanner sc = new Scanner(System.in);
		int input = 0;
		boolean invalid = true;
		
		do {
			try {
				System.out.print( msg );
				input = sc.nextInt();
				invalid = false; // Will exit loop if there are no errors
			} catch( Exception e ) {
				System.out.println( "###Input must be an Integer!###\nTry Again!" );
				sc.nextLine(); // Clears input, Stops infinite loop
			}
		} while( invalid );
		
		return input;
	} // END of usrInt()
	
	// Method to prompt the user the parameter string and read user keyboard input. Repeats while input is empty.
	// PARAMETER: String msg
	// OUTPUT: String input
	private static String usrString( String msg ) {
		Scanner sc = new Scanner( System.in );
		String input = "";
		
		do {
			System.out.print( msg );
			input = sc.nextLine();
			if ( input.length() == 0 )
				System.out.println( "###Input cannot be empty!###\nTry Again!" );
		} while( input.length() == 0 );
		
		return input;
	} // END of usrString()

	// Method to add a sphere element to the parameter array list.
	// PARAMETER: ArrayList<Sphere> pouch
	// OUTPUT: -
	private static void addSphere( ArrayList<Sphere> pouch ) {
		char gem, size;
		
		gem = usrGem( "You decide to add a sphere of the gem type ([D]iamond, [G]arnet, [R]uby, [S]apphire, [E]merald)\n\t> " );
			
		size = usrSize( "with a gem size of ([C]hip, [M]ark, [B]roam)\n\t> " );
		
		pouch.add( new Sphere( gem, size ) );
	}
	
	// Method specialised to allow only valid gem inputs.
	// PARAMETER: String msg
	// OUTPUT: Character gem
	private static char usrGem( String msg ) {
		char gem;
		
		do {
			gem = Character.toUpperCase( usrString( msg ).charAt(0) );
			if ( !( gem == 'D' || gem == 'G' || gem == 'R' || gem == 'S' || gem == 'E') )
				System.out.println( "There is no such gemstone!" );
		} while( !( gem == 'D' || gem == 'G' || gem == 'R' || gem == 'S' || gem == 'E') );
		
		return gem;
	} // END of usrGem
	
	// Method specialised to allow only valid gem size inputs.
	// PARAMETER: String msg
	// OUTPUT: Character size
	private static char usrSize( String msg ) {
		char size;
		
		do {
			size = Character.toUpperCase( usrString( msg ).charAt(0) );
			if ( !( size == 'C' || size == 'M' || size == 'B' ) )
				System.out.println( "There is no such gem size!" );
		} while( !( size == 'C' || size == 'M' || size == 'B' ) ) ;
		
		return size;
	} // END of usrSize
	
	// Method to check the total value of spheres in parameter array list.
	// PARAMETER: ArrayList<Sphere> pouch
	// OUTPUT: Integer total
	private static int checkValue( ArrayList<Sphere> pouch ) {
		int total = 0;
		
		for( int i = 0; i < pouch.size(); i++ ) {
			total += pouch.get(i).getValue();
		}
		
		return total;
	} // END of checkValue
	
	// Method to remove a sphere element from the parameter array list.
	// PARAMETER: ArrayList<Sphere> pouch
	// OUTPUT: -
	private static void removeSphere( ArrayList<Sphere> pouch ) {
		Sphere sp = new Sphere(); // Just for using the sphereDetails method in case the array list is empty after removing
		char gem, size;
		int amount, set;
		String finalMsg = "", takenGem;
		boolean noneFound = true;
		
		if( pouch.size() != 0 ) {
			checkSpheres( pouch );
			
			gem = usrGem( "Take out gem type ([D]iamond, [G]arnet, [R]uby, [S]apphire, [E]merald):\n\t> " );
			size = usrSize( "Of the gem size ([C]hip, [M]ark, [B]roam):\n\t> " );
			amount = usrInt( "Of the amount:\n\t> " );
			set = amount; // just to calculate amount taken if not fulfilled
			
			for( int i = 0; i < pouch.size(); i++ ) {
				if( pouch.get(i).getGem() == gem  && amount != 0 ) {
					if( pouch.get(i).getSize() == size ) {
						pouch.remove(i);
						amount--;
						if( amount == 0 )
							i = pouch.size(); // Cease any further iterations once amount is met
					}
				}
			}
			
			takenGem = sp.sphereDetails( gem, size );
			
			if ( amount == set ) // IF nothing changed 
				System.out.println( "There was no " + takenGem + " in your pouch" );
			else if( amount > 0 ) {
				finalMsg += "You take out only " + (set - amount) + " of the " + set + " " + takenGem;
				if( set > 1 )
					finalMsg += "s";
				System.out.println( finalMsg + " as there are no more left in the pouch..." );
			} else {
				System.out.print( "You took out " + set + " " + takenGem );
				if( set > 1 )
					System.out.print( "s" );
				System.out.println( "" );
			}
		} else
			System.out.println( "The pouch is empty..." );
	} // END of removeSphere
	
	// Method to check details of all sphere elements within the parameter array list.
	// PARAMETER: ArrayList<Sphere> pouch
	// OUTPUT: -
	private static void checkSpheres( ArrayList<Sphere> pouch ) {
		int[][] sphereCount = new int[5][3];
		String finalMsg = "";
		boolean moreThanOne = false;
		int i, j, plural = 0;
		
		if( pouch.size() != 0 ) {
			// Initialise all elements
			for( i = 0; i < 5; i++ ) { // gemstones
				for( j = 0; j < 3; j++ ) { // gem size
					sphereCount[i][j] = 0;
				}
			}
			
			for( i = 0; i < pouch.size(); i++ ) { // Count amount of same spheres
				switch( pouch.get(i).getGem() ) {
					case 'D':
						sphereCount[0][sizeSwitch( pouch.get(i).getSize() )]++;
						break;
						
					case 'G':
						sphereCount[1][sizeSwitch( pouch.get(i).getSize() )]++;
						break;
					
					case 'R':
						sphereCount[2][sizeSwitch( pouch.get(i).getSize() )]++;
						break;
					
					case 'S':
						sphereCount[3][sizeSwitch( pouch.get(i).getSize() )]++;
						break;
					
					case 'E':
						sphereCount[4][sizeSwitch( pouch.get(i).getSize() )]++;
						break;
				}
			}
			
			for( i = 0; i < 5; i++ ) { // gemstones
				for( j = 0; j < 3; j++ ) { // gem size
					if ( sphereCount[i][j] != 0 ) {
						if( plural > 0 )
							finalMsg += " and\n";
						finalMsg += sphereCount[i][j] + " " + sphereDetails( i, j );
						if( sphereCount[i][j] > 1 ) {
							finalMsg += "s"; // For correct grammar
							moreThanOne = true;
						}
						plural++;
					}
				}
			}
			
			if( finalMsg.length() != 0 ) {
				System.out.print( "There " );
				if ( moreThanOne || plural > 1 ) // For correct grammar
					System.out.print( "are" );
				else
					System.out.print( "is" );
				System.out.println( ":\n" + finalMsg + "\nin the pouch" );
			} 
		} else
			System.out.println( "The pouch is empty..." );
	} // END of checkSpheres()
	
	// Method specifically for the method checkSpheres(), avoids redundent code for switch case. Matches a character to element of gem size in the 2D array within checkSpheres() method.
	// PARAMETER: Character inSize
	// OUTPUT: Integer j
	private static int sizeSwitch( char inSize ) {
		int j = 0;
		
		switch( inSize ) {
			case 'C':
				j = 0;
				break;
			
			case 'M':
				j = 1;
				break;
			
			case 'B':
				j = 2;
				break;
		}
		
		return j;
	} // END of sizeSwitch

	// Method to expand on sphere details, within the given parameters. Specifically designed to work with checkSpheres() method.
	// PARAMETER: Integer i, Integer java
	// OUTPUT: String (thisGem + " " + thisSize)
	private static String sphereDetails( int i, int j ) {
		String thisGem, thisSize;
		
		thisGem = "";
		thisSize = "";
		
		switch( i ) {
			case 0:
				thisGem = "Diamond";
				break;
				
			case 1:
				thisGem = "Garnet";
				break;
			
			case 2:
				thisGem = "Ruby";
				break;
			
			case 3:
				thisGem = "Sapphire";
				break;
			
			case 4:
				thisGem = "Emerald";
				break;
		}
		
		switch( j ) {
			case 0:
				thisSize = "Chip";
				break;
			
			case 1:
				thisSize = "Mark";
				break;
			
			case 2:
				thisSize = "Broam";
				break;
		}
		
		return( thisGem + " " + thisSize );
	} // END of sphereDetails
	
	// Method to 'pick up another pouch', as in extract valid data from a file into an array list to then use. Will ask user to 'stow away', as in save the current data before opening a file.
	// PARAMETER: ArrayList<Sphere> pouch
	// OUTPUT: -
	private static void pickPouch( ArrayList<Sphere> pouch ) {
		Scanner sc = null;
		String filename;
		StringTokenizer tok;
		Sphere sp = new Sphere();
		char choice, gem, size, chk;
		int i;
		boolean valid, cancel;
		String tmp, address;
		
		gem = 'a';
		size = 'a';
		cancel = false;
		
		choice = 'a';
		while( !(choice == 'S' || choice == 'C' || choice == 'N') && pouch.size() != 0 ){
			choice = Character.toUpperCase( usrString( 	"Would you like to:\n" + 
														"[S]tow the current pouch before interacting with another pouch\n" + 
														"[C]ombine it with the current pouch\n" + 
														"Ca[N]cel\n\t> " ).charAt(0) );
			if( choice == 'S' ) {
				savePouchPrompt( pouch );
				pouch.clear(); // Removes every element in list
			} else if( choice == 'C' )
				System.out.print( "" ); // Do nothing
			else if ( choice == 'N' )
				cancel = true;
			else
				System.out.println( "Huh?" );
		} 
		
		if( !cancel ) {
			filename = usrString( "What pouch would you like to look at?\n\t> " );
			address = "Pouches\\stowed\\" + filename + ".txt";
			try {
				sc = new Scanner( new File( address ) );
				
				while( sc.hasNextLine() ) {
					tok = new StringTokenizer( sc.nextLine(), "," ); // Seperate by comma(,)
					i = 0; // Reset to 0 for the nextLine iteration
					valid = true; // Reset to true for the nextLine iteration
					while( tok.hasMoreTokens() && valid ) { // Loops if more tokens exist AND the tokens are valid
						tmp = tok.nextToken();
						if( tmp.length() == 1 ) {
							chk = tmp.charAt(0);
							if( i < 2 ) {
								if( i == 0 ) {
									if ( sp.validGem( chk ) )
										gem = chk;
									else
										valid = false; // Don't bother continuing if first data is invalid
								} else if ( i == 1 ) {
									if ( sp.validSize( chk ) )
										size = chk;
								}
							}
						} else
							valid = false;
						i++;
					}
				
					if( valid && i == 2 ) // i == 2 to ignore invalid lines
						pouch.add( new Sphere( gem, size ) );
				}
				
				sc.close();
				
				if( choice == 'C' ) {
					clearFile( filename ); // Clear contents of second pouch when combining
				} else
					movePouchOpened( filename ); // Move open pouch
				
			} catch( Exception e ) {
				System.out.println( "ERROR: " + e );
			}
		}
	}
	
	// Method essentially moves the stowed file of the parameter filename into 'opened' folder.
	// PARAMETER: String filename
	// OUTPUT: -
	private static void movePouchOpened( String filename ) {
		File toOpen = new File( "Pouches\\stowed\\" + filename + ".txt" );
		toOpen.renameTo( new File( "Pouches\\opened\\" + filename + ".txt" ) );
	}
	
	// Method essentially moves the opened file into stowed folder.
	// PARAMETER: -
	// OUTPUT: -
	private static void movePouchStowed() {
		File folder = new File( "Pouches\\opened");
		openedFilename = folder.listFiles()[0].getName();
		
		File toStow = new File( "Pouches\\opened\\" + openedFilename );
		toStow.renameTo( new File( "Pouches\\stowed\\" + openedFilename ) );
	}
	
	// Method to prompt user for filename to write list into, calls savePouch()
	// PARAMETER: ArrayList<Sphere> pouch
	// OUTPUT: -
	private static void savePouchPrompt ( ArrayList<Sphere> pouch ) {
		String filename;
		
		filename = usrString( "What would you like to name your pouch?\n\t> " );
		savePouch( pouch, filename );
	}
	
	// Method to clean a file of any invalid data, only works with file currently in 'opened' folder.
	// PARAMETER: ArrayList<Sphere> pouch, String filename
	// OUTPUT: -
	private static void savePouch( ArrayList<Sphere> pouch, String filename ) {
		PrintWriter pw = null;
		boolean resume = true;
		char choice;
		String stowAddress, openAddress;
		
		openAddress = "Pouches\\opened\\" + filename + ".txt";
		stowAddress = "Pouches\\stowed\\" + filename + ".txt";
		
		try {
			if ( new File( stowAddress ).exists() ) {
				do {
					System.out.println( "A pouch with the same name already exists!" );
					choice = Character.toUpperCase( usrString( "Would you like to call this pouch '" + filename + "' instead? (Y/N)\n\t> " ).charAt(0) );
					if( choice == 'Y' )
						resume = true;
					else if( choice == 'N' )
						resume = false;
					else
						System.out.println( "Huh?" );
				} while( !(choice == 'Y' || choice == 'N') );
			}
			
			if( resume ) {
				pw = new PrintWriter( new File( stowAddress ) );
				for( int i = 0; i < pouch.size(); i++ ) {
					pw.println( pouch.get(i).getGem() + "," + pouch.get(i).getSize() );
				}
			
				if( pouch.size() != 0 ) {
					System.out.println( "You called the pouch '" + filename + "' and stowed it away." );
				} else
					System.out.println( "You called the empty pouch '" + filename + "' and stowed it away." );
			
				pw.close();
				File open = new File( openAddress );
				open.delete(); // Remove file in 'opened' folder
				pouch.clear(); // Clear the list
			}
		} catch( Exception e ) {
			System.out.println( "ERROR: " + e );
		}
	} // END of savePouch
	
	// Method to clear file of the name of parameter String filename of any data.
	// PARAMETER: String filename
	// OUTPUT: -
	private static void clearFile( String filename ) {
		PrintWriter pw = null;
		
		try {
			pw = new PrintWriter( new File( "Pouches\\stowed\\" + filename + ".txt" ) );
			pw.print( "" );
			pw.close();
		} catch( Exception e ) {
			System.out.println( "ERROR: " + e );
		}
	} // END of clearFile
	
	// Method to rename the file associated with the currently active pouch
	// PARAMETER: -
	// OUTPUT: -
	private static void renameCurrentPouch() {
		String newName, currentName;
		
		newName = usrString( "What are you going to rename this pouch to?\n\t> " );
		
		File folder = new File( "Pouches\\opened");
		currentName = folder.listFiles()[0].getName();
		
		File currentFile = new File( "Pouches\\opened\\" + currentName );
		currentFile.renameTo( new File( "Pouches\\opened\\" + newName + ".txt") );
	}
} // END of CLASS