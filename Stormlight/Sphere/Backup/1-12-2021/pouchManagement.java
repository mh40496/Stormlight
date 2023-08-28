/*
// Author: Marcus Hacker
// Test class for Spheres.java Class
*/

/*
TODO: 	
		* Make address related parameters more streamline, e.g filename and where should just be single String address instead
		* Implement a shop system
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
		char choice;
		
		resume = true;
		stowed = false;
		havePouch = false;
		
		do {
			choice = usrString( "[1] Add a sphere\n" +
							 "[2] Take out a sphere\n" +
							 "[3] Check the total value of the pouch\n" +
							 "[4] Count the spheres in the pouch\n" +
							 "[5] Pick up a stowed pouch\n" +
							 "[6] Stow away current pouch\n" +
							 "[7] Rename currently held pouch\n" +
							 "[8] Get a new pouch\n" +
							 "[9] Check stowed pouches\n" +
							 "[Q] Hang the pouch\n" +
							 "[W] Check hung pouch(es)\n" +
							 "[E] Take a deep breath\n" +
							 "[R] Summon a highstorm\n" +
							 "[T] Stow hung pouch(es)\n" +
							 "[0] Continue on your way\n" + 
							 "You decide to: ").charAt(0);
							 
			 if( choice > 96 && choice < 123 )
				 choice = Character.toUpperCase( choice );
			 
			 switch( choice ) {
				case '1':
					if( havePouch ) {
						addSphere( pouch );
						stowed = false;
					} else
						System.out.println( "You are not holding a pouch..." );
					break;
					
				case '2':
					if( havePouch ) {
						if( pouch.size() != 0 )
							stowed = false;
						removeSphere( pouch );
					} else
						System.out.println( "You are not holding a pouch..." );
					break;
				
				case '3':
					if( havePouch ) {
						if( pouch.size() != 0 )
							System.out.println( "Total value of all spheres in the pouch is equal to " + checkValue( pouch ) + " clearchip(s)" );
						else
							System.out.println( "The pouch is empty..." );
					} else
						System.out.println( "You are not holding a pouch..." );
					break;
					
				case '4':
					if( havePouch ) {
						checkSpheres( pouch );
					} else
						System.out.println( "You are not holding a pouch..." );
					break;
				
				case '5':
					checkFolderFiles( "stowed" );
					if( pickPouchPrompt( pouch ) ) {
						stowed = false;
						havePouch = true;
					}
					break;
				
				case '6':
					if( havePouch ) {
						movePouchStowed( pouch );
						stowed = true;
						havePouch = false;
					} else
						System.out.println( "You are not holding a pouch..." );
					break;

				case '7':
					if( havePouch ) {
						renameCurrentPouch();
					} else
						System.out.println( "You are not holding a pouch..." );
					break;
					
				case '8':
					newPouchPrompt();
					break;
					
				case '9':
					checkFolderFiles( "stowed" );
					break;
					
				case 'Q':
					if( havePouch ) {
						hangPouch( pouch );
						havePouch = false;
					} else
						System.out.println( "You are not holding a pouch..." );
					break;
					
				case 'W':
					checkFolderFiles( "hung" );
					break;
					
				case 'E':
					takeBreath( pouch );
					break;
					
				case 'R':
					summonStorm();
					break;
					
				case 'T':
					retrieveHung();
					break;	
					
				case '0':
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
		char gem, size, glow;
		File folder = new File( "Pouches\\opened");
		File[] files = folder.listFiles();
		String filename;
		
		filename = files[0].getName(); // Should only be one file in opened folder at a time
		
		gem = usrGem( "You decide to add a sphere of the gem type ([D]iamond, [G]arnet, [R]uby, [S]apphire, [E]merald)\n\t> " );
			
		size = usrSize( "with a gem size of ([C]hip, [M]ark, [B]roam)\n\t> " );
		
		glow = usrInfuse( "and the sphere is infused with stormlight([T]rue/[F]alse)\n\t> " );
		
		pouch.add( new Sphere( gem, size, glow ) );
		alterFile( pouch, "opened\\" + filename );
	}
	
	// Method specialised to allow only valid gem inputs.
	// PARAMETER: String msg
	// OUTPUT: Character gem
	private static char usrGem( String msg ) {
		Sphere sp = new Sphere();
		char gem;
		
		do {
			gem = Character.toUpperCase( usrString( msg ).charAt(0) );
			if ( !sp.validGem( gem ) )
				System.out.println( "There is no such gemstone!" );
		} while( !sp.validGem( gem ) );
		
		return gem;
	} // END of usrGem
	
	// Method specialised to allow only valid gem size inputs.
	// PARAMETER: String msg
	// OUTPUT: Character size
	private static char usrSize( String msg ) {
		Sphere sp = new Sphere();
		char size;
		
		do {
			size = Character.toUpperCase( usrString( msg ).charAt(0) );
			if ( !sp.validSize( size ) )
				System.out.println( "There is no such gem size!" );
		} while( !sp.validSize( size ) ) ;
		
		return size;
	} // END of usrSize
	
	// Method specialised to allow only valid sphere infused inputs.
	// PARAMETER: String msg
	// OUTPUT: Character size
	private static char usrInfuse( String msg ) {
		Sphere sp = new Sphere();
		char infuse;
		
		do {
			infuse = Character.toUpperCase( usrString( msg ).charAt(0) );
			if ( !sp.validInfused( infuse ) )
				System.out.println( "Is the sphere infused or not?" );
		} while( !sp.validInfused( infuse ) ) ;
		
		return infuse;
	}
	
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
		File folder = new File( "Pouches\\opened");
		File[] files = folder.listFiles();
		String filename;
		
		if( files.length > 0 ) {
			filename = files[0].getName(); // Should only be one file in opened folder at a time
			
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
						alterFile( pouch, "opened\\" + filename );
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
		}
	} // END of removeSphere
	
	// Method to check details of all sphere elements within the parameter array list.
	// PARAMETER: ArrayList<Sphere> pouch
	// OUTPUT: -
	private static void checkSpheres( ArrayList<Sphere> pouch ) {
		int[][][] sphereCount = new int[5][3][2];
		String finalMsg = "";
		boolean moreThanOne = false;
		int i, j, x, count, plural;
		
		count = 0;
		plural = 0;
		
		if( pouch.size() != 0 ) {
			// Initialise all elements
			for( i = 0; i < 5; i++ ) { // gemstones
				for( j = 0; j < 3; j++ ) { // gem size
					for( x = 0; x < 2; x++ ) { // gem infusion
						sphereCount[i][j][x] = 0;
					}
				}
			}
			
			for( i = 0; i < pouch.size(); i++ ) { // Count amount of same spheres
				switch( pouch.get(i).getGem() ) {
					case 'D':
						sphereCount[0][sizeSwitch( pouch.get(i).getSize() )][infuseSwitch( pouch.get(i).getInfused() )]++;
						count++;
						break;
						
					case 'G':
						sphereCount[1][sizeSwitch( pouch.get(i).getSize() )][infuseSwitch( pouch.get(i).getInfused() )]++;
						count++;
						break;
					
					case 'R':
						sphereCount[2][sizeSwitch( pouch.get(i).getSize() )][infuseSwitch( pouch.get(i).getInfused() )]++;
						count++;
						break;
					
					case 'S':
						sphereCount[3][sizeSwitch( pouch.get(i).getSize() )][infuseSwitch( pouch.get(i).getInfused() )]++;
						count++;
						break;
					
					case 'E':
						sphereCount[4][sizeSwitch( pouch.get(i).getSize() )][infuseSwitch( pouch.get(i).getInfused() )]++;
						count++;
						break;
				}
			}
			
			for( i = 0; i < 5; i++ ) { // gemstones
				for( j = 0; j < 3; j++ ) { // gem size
					for( x = 0; x < 2; x++ ) { // gem infusion
						if ( sphereCount[i][j][x] != 0 ) {
							if( plural > 0 )
								finalMsg += " and\n";
							
							finalMsg += sphereCount[i][j][x] + " " + sphereDetails( i, j, x );
							if( sphereCount[i][j][x] > 1 ) {
								finalMsg += "s"; // For correct grammar
								moreThanOne = true;
							}
							
							count -= sphereCount[i][j][x]; // Keeps track of how many remain, only for aesthetic purpose
							if( count > 0 )
								finalMsg +=( "\n" ); // New line
						}
					}
				}
			}
			
			if( finalMsg.length() > 0 ) {
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
	
	// Method specifically for the method checkSpheres(), avoids redundent code for switch case. Matches a character to element of gem size in the array within checkSpheres() method.
	// PARAMETER: Character inFuse
	// OUTPUT: Integer x
	private static int infuseSwitch( char inFuse ) {
		int x = 0;
		
		switch( inFuse ) {
			case 'F':
				x = 0;
				break;
			
			case 'T':
				x = 1;
				break;
		}
		
		return x;
	} // END of infuseSwitch

	// Method to expand on sphere details, within the given parameters. Specifically designed to work with checkSpheres() method.
	// PARAMETER: Integer i, Integer j, Integer x
	// OUTPUT: String ( thisInfused + " " + thisGem + " " + thisSize)
	private static String sphereDetails( int i, int j, int x ) {
		String thisGem, thisSize, thisInfused;
		
		thisGem = "";
		thisSize = "";
		thisInfused = "";
		
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
		
		switch( x ) {
			case 0:
				thisInfused = "Dun";
				break;
			
			case 1:
				thisInfused = "Infused";
				break;
		}
		
		return( thisInfused + " " + thisGem + " " + thisSize );
	} // END of sphereDetails
	
	// Method to 'pick up another pouch', as in extract valid data from a file into an array list to then use. Will ask user to 'stow away', as in save the current data before opening a file, if pouch is not empty.
	// PARAMETER: ArrayList<Sphere> pouch, String location
	// OUTPUT: -
	private static void pickPouch( ArrayList<Sphere> pouch, String location ) {
		Scanner sc = null;
		StringTokenizer tok;
		Sphere sp = new Sphere();
		char gem, size, infused, chk;
		int i;
		boolean valid;
		String tmp, address;
		File fail;
		
		gem = 'a';
		size = 'a';
		infused = 'a';
		
		address = "Pouches\\" + location;
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
						if( i < 3 ) {
							if( i == 0 ) {
								if ( sp.validGem( chk ) )
									gem = chk;
								else
									valid = false; // Don't bother continuing if first data is invalid
							} else if ( i == 1 ) {
								if ( sp.validSize( chk ) )
									size = chk;
							} else if ( i == 2 ) {
								if ( sp.validInfused( chk ) )
									infused = chk;
							}
						}
					} else
						valid = false;
					i++;
				}
			
				if( valid && i == 3 ) // i == 3 to ignore invalid lines
					pouch.add( new Sphere( gem, size, infused ) );
			}
			
			sc.close();
		} catch( Exception e ) {
			System.out.println( "Pouch does not exist!" );
		}
	}

	// Method to prompt user for filename to write into parameter ArrayList, calls pickPouch()
	// PARAMETER: -
	// OUTPUT: -
	private static boolean pickPouchPrompt( ArrayList<Sphere> pouch ) {
		String filename;
		char choice;
		boolean cancel, success;
		
		choice = 'a';
		cancel = false;
		success = false;
		
		while( !(choice == 'S' || choice == 'C' || choice == 'N') && pouch.size() != 0 ){
			choice = Character.toUpperCase( usrString( 	"Would you like to:\n" + 
														"[S]tow the current pouch before interacting with another pouch\n" + 
														"[C]ombine it with the current pouch\n" + 
														"Ca[N]cel\n\t> " ).charAt(0) );
			if( choice == 'S' ) {
				movePouchStowed( pouch );
				pouch.clear(); // Removes every element in list
			} else if( choice == 'C' )
				System.out.print( "" ); // Do nothing
			else if ( choice == 'N' )
				cancel = true;
			else
				System.out.println( "Huh?" );
		} 
		
		if( !cancel ) {
			filename = usrString( "What pouch would you like to look at?\n\t> " ) + ".txt";
			
			if( new File( "Pouches\\stowed\\" + filename ).exists() ) {
				pickPouch( pouch, "stowed\\" + filename );
				
				if( choice == 'C' ) {
							clearFile( filename ); // Clear contents of second pouch when combining
				} else {
					movePouchOpened( filename ); // Move open pouch
					alterFile( pouch, "opened\\" + filename ); // Alter file since picking pouch will exclude invalid data
				}
				
				success = true;
			} else 
				System.out.println( "Pouch does not exist!" );
		}
		
		return success;
	} // END of pickPouchPrompt
		
	// Method essentially moves the stowed file of the parameter filename into 'opened' folder.
	// PARAMETER: String filename
	// OUTPUT: -
	private static void movePouchOpened( String filename ) {
		File toOpen = new File( "Pouches\\stowed\\" + filename );
		toOpen.renameTo( new File( "Pouches\\opened\\" + filename ) );
	}
	
	// Method essentially moves the opened file into stowed folder and clears parameter arraylist.
	// PARAMETER: ArrayList<Sphere> pouch
	// OUTPUT: -
	private static void movePouchStowed( ArrayList<Sphere> pouch ) {
		String openedFilename;
		
		pouch.clear(); // Clear pouch since it shouldn't be active now
		
		File folder = new File( "Pouches\\opened");
		if( folder.listFiles().length > 0 ) {
			openedFilename = folder.listFiles()[0].getName();
			File toStow = new File( "Pouches\\opened\\" + openedFilename );
			toStow.renameTo( new File( "Pouches\\stowed\\" + openedFilename ) );
		}
	}
	
	// Method to prompt user for parameter filename to write ArrayList into, calls newPouch()
	// PARAMETER: -
	// OUTPUT: -
	private static void newPouchPrompt () {
		String filename;
		
		filename = usrString( "What would you like to name your pouch?\n\t> " ) + ".txt";
		newPouch( "stowed", filename );
	}
	
	// Method to create a new pouch file as parameter filename into parameter where folder.
	// PARAMETER: String where, String filename
	// OUTPUT: -
	private static void newPouch( String where, String filename ) {
		PrintWriter pw = null;
		boolean resume = true;
		char choice;
		String address;
		
		address = "Pouches\\" + where + "\\" + filename;
		
		try {
			if ( new File( address ).exists() )
				System.out.println( "A pouch with the same name already exists!" );
			
			if( resume ) {
				pw = new PrintWriter( new File( address ) );
				pw.print( "" );
				System.out.println( "You called the pouch '" + filename + "' and stowed it away." );
				
				pw.close();
			}
		} catch( Exception e ) {
			System.out.println( "ERROR: " + e );
		}
	} // END of newPouch
	
	// Method to overwrite/alter pouch file of parameter 'location' address.
	// PARAMETER: ArrayList<Sphere> pouch, String location
	// OUTPUT: -
	private static void alterFile( ArrayList<Sphere> pouch, String location ) {
		PrintWriter pw = null;
		String address;
		
		address = "Pouches\\" + location;
		
		try {
			pw = new PrintWriter( new File( address ) );
			for( int i = 0; i < pouch.size(); i++ ) {
				pw.println( pouch.get(i).getGem() + "," + pouch.get(i).getSize() + "," + pouch.get(i).getInfused() );
			}
			
			pw.close();
		} catch ( Exception e ) {
			System.out.println( "Error: " + e );
		}
	} // END of alterFile
	
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
			System.out.println( "Pouch " + filename + " does not exist!" );
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
	
	// Method to check and display to user the existing files in the parameter folder 
	// PARAMETER: String location
	// OUTPUT: -
	private static void checkFolderFiles( String location ) {
		StringTokenizer split;
		File folder = new File( "Pouches\\" + location );
		File[] files = folder.listFiles();
		
		for( int i = 0; i < files.length; i++ ) {
			split = new StringTokenizer( files[i].getName(), "." );
			System.out.println( "'" + split.nextToken() + "'" );
		}
	} // END of checkPouches

	// Method to put pouch file from opened folder into hung folder
	// PARAMETER: ArrayList<Sphere> pouch
	// OUTPUT: -
	private static void hangPouch( ArrayList<Sphere> pouch ) {
		File folder = new File( "Pouches\\opened");
		File[] files = folder.listFiles();
		String filename;
		StringTokenizer split;
		
		if( files.length > 0 ) {
			for( int i = 0; i < files.length; i++ ) {
				filename = files[i].getName();
				File hang = new File( "Pouches\\opened\\" + filename );
				hang.renameTo( new File( "Pouches\\hung\\" + filename ) );
				split = new StringTokenizer( filename, "." );
				System.out.println( "You tightly secured pouch '" + split.nextToken() + "' to a sturdy branch." );
				pouch.clear(); // Clear arraylist since content is no longer active
			}
		}
	} // END of hangPouch
	
	// Method to set all spheres in active pouch as dun
	// PARAMETER: ArrayList<Sphere> pouch
	// OUTPUT: -
	private static void takeBreath( ArrayList<Sphere> pouch ) {
		ArrayList<Sphere> breath = new ArrayList<Sphere>();
		File folder = new File( "Pouches\\opened");
		File[] files = folder.listFiles();
		String filename;
		boolean breathedStormlight = false;
		
		if( files.length > 0 ) {
			for( int i = 0; i < files.length; i++ ) {
				filename = files[i].getName();
				pickPouch( breath, "opened\\" + filename );
				for( int j = 0; j < breath.size(); j++ ) {
					if( breath.get(j).getInfused() == 'T' )
						breathedStormlight = true;
					breath.get(j).breath();
				}
				
				if( breathedStormlight ) {
					System.out.println( "You take a deep breath and feel a surge of energy." );
					alterFile( breath, "opened\\" + filename );
					pouch.clear(); // Clear and then
					pickPouch( pouch, "opened\\" + filename ); // Update current ArrayList
				} 
			}
		}
		
		if( !breathedStormlight ) 
			System.out.println( "You take a deep breath and feel no better." );
	} // END of takeBreath
	
	
	// Method to set all spheres in all hung pouch(es) as infused
	// PARAMETER: -
	// OUTPUT: -
	private static void summonStorm() {
		System.out.println( "You somehow summoned a highstorm..." );
		ArrayList<Sphere> infusing = new ArrayList<Sphere>();
		File folder = new File( "Pouches\\hung");
		File[] files = folder.listFiles();
		String filename;
		
		if( files.length > 0 ) {
			for( int i = 0; i < files.length; i++ ) {
				filename = files[i].getName();
				pickPouch( infusing, "hung\\" + filename );
				for( int j = 0; j < infusing.size(); j++ ) {
					infusing.get(j).infuse();
				}
				
				alterFile( infusing, "hung\\" + filename );
			}
		}
	} // END of summonStorm
	
	// Method to move all files in hung folder into stowed
	// PARAMETER: -
	// OUTPUT: -
	private static void retrieveHung() {
		File folder = new File( "Pouches\\hung");
		File[] files = folder.listFiles();
		String filename;
		StringTokenizer split;
		
		if( files.length > 0 ) {
			for( int i = 0; i < files.length; i++ ) {
				filename = files[i].getName();
				File stow = new File( "Pouches\\hung\\" + filename );
				stow.renameTo( new File( "Pouches\\stowed\\" + filename ) );
				split = new StringTokenizer( filename, "." );
				System.out.println( "Pouch '" + split.nextToken() + "' retrieved and stowed.");
			}
		} else
			System.out.println( "There are no pouches hanging..." );
	} // END of retrieveHung
} // END of CLASS